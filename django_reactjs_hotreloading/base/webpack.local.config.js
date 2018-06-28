var path = require("path")
var webpack = require('webpack')
var BundleTracker = require('webpack-bundle-tracker')

module.exports = {
  context: __dirname,
  mode: 'development',

  entry: [
    'webpack-dev-server/client?http://localhost:3000',
    'webpack/hot/only-dev-server',
    '../assets/js/index'
  ],

  output: {
    path: path.resolve('./assets/bundles/'),
    filename: '[name]-[hash].js',

    // Tell django to use this URL to load packages and not use STATIC_URL + bundle_name
    // Comment if want to use static url
    publicPath: 'http://localhost:3000/assets/bundles/',
  },

  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NoEmitOnErrorsPlugin(), // don't reload if there is an error
    new BundleTracker({ filename: './webpack-stats.json' }),
  ],

  module: {
    rules: [
      {
        test: /\.jsx?$/,
        exclude: /node_modules/,
        use: [
          {
            loader: 'babel-loader',
            options: {
              presets: ['react']
            }
          }
        ],
      }
    ],
  },

  resolve: {
    modules: ['node_modules', 'bower_components'],
    extensions: ['.js', '.jsx']
  },
}
