import React, { Component } from 'react';
import { hot } from 'react-hot-loader'

class App extends Component {
  render() {
    return (
      <div>
        <h1> This is React App !!!</h1>
      </div>
    );
  }
}

export default hot(module)(App)
