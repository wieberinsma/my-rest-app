const path = require('path');
const os = process.platform;
const sep = os === 'win32' ? '\\' : '/';
const resourcePath = [__dirname, 'src', 'main', 'resources'].join(sep);
const entryPoint = [resourcePath, 'main.js'].join(sep);
const outputPath = [resourcePath, 'static', 'built'].join(sep);
console.log('\nEntry point: ' + entryPoint);
console.log('Output path: ' + outputPath + '\n');

module.exports = {
    target: 'web',
    devtool: 'source-map',
    // watch: true,
    entry: entryPoint,
    output: {
        path: outputPath,
        filename: 'bundle.js',
    },
    resolve: {
        modules: [resourcePath, 'node_modules']
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [
                    {
                        loader: 'babel-loader',
                        options: {
                            presets: ['@babel/preset-env', '@babel/preset-react']
                        }
                    }
                ]
            },
            {
                test: /\.css$/i,
                use: ["style-loader", "css-loader"]
            }
        ]
    }
};
