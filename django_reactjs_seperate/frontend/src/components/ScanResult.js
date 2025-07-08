import React, { useState } from 'react';
import EMVTLVParser from './EMVTLVParser';
import './ScanResult.css';

const ScanResult = ({ result }) => {
  const [showRaw, setShowRaw] = useState(false);
  const [showTLVDetails, setShowTLVDetails] = useState(false);
  const emvParser = new EMVTLVParser();

  if (!result) {
    return (
      <div className="scan-result empty">
        <p>暂无扫描结果</p>
      </div>
    );
  }

  const { type, data, format } = result;
  
  // 检查是否为EMV码
  const emvResult = emvParser.parse(data);
  const isEMV = emvResult !== null;

  const formatTimestamp = (timestamp) => {
    return new Date(timestamp).toLocaleString('zh-CN');
  };

  const renderBasicInfo = () => (
    <div className="basic-info">
      <div className="info-row">
        <span className="label">类型:</span>
        <span className="value">{type}</span>
      </div>
      
      {format && (
        <div className="info-row">
          <span className="label">格式:</span>
          <span className="value">{format}</span>
        </div>
      )}
      
      <div className="info-row">
        <span className="label">长度:</span>
        <span className="value">{data.length} 字符</span>
      </div>
      
      <div className="info-row">
        <span className="label">扫描时间:</span>
        <span className="value">{formatTimestamp(Date.now())}</span>
      </div>
    </div>
  );

  const renderTLVItem = (item, level = 0) => {
    const indent = level * 20;
    
    return (
      <div key={`${item.tag}-${level}`} className="tlv-item" style={{ marginLeft: `${indent}px` }}>
        <div className="tlv-header">
          <span className="tlv-tag">{item.tag}</span>
          <span className="tlv-length">[{item.length}]</span>
          <span className="tlv-name">{item.name}</span>
        </div>
        
        <div className="tlv-description">{item.description}</div>
        
        <div className="tlv-value">
          <span className="label">值:</span>
          <span className="value">{item.displayValue}</span>
        </div>
        
        {item.value !== item.displayValue && (
          <div className="tlv-raw-value">
            <span className="label">原始值:</span>
            <span className="value raw">{item.value}</span>
          </div>
        )}
        
        {item.subTags && item.subTags.length > 0 && (
          <div className="tlv-subtags">
            <div className="subtags-header">子标签:</div>
            {item.subTags.map(subTag => renderTLVItem(subTag, level + 1))}
          </div>
        )}
      </div>
    );
  };

  const renderEMVSummary = () => {
    if (!isEMV) return null;
    
    const { summary, crcValidation } = emvResult;
    
    return (
      <div className="emv-summary">
        <h4>EMV 摘要信息</h4>
        
        <div className="summary-grid">
          {summary.merchantName && (
            <div className="summary-item">
              <span className="label">商户名称:</span>
              <span className="value">{summary.merchantName}</span>
            </div>
          )}
          
          {summary.merchantCity && (
            <div className="summary-item">
              <span className="label">商户城市:</span>
              <span className="value">{summary.merchantCity}</span>
            </div>
          )}
          
          {summary.amount && (
            <div className="summary-item">
              <span className="label">交易金额:</span>
              <span className="value">{summary.amount}</span>
            </div>
          )}
          
          {summary.currency && (
            <div className="summary-item">
              <span className="label">交易货币:</span>
              <span className="value">{summary.currency}</span>
            </div>
          )}
          
          {summary.mcc && (
            <div className="summary-item">
              <span className="label">商户类别:</span>
              <span className="value">{summary.mcc}</span>
            </div>
          )}
        </div>
        
        <div className="crc-validation">
          <div className={`crc-status ${crcValidation.valid ? 'valid' : 'invalid'}`}>
            <span className="status-icon">{crcValidation.valid ? '✓' : '✗'}</span>
            <span className="status-text">{crcValidation.reason}</span>
          </div>
          
          {!crcValidation.valid && (
            <div className="crc-details">
              <div>提供的CRC: {crcValidation.provided}</div>
              <div>计算的CRC: {crcValidation.calculated}</div>
            </div>
          )}
        </div>
      </div>
    );
  };

  const renderTLVDetails = () => {
    if (!isEMV || !showTLVDetails) return null;
    
    return (
      <div className="tlv-details">
        <h4>TLV 详细解析</h4>
        <div className="tlv-list">
          {emvResult.tlvData.map(item => renderTLVItem(item))}
        </div>
      </div>
    );
  };

  const renderRawData = () => {
    if (!showRaw) return null;
    
    return (
      <div className="raw-data">
        <h4>原始数据</h4>
        <div className="raw-content">
          <pre>{data}</pre>
        </div>
      </div>
    );
  };

  const copyToClipboard = () => {
    navigator.clipboard.writeText(data).then(() => {
      // 可以添加一个提示
      alert('已复制到剪贴板');
    }).catch(err => {
      console.error('复制失败:', err);
    });
  };

  return (
    <div className="scan-result">
      <div className="result-header">
        <h3>扫描结果</h3>
        {isEMV && (
          <div className="emv-badge">EMV QR码</div>
        )}
      </div>
      
      {renderBasicInfo()}
      
      {isEMV && renderEMVSummary()}
      
      <div className="result-controls">
        <button 
          className="toggle-btn"
          onClick={() => setShowRaw(!showRaw)}
        >
          {showRaw ? '隐藏' : '显示'}原始数据
        </button>
        
        {isEMV && (
          <button 
            className="toggle-btn"
            onClick={() => setShowTLVDetails(!showTLVDetails)}
          >
            {showTLVDetails ? '隐藏' : '显示'}TLV详情
          </button>
        )}
        
        <button 
          className="copy-btn"
          onClick={copyToClipboard}
        >
          复制数据
        </button>
      </div>
      
      {renderRawData()}
      
      {renderTLVDetails()}
    </div>
  );
};

export default ScanResult;