<template>
  <v-container>
    <v-row>
      <v-col cols="6">
        <v-card>
          <v-card-title>{{ lobby?.lobbyName }}</v-card-title>
          <v-card-text>
            <p v-if="lobby?.isPrivate">Ключ: {{ lobby?.key }}</p>
            <p v-else>Лобби открыто</p>
            <p>Количество карт: {{ game?.cardCount }}</p>
          </v-card-text>
          <v-card-actions class="justify-center">
            <v-btn color="blue darken-1" @click="startGame">Начать игру</v-btn>
            <v-btn color="red darken-1" @click="cancelGame">Отменить игру</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
      <v-col cols="6">
        <v-list>
          <v-list-item-subtitle>Игроки</v-list-item-subtitle>
          <v-list-item v-for="user in users" :key="user.id">
            <v-list-item-title>{{ user.userName }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useGameStore } from '../stores/game'
import { useRouter } from 'vue-router'
import type Lobby from '../interfaces/Lobby'
import type User from '../interfaces/User'
import type Game from '../interfaces/Game'
import { io } from 'socket.io-client'

export default {
  setup() {
    const gameStore = useGameStore()
    const router = useRouter()

    const lobby = ref<Lobby | null>(null)
    const game = ref<Game | null>(null)
    const users = ref<User[]>([])

    let intervalId: number | null = null
    const socket = io('http://localhost:3000')

    onMounted(async () => {
      const lobbyId = Number(router.currentRoute.value.params.id)
      if (lobbyId) {
        lobby.value = await gameStore.findLobbyById(lobbyId)
        game.value = await gameStore.findGameByLobbyId(lobbyId)
        users.value = game.value ? game.value.users : []
        intervalId = setInterval(async () => {
          if (game.value?.id) {
            users.value = await gameStore.getPlayersInLobby(game.value.id)
          }
        }, 5000)
      }
      socket.on('game-cancelled', (cancelledGameId: string) => {
        if (game.value?.id === Number(cancelledGameId)) {
          router.push('/')
        }
      })
      socket.on('game-started', (gameId: string) => {
        if (game.value?.id === Number(gameId)) {
          router.push(`/game/${gameId}`)
        }
      })
    })

    onUnmounted(() => {
      if (intervalId !== null) {
        clearInterval(intervalId)
      }
      socket.on('game-cancelled', (cancelledGameId: string) => {
        if (game.value?.id === Number(cancelledGameId)) {
          router.push('/')
        }
      })
    })

    const cancelGame = async () => {
      if (lobby.value?.id && game.value?.id) {
        await gameStore.deleteLobbyAndGame(lobby.value.id, game.value.id)
        socket.emit('cancel-game', game.value.id)
        router.push('/')
      }
    }

    const startGame = async () => {
      console.log('asd')
      if (lobby.value?.id && game.value?.id) {
        console.log('asd2')
        gameStore.startGame(game.value.id)
        socket.emit('game-started', game.value.id)
      }
    }

    return {
      lobby,
      game,
      users,
      cancelGame,
      startGame
    }
  }
}
</script>
