<template>
  <v-card>
    <v-card-title>
      <span class="text-h5">Найти игру</span>
    </v-card-title>
    <v-card-text>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-text-field
              v-model="search"
              label="Поиск лобби"
              outlined
              @input="debouncedSearch($event.target.value)"
            ></v-text-field>
            <v-list v-if="!loading">
              <v-list-item
                v-for="lobby in lobbies"
                :key="lobby.id"
                @click="attemptJoinLobby(lobby)"
              >
                <v-list-item-title>
                  {{ lobby.lobbyName }}
                  <v-icon v-if="lobby.isPrivate">mdi-lock</v-icon>
                </v-list-item-title>
              </v-list-item>
            </v-list>
            <v-progress-circular v-else indeterminate></v-progress-circular>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="blue darken-1" @click="closeDialog">Отмена</v-btn>
    </v-card-actions>
    <v-dialog v-model="keyDialog" max-width="290">
      <v-card>
        <v-card-title>
          <span class="text-h5">Введите ключ</span>
        </v-card-title>
        <v-card-text>
          <v-text-field v-model="key" label="Ключ" outlined></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" @click="joinSelectedLobby()">Присоединиться</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script lang="ts">
import { ref, computed } from 'vue'
import { useGameStore } from '../stores/game'
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'
import type Lobby from '@/interfaces/Lobby'
import { debounce } from 'lodash'
import ServerApi from '@/utils/ServerApi'

export default {
  props: {
    findDialog: Boolean
  },
  emits: ['update:findDialog'],
  setup(props, { emit }) {
    const internalDialog = ref(props.findDialog)
    const gameStore = useGameStore()
    const userStore = useUserStore()
    const router = useRouter()
    const api = new ServerApi()
    const lobbies = computed(() => gameStore.lobbies)
    const loading = ref(false)
    const search = ref('')
    const keyDialog = ref(false)
    const key = ref('')
    const selectedLobby = ref<Lobby | null>(null)

    const debouncedSearch = debounce(async (lobbyName: string) => {
      if (lobbyName.trim() === '') {
        await gameStore.loadLobbies()
      } else {
        await gameStore.findLobbyByName(lobbyName)
      }
    }, 300)

    const closeDialog = () => {
      emit('update:findDialog', false)
    }

    const attemptJoinLobby = (lobby: Lobby) => {
      if (lobby.isPrivate) {
        selectedLobby.value = lobby
        keyDialog.value = true
      } else {
        joinLobby(lobby)
      }
    }

    const joinLobby = async (lobby: Lobby) => {
      if (lobby.isPrivate && lobby.key !== key.value) {
        alert('Неверный ключ!')
      } else {
        if (lobby.id) {
          const userId = Number(userStore.currentUser?.id)
          const game = await api.findGameByLobbyId(lobby.id)
          if (game && game.id) {
            await gameStore.addUserToGame(game.id, userId)
            router.push(`/lobbies/${lobby.id}`)
          }
        }
      }
    }

    const joinSelectedLobby = () => {
      if (selectedLobby.value) {
        joinLobby(selectedLobby.value)
      }
    }

    return {
      internalDialog,
      lobbies,
      attemptJoinLobby,
      joinLobby,
      closeDialog,
      loading,
      search,
      debouncedSearch,
      keyDialog,
      key,
      selectedLobby,
      joinSelectedLobby
    }
  }
}
</script>
