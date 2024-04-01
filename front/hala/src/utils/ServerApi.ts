import type Api from './Api'
import type User from '@/interfaces/User'
import type Game from '@/interfaces/Game'
import type Lobby from '@/interfaces/Lobby'
import type Card from '@/interfaces/Card'
import axiosInstance from '@/plugins/axios'

export default class implements Api {
  async createUser(userEntity: User): Promise<User> {
    try {
      const response = await axiosInstance.post<User>('/users', userEntity)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async deleteUser(userId: number): Promise<void> {
    try {
      await axiosInstance.delete(`/users/${userId}`)
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async findUserByName(userName: string): Promise<User> {
    try {
      const response = await axiosInstance.get<User>(`/users/${userName}`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async createGame(game: Game): Promise<Game> {
    try {
      console.log(game)
      const response = await axiosInstance.post<Game>('/games', game)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async deleteGame(gameId: number): Promise<void> {
    try {
      await axiosInstance.delete(`/games/${gameId}`)
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async getCardsInGame(gameId: number): Promise<Card[]> {
    try {
      const response = await axiosInstance.get<Card[]>(`/games/${gameId}/cards`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async getPlayersInGameLobby(lobbyId: number): Promise<User[]> {
    try {
      const response = await axiosInstance.get<User[]>(`/games/lobby/${lobbyId}/players`)
      console.log(response.data)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async findGameByGameLobbyName(lobbyName: string): Promise<Game> {
    try {
      const response = await axiosInstance.get<Game>(`/games/lobby/${lobbyName}`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async setCardCount(gameId: number, cardCount: number): Promise<Game> {
    try {
      const response = await axiosInstance.put<Game>(`/games/${gameId}/cardCount/${cardCount}`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async createLobby(lobby: Lobby): Promise<Lobby> {
    try {
      const response = await axiosInstance.post<Lobby>('/lobbies', lobby)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async deleteLobby(lobbyId: number): Promise<void> {
    try {
      await axiosInstance.delete(`/lobbies/${lobbyId}`)
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async getPlayersInLobby(lobbyId: number): Promise<Game[]> {
    try {
      const response = await axiosInstance.get<Game[]>(`/lobbies/${lobbyId}/players`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async findGameByLobbyName(lobbyName: string): Promise<Game> {
    try {
      const response = await axiosInstance.get<Game>(`/lobbies/game/${lobbyName}`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async findLobbyByName(lobbyName: string): Promise<Lobby> {
    try {
      const response = await axiosInstance.get<Lobby>(`/lobbies/name/${lobbyName}`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async findAllActiveLobbies(): Promise<Lobby[]> {
    try {
      const response = await axiosInstance.get<Lobby[]>(`/lobbies/active`)
      console.log(response.data)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async findWaitingLobbiesByName(lobbyName: string): Promise<Lobby[]> {
    try {
      console.log(lobbyName)
      const response = await axiosInstance.get<Lobby[]>(`/lobbies/active/${lobbyName}`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async findGameByLobbyId(lobbyId: number): Promise<Game> {
    try {
      const response = await axiosInstance.get<Game>(`/games/lobby/id/${lobbyId}`)
      console.log(response.data)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async findLobbyById(lobbyId: number): Promise<Lobby> {
    try {
      console.log(lobbyId)
      const response = await axiosInstance.get<Lobby>(`/lobbies/id/${lobbyId}`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async findGameByLobbyNameWithGame(lobbyName: string): Promise<Game> {
    try {
      const response = await axiosInstance.get<Game>(`/games/lobby/name/${lobbyName}`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async addUserToGame(gameId: number, userId: number): Promise<Game> {
    try {
      const response = await axiosInstance.post<Game>(`/games/${gameId}/users/${userId}`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async getGame(gameId: number): Promise<Game> {
    try {
      const response = await axiosInstance.get<Game>(`/games/id/${gameId}`)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }

  async getUniqueCards(count: number): Promise<Card[]> {
    try {
      console.log(count)
      const response = await axiosInstance.get<Card[]>(`/cards/unique/${count}`)
      console.log(response.data)
      return response.data
    } catch (error) {
      console.error(error)
      throw error
    }
  }
}
