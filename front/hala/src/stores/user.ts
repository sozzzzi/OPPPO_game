import { defineStore } from 'pinia'
import type User from '@/interfaces/User'
import type Api from '@/utils/Api'
import ServerApi from '@/utils/ServerApi'

const api: Api = new ServerApi()

export const useUserStore = defineStore({
  id: 'user',
  state: () => ({
    currentUser: null as User | null
  }),
  actions: {
    async createUser(userEntity: User) {
      const user = await api.createUser(userEntity)
      this.currentUser = user
      sessionStorage.setItem('userName', user.userName)
    },
    loadUserFromSession() {
      const userName = sessionStorage.getItem('userName')
      if (userName) {
        this.currentUser = { userName }
      }
    }
  }
})
