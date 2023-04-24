const path = require('path');

module.exports = {
    entry: './main.js',
    devtool: 'sourcemaps',
    output: {
        path: path.resolve(__dirname, '/dist'),
        filename: 'bundle.js',
    },
    resolve: {
        modules: [
            path.resolve(__dirname, 'src/main/resources/js'),
            'node_modules'
        ]
    },
    module: {
        rules: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env']
                    }
                }
            }
        ]
    }
};
