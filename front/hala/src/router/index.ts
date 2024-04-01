import { createRouter, createWebHistory } from 'vue-router'
import MainMenu from '@/components/MainMenu.vue'
import LobbyPage from '@/components/LobbyPage.vue'
import GamePage from '@/components/GamePage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'menu',
      component: MainMenu
    },
    {
      path: '/lobbies/:id',
      name: 'lobby',
      component: LobbyPage,
      props: true
    },
    {
      path: '/game/:id',
      name: 'game',
      component: GamePage,
      props: true
    }
  ]
})

export default router
