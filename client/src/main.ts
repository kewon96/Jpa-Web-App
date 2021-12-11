import { createApp } from 'vue'
import App from './App.vue'
import { routers } from './route/routers'
import "./style/_reset.scss"
import "./style/_auth.scss"

createApp(App).use(routers).mount('#app')
