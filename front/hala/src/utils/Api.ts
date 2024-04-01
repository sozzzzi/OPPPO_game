import type User from '@/interfaces/User'
import type Game from '@/interfaces/Game'
import type Lobby from '@/interfaces/Lobby'
import type Card from '@/interfaces/Card'

export default interface Api {
  createUser(userEntity: User): Promise<User>
  deleteUser(userId: number): Promise<void>
  findUserByName(userName: string): Promise<User>
  createGame(game: Game): Promise<Game>
  deleteGame(gameId: number): Promise<void>
  getCardsInGame(gameId: number): Promise<Card[]>
  getPlayersInGameLobby(lobbyId: number): Promise<User[]>
  findGameByGameLobbyName(lobbyName: string): Promise<Game>
  setCardCount(gameId: number, cardCount: number): Promise<Game>
  createLobby(lobby: Lobby): Promise<Lobby>
  deleteLobby(lobbyId: number): Promise<void>
  getPlayersInLobby(lobbyId: number): Promise<Game[]>
  findGameByLobbyName(lobbyName: string): Promise<Game>
  findLobbyByName(lobbyName: string): Promise<Lobby>
  findAllActiveLobbies(): Promise<Lobby[]>
  findWaitingLobbiesByName(lobbyName: string): Promise<Lobby[]>
  findLobbyById(lobbyId: number): Promise<Lobby>
  findGameByLobbyId(lobbyId: number): Promise<Game>
  findGameByLobbyNameWithGame(lobbyName: string): Promise<Game>
  addUserToGame(gameId: number, userId: number): Promise<Game>
  getUniqueCards(count: number): Promise<Card[]>
  getGame(gameId: number): Promise<Game>
}
