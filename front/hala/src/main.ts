import './assets/main.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { io } from 'socket.io-client'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

import App from './App.vue'
import router from './router'

const app = createApp(App)
const vuetify = createVuetify({
  ssr: true,
  components,
  directives
})

const socket = io('http://localhost:3000')

app.config.globalProperties.$socket = socket

app.use(createPinia())
app.use(router)
app.use(vuetify)

app.mount('#app')

window.addEventListener('DOMContentLoaded', () => {
  const fontFamily = localStorage.getItem('fontFamily')
  if (fontFamily) {
    document.documentElement.style.setProperty('--font-family', fontFamily)
  }
})
