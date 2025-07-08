import React, { Component } from 'react';
import ScannerPage from './components/ScannerPage';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentPage: 'scanner' // 'home' or 'scanner'
    };
  }

  handlePageChange = (page) => {
    this.setState({ currentPage: page });
  };

  renderHomePage = () => {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <div className="App-intro">
          <p>To get started, edit <code>src/App.js</code> and save to reload.</p>
          <button 
            className="scanner-nav-btn"
            onClick={() => this.handlePageChange('scanner')}
          >
            打开扫码功能
          </button>
        </div>
      </div>
    );
  };

  renderScannerPage = () => {
    return (
      <div className="App">
        <div className="nav-header">
          <button 
            className="back-btn"
            onClick={() => this.handlePageChange('home')}
          >
            ← 返回主页
          </button>
        </div>
        <ScannerPage />
      </div>
    );
  };

  render() {
    const { currentPage } = this.state;
    
    return currentPage === 'scanner' ? this.renderScannerPage() : this.renderHomePage();
  }
}

export default App;
