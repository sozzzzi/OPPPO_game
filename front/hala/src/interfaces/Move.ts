import type User from './User'
import type Game from './Game'
import type Card from './Card'

export interface Move {
  id: number
  game: Game
  user: User
  card: Card
  timeStamp: Date
}
