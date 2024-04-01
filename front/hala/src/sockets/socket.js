import express from 'express'
import http from 'http'
import { Server } from 'socket.io'

const app = express()
const server = http.createServer(app)
const io = new Server(server, {
  cors: {
    origin: '*',
    methods: ['GET', 'POST', 'PUT', 'DELETE']
  }
})

io.on('connection', (socket) => {
  console.log('a user connected')

  socket.on('cancel-game', (gameId) => {
    console.log(`Game ${gameId} was cancelled`)
    io.emit('game-cancelled', gameId)
  })

  socket.on('game-started', (gameId) => {
    console.log(`Game ${gameId} has started`)
    io.emit('game-started', gameId)
  })

  socket.on('game ended', (winner) => {
    console.log(`Game ended. Winner: ${winner}`)
    io.emit('game ended', winner)
  })

  socket.on('disconnect', () => {
    console.log('user disconnected')
  })
})

server.listen(3000, () => {
  console.log('listening on *:3000')
})
