import { createApp } from 'vue'
import App from './App.vue'
import { routers } from './route/routers'

createApp(App).use(routers).mount('#app')
