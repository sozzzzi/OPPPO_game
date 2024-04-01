<template>
  <v-container class="main-menu">
    <v-card-text>
      <v-row justify="center">
        <v-card-title>Главное меню</v-card-title>
      </v-row>
      <v-row class="name-text-fiels" justify="center">
        <v-text-field v-model="name" label="Имя" outlined></v-text-field>
      </v-row>
      <v-row justify="center">
        <p :class="messageColor" class="message">{{ message }}</p>
      </v-row>
      <v-row justify="center">
        <v-btn class="menu-button" @click="confirmName">Утвердить имя</v-btn>
      </v-row>
      <v-row justify="center">
        <v-btn class="menu-button" @click="createGame">Создать игру</v-btn>
        <v-dialog v-model="dialog" max-width="600px">
          <CreateLobbyPopup v-model:dialog="dialog" />
        </v-dialog>
      </v-row>
      <v-row justify="center">
        <v-btn class="menu-button" @click="findGame">Найти игру</v-btn>
        <v-dialog v-model="findDialog" max-width="600px">
          <FindLobbyPopup v-model:findDialog="findDialog" />
        </v-dialog>
      </v-row>
      <v-row justify="center">
        <v-btn class="menu-button" @click="openSettings">Настройки</v-btn>
        <v-dialog v-model="settingDialog" max-width="600px">
          <SettingPopup v-model:dialog="settingDialog" />
        </v-dialog>
      </v-row>
    </v-card-text>
  </v-container>
</template>

<script lang="ts">
import { ref } from 'vue'
import { useUserStore } from '@/stores/user'
import CreateLobbyPopup from '@/components/CreateLobbyPopup.vue'
import FindLobbyPopup from '@/components/FindLobbyPopup.vue'
import SettingPopup from '@/components/SettingPopup.vue'
import { useGameStore } from '@/stores/game'

export default {
  components: {
    CreateLobbyPopup,
    FindLobbyPopup,
    SettingPopup
  },
  setup() {
    const name = ref('')
    const message = ref('')
    const messageColor = ref('')
    const dialog = ref(false)
    const findDialog = ref(false)
    const settingDialog = ref(false)
    const userStore = useUserStore()
    const gameStore = useGameStore()
    userStore.loadUserFromSession()

    if (userStore.currentUser) {
      name.value = userStore.currentUser.userName
    }

    const confirmName = async () => {
      if (name.value) {
        try {
          await userStore.createUser({ userName: name.value })
          message.value = 'Пользователь успешно создан'
          messageColor.value = 'green'
        } catch (error) {
          message.value = 'Не удалось создать пользователя'
          messageColor.value = 'red'
        }
      }
    }

    const createGame = () => {
      if (userStore.currentUser) {
        dialog.value = true
      } else {
        message.value = 'Пожалуйста, сначала установите свое имя пользователя.'
        messageColor.value = 'red'
      }
    }

    const findGame = async () => {
      if (userStore.currentUser) {
        findDialog.value = true
        await gameStore.loadLobbies()
        console.log(gameStore.lobbies)
      } else {
        message.value = 'Пожалуйста, сначала установите свое имя пользователя.'
        messageColor.value = 'red'
      }
    }

    const openSettings = () => {
      settingDialog.value = true
    }

    return {
      name,
      message,
      messageColor,
      dialog,
      findDialog,
      settingDialog,
      confirmName,
      createGame,
      findGame,
      openSettings
    }
  }
}
</script>
