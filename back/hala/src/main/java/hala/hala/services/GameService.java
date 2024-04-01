package hala.hala.services;

import hala.hala.models.*;
import hala.hala.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final CardRepository cardRepository;
    private final GameUserRepository gameUserRepository;
    private final LobbyRepository lobbyRepository;

    private final UserRepository userRepository;

    @Autowired
    public GameService(GameRepository gameRepository, CardRepository cardRepository, GameUserRepository gameUserRepository, LobbyRepository lobbyRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.cardRepository = cardRepository;
        this.gameUserRepository = gameUserRepository;
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Game createGame(Game game) {
        Lobby savedLobby = lobbyRepository.save(game.getLobby());
        game.setLobby(savedLobby);
        Game savedGame = gameRepository.save(game);
        return savedGame;
    }

    @Transactional
    public void deleteGame(Long gameId) {
        gameRepository.deleteById(gameId);
    }

    public List<Card> getCardsInGame(Long gameId) {
        return cardRepository.findByGame_Id(gameId);
    }

    public List<UserEntity> getPlayersInLobby(Long lobbyId) {
        Optional<Game> gameInLobby = gameRepository.findByLobby_Id(lobbyId);
        List<UserEntity> playersInLobby = new ArrayList<>();
        if (gameInLobby.isPresent()) {
            List<GameUser> gameUsers = gameUserRepository.findByGame_Id(gameInLobby.get().getId());
            for (GameUser gameUser : gameUsers) {
                playersInLobby.add(gameUser.getUserEntity());
            }
        }
        return playersInLobby;
    }

    public Optional<Game> findGameByLobbyName(String lobbyName) {
        return gameRepository.findFirstByLobby_LobbyName(lobbyName);
    }

    public Optional<Game> setCardCount(Long gameId, int cardCount) {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            Game updatedGame = game.get();
            updatedGame.setCardCount(cardCount);
            gameRepository.save(updatedGame);
        }
        return game;
    }

    public Optional<Game> findGameById(Long gameId) {
        return gameRepository.findById(gameId);
    }

    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> findGameByLobbyId(Long lobbyId) {
        return gameRepository.findByLobby_Id(lobbyId);
    }

    @Transactional
    public Game addUserToGame(Long gameId, Long userId) {
        Optional<Game> gameOpt = gameRepository.findById(gameId);
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (gameOpt.isPresent() && userOpt.isPresent()) {
            Game game = gameOpt.get();
            UserEntity user = userOpt.get();
            GameUser gameUser = new GameUser();
            gameUser.setGame(game);
            gameUser.setUserEntity(user);
            gameUserRepository.save(gameUser);
            game.getUsers().add(user);
            gameRepository.save(game);
            return game;
        } else {
            throw new RuntimeException("Game or User not found");
        }
    }
}
