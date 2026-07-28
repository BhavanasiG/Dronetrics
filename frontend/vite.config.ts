import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import {fileURLToPath, URL} from 'node:url'

export default defineConfig({
    plugins: [vue()],
    root: "../src/main/frontend",
    envDir: fileURLToPath(new URL('../', import.meta.url)),
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('../src/main/frontend', import.meta.url))
        }
    },
    server: {
        port: 3000,
        proxy: {
            '/rest': 'http://localhost:2004'
        }
    }
});