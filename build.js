const { build } = require("esbuild");
const servor = require("servor");

const watch = true; // The command used to be "!!process.env.WATCH", but Windows users had an error;

const outDir = './target/classes/static/js';

if (watch) {
    console.log("\nKeep this program running while you work in your IDE (It rebuilds the JS files automatically)");
    console.log("Press Ctrl+C to stop\n");
}

build({
    entryPoints: [
        './src/main/resources/static/js/app.js',
        './src/main/resources/static/js/traceability-matrix-react.js',
        './src/main/resources/static/js/traceability-matrix-vanilla.js',
        './src/main/resources/static/js/page-adf-comparison.js'
    ],
    outdir: outDir,
    loader: { '.js': 'jsx' },
    minify: false,
    bundle: true,
    sourcemap: true,
    watch: watch,

    // This is a gross hacking because @atlaskit/next-analytics tries to access the variable 'process' which is undefined.
    // I'd hope it's temporary.
    define: {
        "process": JSON.stringify({
            env: {
                NODE_ENV: "production"
            }
        })
    },
    platform: "browser"

}).then(async () => {
    watch && await servor({
        browser: true,
        root: outDir,
        port: 8090,
    });
});
