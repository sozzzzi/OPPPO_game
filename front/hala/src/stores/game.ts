import { defineStore } from 'pinia'
import type Game from '@/interfaces/Game'
import type Lobby from '@/interfaces/Lobby'
import type User from '@/interfaces/User'
import type Card from '@/interfaces/Card'
import ServerApi from '@/utils/ServerApi'

const api = new ServerApi()

export const useGameStore = defineStore({
  id: 'game',
  state: () => ({
    currentGame: null as Game | null,
    lobbies: [] as Lobby[],
    cards: [] as Card[]
  }),
  actions: {
    async createGame(lobby: Lobby, users: User[], cardCount: number) {
      const game: Game = {
        lobby: lobby,
        users: users,
        cardCount: cardCount,
        gameInProgress: false,
        gameEnded: null
      }
      const createdGame = await api.createGame(game)
      this.currentGame = createdGame
      return createdGame
    },
    async loadLobbies() {
      console.log('im here')
      const lobbies = await api.findAllActiveLobbies()
      this.lobbies = lobbies
    },
    async findLobbyByName(lobbyName: string) {
      const lobbies = await api.findWaitingLobbiesByName(lobbyName)
      this.lobbies = lobbies
    },
    async findLobbyById(lobbyId: number) {
      const lobby = await api.findLobbyById(lobbyId)
      return lobby
    },
    async findGameByLobbyId(lobbyId: number) {
      const game = await api.findGameByLobbyId(lobbyId)
      return game
    },
    async deleteLobbyAndGame(lobbyId: number, gameId: number) {
      await api.deleteGame(gameId)
      await api.deleteLobby(lobbyId)
    },
    async getPlayersInLobby(gameId: number) {
      const game = await api.getGame(gameId)
      return game.users
    },
    async addUserToGame(gameId: number, userId: number) {
      const updatedGame = await api.addUserToGame(gameId, userId)
      if (this.currentGame && this.currentGame.id === gameId) {
        this.currentGame = updatedGame
      }
      return updatedGame
    },
    async getCards(count: number) {
      const cards = await api.getUniqueCards(count)
      this.cards = cards
      return cards
    },
    async startGame(gameId: number) {
      const game = await api.getGame(gameId)
      this.currentGame = game
      return game.users
    }
  }
})
