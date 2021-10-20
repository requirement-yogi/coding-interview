const { build } = require("esbuild");
const servor = require("servor");

const outDir = './target/classes/static/js';

build({
    entryPoints: ['./src/main/resources/static/js/app.js'],
    outdir: outDir,
    loader: { '.js': 'jsx' },
    minify: true,
    bundle: true,
    sourcemap: true,
    watch: true
}).then(async () => {
    await servor({
        browser: true,
        root: outDir,
        port: 8090,
    });
});

