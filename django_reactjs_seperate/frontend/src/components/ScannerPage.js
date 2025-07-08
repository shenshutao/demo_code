import React, { useState, useCallback } from 'react';
import CodeScanner from './CodeScanner';
import ScanResult from './ScanResult';
import './ScannerPage.css';

const ScannerPage = () => {
  const [scanResult, setScanResult] = useState(null);
  const [scanHistory, setScanHistory] = useState([]);
  const [showHistory, setShowHistory] = useState(false);

  const handleScan = useCallback((result) => {
    console.log('扫描结果:', result);
    
    // 添加时间戳
    const resultWithTimestamp = {
      ...result,
      timestamp: Date.now(),
      id: Date.now() + Math.random() // 简单的ID生成
    };
    
    setScanResult(resultWithTimestamp);
    
    // 添加到历史记录
    setScanHistory(prev => [resultWithTimestamp, ...prev.slice(0, 9)]); // 最多保留10条记录
  }, []);

  const handleSelectHistoryItem = (item) => {
    setScanResult(item);
    setShowHistory(false);
  };

  const handleClearHistory = () => {
    setScanHistory([]);
    setShowHistory(false);
  };

  const formatTimestamp = (timestamp) => {
    return new Date(timestamp).toLocaleString('zh-CN');
  };

  const truncateText = (text, maxLength = 50) => {
    if (text.length <= maxLength) return text;
    return text.substring(0, maxLength) + '...';
  };

  return (
    <div className="scanner-page">
      <header className="page-header">
        <h1>二维码 & 条形码扫描器</h1>
        <p className="page-description">
          支持各种二维码和条形码扫描，特别针对EMV码提供TLV格式解析
        </p>
      </header>

      <div className="scanner-container">
        <div className="scanner-section">
          <CodeScanner onScan={handleScan} />
        </div>

        <div className="history-section">
          <div className="history-header">
            <h3>扫描历史</h3>
            <div className="history-controls">
              <button 
                className="toggle-history-btn"
                onClick={() => setShowHistory(!showHistory)}
                disabled={scanHistory.length === 0}
              >
                {showHistory ? '隐藏' : '显示'}历史 ({scanHistory.length})
              </button>
              
              {scanHistory.length > 0 && (
                <button 
                  className="clear-history-btn"
                  onClick={handleClearHistory}
                >
                  清空历史
                </button>
              )}
            </div>
          </div>

          {showHistory && scanHistory.length > 0 && (
            <div className="history-list">
              {scanHistory.map((item, index) => (
                <div 
                  key={item.id}
                  className="history-item"
                  onClick={() => handleSelectHistoryItem(item)}
                >
                  <div className="history-item-header">
                    <span className="history-type">{item.type}</span>
                    <span className="history-time">{formatTimestamp(item.timestamp)}</span>
                  </div>
                  <div className="history-data">
                    {truncateText(item.data)}
                  </div>
                  {item.format && (
                    <div className="history-format">
                      格式: {item.format}
                    </div>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>

        <div className="result-section">
          <ScanResult result={scanResult} />
        </div>
      </div>

      <footer className="page-footer">
        <div className="features">
          <div className="feature">
            <h4><span role="img" aria-label="mobile phone">📱</span> 二维码扫描</h4>
            <p>支持标准二维码格式，包括URL、文本、WiFi等</p>
          </div>
          <div className="feature">
            <h4><span role="img" aria-label="bar chart">📊</span> 条形码扫描</h4>
            <p>支持多种条形码格式：EAN、UPC、Code 128、Code 39等</p>
          </div>
          <div className="feature">
            <h4><span role="img" aria-label="credit card">💳</span> EMV码解析</h4>
            <p>专门针对EMV支付码进行TLV格式解析和美化显示</p>
          </div>
        </div>
        
        <div className="tech-info">
          <p>
            <strong>技术支持:</strong> 
            qr-scanner, QuaggaJS, 自定义EMV TLV解析器
          </p>
          <p>
            <strong>支持的码制:</strong> 
            QR Code, Data Matrix, EAN-13, EAN-8, UPC-A, UPC-E, Code 128, Code 39, Code 93, Codabar, ITF, 2of5
          </p>
        </div>
      </footer>
    </div>
  );
};

export default ScannerPage;