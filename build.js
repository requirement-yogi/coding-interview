const { build } = require("esbuild");
const servor = require("servor");

const watch = !!process.env.WATCH;

const outDir = './target/classes/static/js';

if (watch) {
    console.log("\nKeep this program running while you work in your IDE (It rebuilds the JS files automatically)");
    console.log("Press Ctrl+C to stop\n");
}

build({
    entryPoints: ['./src/main/resources/static/js/app.js'],
    outdir: outDir,
    loader: { '.js': 'jsx' },
    minify: true,
    bundle: true,
    sourcemap: true,
    watch: watch
}).then(async () => {
    watch && await servor({
        browser: true,
        root: outDir,
        port: 8090,
    });
});
