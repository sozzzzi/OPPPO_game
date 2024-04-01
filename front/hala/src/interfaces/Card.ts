import type Game from './Game'

export default interface Card {
  id: number
  isFlipped: boolean
  imageUrl: string
}
