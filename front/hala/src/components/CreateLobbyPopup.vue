<template>
  <v-card>
    <v-card-title>
      <span class="text-h5">Создать новую игру</span>
    </v-card-title>
    <v-card-text>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-text-field v-model="lobby.lobbyName" label="Название игры" required></v-text-field>
          </v-col>
          <v-col cols="12">
            <v-checkbox v-model="lobby.isPrivate" label="Приватная игра"></v-checkbox>
          </v-col>
          <v-col cols="12" v-if="lobby.isPrivate">
            <v-text-field v-model="lobby.key" label="Ключ игры" required></v-text-field>
          </v-col>
          <v-col cols="12">
            <v-select
              v-model="cardCount"
              :items="[6, 12, 24, 36]"
              label="Количество карт"
              required
            ></v-select>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" @click="closeDialog">Отмена</v-btn>
      <v-btn color="blue darken-1" @click="createGame">Создать</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script lang="ts">
import { ref, watch } from 'vue'
import { useGameStore } from '@/stores/game'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'

export default {
  props: {
    dialog: Boolean
  },
  emits: ['update:dialog'],
  setup(props, { emit }) {
    const internalDialog = ref(props.dialog)

    const lobby = ref({
      lobbyName: '',
      isPrivate: false,
      key: ''
    })
    const users = ref([])
    const cardCount = ref(12)
    const gameStore = useGameStore()
    const userStore = useUserStore()

    const router = useRouter()
    const errorMessage = ref('')

    watch(
      () => props.dialog,
      (newVal) => {
        internalDialog.value = newVal
      }
    )

    const closeDialog = () => {
      emit('update:dialog', false)
    }

    const createGame = async () => {
      const currentUser = userStore.currentUser
      if (currentUser) {
        try {
          const newGame = await gameStore.createGame(lobby.value, [currentUser], cardCount.value)
          closeDialog()
          router.push(`/lobbies/${newGame.lobby.id}`)
        } catch (error) {
          errorMessage.value = 'Не удалось создать игру. Пожалуйста, попробуйте еще раз.'
        }
      } else {
        alert('Пожалуйста, сначала установите свое имя пользователя.')
      }
    }

    return {
      internalDialog,
      lobby,
      users,
      cardCount,
      createGame,
      closeDialog,
      errorMessage
    }
  }
}
</script>
