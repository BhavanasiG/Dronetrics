import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import {fileURLToPath, URL} from 'node:url'
import path from 'path'

export default defineConfig({
    plugins: [vue()],
    envDir: path.resolve(__dirname, '../../..'),
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./', import.meta.url))
        }
    },
    server: {
        port: 3000,
        proxy: {
            '/rest': 'http://localhost:2004'
        }
    }
});