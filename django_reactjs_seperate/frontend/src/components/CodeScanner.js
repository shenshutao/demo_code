import React, { useState, useRef, useEffect } from 'react';
import jsQR from 'jsqr';
import Quagga from 'quagga';
import './CodeScanner.css';

const CodeScanner = ({ onScan }) => {
  const [isScanning, setIsScanning] = useState(false);
  const [scanMode, setScanMode] = useState('qr'); // 'qr' or 'barcode'
  const videoRef = useRef(null);
  const canvasRef = useRef(null);
  const streamRef = useRef(null);
  const scanningRef = useRef(false);
  const [hasCamera, setHasCamera] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    // 检查摄像头权限
    navigator.mediaDevices.getUserMedia({ video: true })
      .then(stream => {
        setHasCamera(true);
        stream.getTracks().forEach(track => track.stop());
      })
      .catch(err => {
        setError('无法访问摄像头');
        setHasCamera(false);
      });

    return () => {
      // 清理
      if (streamRef.current) {
        streamRef.current.getTracks().forEach(track => track.stop());
      }
      Quagga.stop();
      scanningRef.current = false;
    };
  }, []);

  const startQRScan = async () => {
    try {
      if (!videoRef.current || !canvasRef.current) return;
      
      const stream = await navigator.mediaDevices.getUserMedia({
        video: {
          facingMode: 'environment'
        }
      });
      
      streamRef.current = stream;
      videoRef.current.srcObject = stream;
      
      await new Promise((resolve) => {
        videoRef.current.onloadedmetadata = resolve;
      });
      
      videoRef.current.play();
      setIsScanning(true);
      setError('');
      scanningRef.current = true;
      
      // 开始扫描循环
      scanQRCode();
    } catch (err) {
      setError('启动二维码扫描失败: ' + err.message);
      console.error('QR扫描错误:', err);
    }
  };

  const scanQRCode = () => {
    if (!scanningRef.current || !videoRef.current || !canvasRef.current) return;
    
    const video = videoRef.current;
    const canvas = canvasRef.current;
    const context = canvas.getContext('2d');
    
    if (video.readyState === video.HAVE_ENOUGH_DATA) {
      canvas.width = video.videoWidth;
      canvas.height = video.videoHeight;
      context.drawImage(video, 0, 0, canvas.width, canvas.height);
      
      const imageData = context.getImageData(0, 0, canvas.width, canvas.height);
      const code = jsQR(imageData.data, imageData.width, imageData.height);
      
      if (code) {
        console.log('QR扫描结果:', code);
        onScan({
          type: 'QR',
          data: code.data,
          rawData: code
        });
        // 找到码后暂停扫描
        scanningRef.current = false;
        setIsScanning(false);
        if (streamRef.current) {
          streamRef.current.getTracks().forEach(track => track.stop());
        }
        return;
      }
    }
    
    // 继续扫描
    requestAnimationFrame(scanQRCode);
  };

  const startBarcodeScan = async () => {
    try {
      if (!videoRef.current) return;

      Quagga.init({
        inputStream: {
          name: "Live",
          type: "LiveStream",
          target: videoRef.current,
          constraints: {
            width: 640,
            height: 480,
            facingMode: "environment"
          }
        },
        decoder: {
          readers: [
            "code_128_reader",
            "ean_reader",
            "ean_8_reader",
            "code_39_reader",
            "code_39_vin_reader",
            "codabar_reader",
            "upc_reader",
            "upc_e_reader",
            "i2of5_reader",
            "2of5_reader",
            "code_93_reader"
          ]
        }
      }, (err) => {
        if (err) {
          console.error('条形码扫描初始化失败:', err);
          setError('启动条形码扫描失败: ' + err.message);
          return;
        }

        Quagga.start();
        setIsScanning(true);
        setError('');

        Quagga.onDetected((result) => {
          console.log('条形码扫描结果:', result);
          onScan({
            type: 'BARCODE',
            data: result.codeResult.code,
            format: result.codeResult.format,
            rawData: result
          });
        });
      });
    } catch (err) {
      setError('启动条形码扫描失败: ' + err.message);
      console.error('条形码扫描错误:', err);
    }
  };

  const stopScan = () => {
    if (scanMode === 'qr') {
      scanningRef.current = false;
      if (streamRef.current) {
        streamRef.current.getTracks().forEach(track => track.stop());
      }
    } else if (scanMode === 'barcode') {
      Quagga.stop();
    }
    setIsScanning(false);
  };

  const handleStartScan = () => {
    if (scanMode === 'qr') {
      startQRScan();
    } else {
      startBarcodeScan();
    }
  };

  const handleModeChange = (mode) => {
    if (isScanning) {
      stopScan();
    }
    setScanMode(mode);
  };

  if (!hasCamera) {
    return (
      <div className="code-scanner no-camera">
        <h3>扫码功能</h3>
        <p>未检测到摄像头或摄像头权限被拒绝</p>
      </div>
    );
  }

  return (
    <div className="code-scanner">
      <h3>扫码功能</h3>
      
      <div className="scan-modes">
        <button 
          className={scanMode === 'qr' ? 'active' : ''}
          onClick={() => handleModeChange('qr')}
          disabled={isScanning}
        >
          二维码
        </button>
        <button 
          className={scanMode === 'barcode' ? 'active' : ''}
          onClick={() => handleModeChange('barcode')}
          disabled={isScanning}
        >
          条形码
        </button>
      </div>

      <div className="video-container">
        <video 
          ref={videoRef}
          className="scanner-video"
          autoPlay
          playsInline
          muted
        />
        
        <canvas 
          ref={canvasRef}
          className="scanner-canvas"
          style={{ display: 'none' }}
        />
        
        {!isScanning && (
          <div className="scan-overlay">
            <p>点击开始扫描{scanMode === 'qr' ? '二维码' : '条形码'}</p>
          </div>
        )}
      </div>

      <div className="scan-controls">
        {!isScanning ? (
          <button 
            className="start-scan-btn"
            onClick={handleStartScan}
          >
            开始扫描
          </button>
        ) : (
          <button 
            className="stop-scan-btn"
            onClick={stopScan}
          >
            停止扫描
          </button>
        )}
      </div>

      {error && (
        <div className="error-message">
          {error}
        </div>
      )}
    </div>
  );
};

export default CodeScanner;