var path = require("path")
var webpack = require('webpack')
var BundleTracker = require('webpack-bundle-tracker')

module.exports = {
    context: __dirname,
    mode: 'production',

    entry: [
        '../assets/js/index'
    ],

    output: {
        path: path.resolve('./assets/dist/'),
        filename: '[name]-[hash].js',
    },

    plugins: [
        new BundleTracker({ filename: './webpack-stats-prod.json' }),

        // removes a lot of debugging code in React
        new webpack.DefinePlugin({
            'process.env': {
                'NODE_ENV': JSON.stringify('production')
            }
        }),

        // keeps hashes consistent between compilations
        new webpack.optimize.OccurrenceOrderPlugin(),
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
