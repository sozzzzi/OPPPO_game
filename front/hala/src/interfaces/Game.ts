import type Lobby from './Lobby'
import type User from './User'

export default interface Game {
  id?: number
  lobby: Lobby
  users: User[]
  cardCount: number
  gameInProgress?: boolean
  gameEnded?: Date | null
}
