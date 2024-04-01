<template>
  <div class="game-page">
    <div class="cards">
      <div
          v-for="card in shuffledCards"
          :key="card.id"
          class="card"
          @click="flipCard(card)"
          :class="{ flipped: card.isFlipped }"
      >
        <img v-if="card.isFlipped" :src="card.imageUrl" alt="Card image" />
        <div v-else class="card-back"></div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, computed } from 'vue'
import { useGameStore } from '../stores/game'
import { useRouter } from 'vue-router'
import { io } from 'socket.io-client'
import type Card from '@/interfaces/Card'
import { useUserStore } from '@/stores/user'

export default defineComponent({
  name: 'GamePage',
  setup() {
    const gameStore = useGameStore()
    const router = useRouter()
    const socket = io('http://localhost:3000')
    const cards = ref<Array<Card>>(gameStore.cards)
    const playerOneTime = ref(0)
    const playerTwoTime = ref(0)
    const playerOneName = ref('Игрок 1')
    const playerTwoName = ref('Игрок 2')
    const currentPlayer = ref(playerOneName.value)
    const playerName = ref('Игрок')
    const currentPlayerTime = computed(() => {
      return currentPlayer.value === playerOneName.value ? playerOneTime.value : playerTwoTime.value
    })
    const currentPlayerName = computed(() => {
      return currentPlayer.value === playerOneName.value ? playerOneName.value : playerTwoName.value
    })
    const flippedCards = ref<Array<Card>>([])
    const userStore = useUserStore()

    const shuffledCards = computed(() => {
      let array = [...cards.value]
      let currentIndex = array.length,
          temporaryValue,
          randomIndex
      while (0 !== currentIndex) {
        randomIndex = Math.floor(Math.random() * currentIndex)
        currentIndex -= 1
        temporaryValue = array[currentIndex]
        array[currentIndex] = array[randomIndex]
        array[randomIndex] = temporaryValue
      }
      return array
    })

    const endGame = () => {
      const winner =
          playerOneTime.value < playerTwoTime.value ? playerOneName.value : playerTwoName.value
      socket.emit('game ended', winner)
      gameStore.deleteLobbyAndGame(
          gameStore.currentGame?.lobby.id!,
          Number(router.currentRoute.value.params.id)
      )
      router.push('/')
    }

    socket.on('game ended', (winner: string) => {
      alert(`Победитель: ${winner}`)
    })

    let timerId: number | null = null

    const startTimer = () => {
      if (timerId) {
        clearInterval(timerId)
      }
      timerId = window.setInterval(() => {
        if (currentPlayer.value === playerOneName.value) {
          playerOneTime.value++
        } else {
          playerTwoTime.value++
        }
      }, 1000)
    }

    const flipCard = (card: Card) => {
      if (flippedCards.value.length < 2) {
        card.isFlipped = true
        flippedCards.value.push(card)
        if (flippedCards.value.length === 2) {
          checkMatch()
        }
      }
    }

    const checkMatch = () => {
      if (flippedCards.value[0].id === flippedCards.value[1].id) {
        flippedCards.value = []
        if (shuffledCards.value.every((card) => card.isFlipped)) {
          endGame()
        }
      } else {
        setTimeout(() => {
          flippedCards.value[0].isFlipped = false
          flippedCards.value[1].isFlipped = false
          flippedCards.value = []
        }, 1000)
      }
      currentPlayer.value =
          currentPlayer.value === playerOneName.value ? playerTwoName.value : playerOneName.value
      startTimer()
    }

    onMounted(async () => {
      await gameStore.startGame(Number(router.currentRoute.value.params.id))
      cards.value = await gameStore.getCards(Number(gameStore.currentGame?.cardCount))
      playerOneName.value = gameStore.currentGame?.users[0].userName!
      playerTwoName.value = gameStore.currentGame?.users[1].userName!
      playerName.value = userStore.currentUser?.userName!
      startTimer()
    })

    return {
      cards,
      shuffledCards,
      playerOneTime,
      playerTwoTime,
      playerOneName,
      playerTwoName,
      currentPlayerTime,
      currentPlayerName,
      playerName,
      flipCard
    }
  }
})
</script>
