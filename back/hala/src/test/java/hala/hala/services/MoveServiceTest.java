package hala.hala.services;

import hala.hala.models.Move;
import hala.hala.repository.MoveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MoveServiceTest {

    @Mock
    private MoveRepository moveRepository;

    @InjectMocks
    private MoveService moveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createMove() {
        Move move = new Move();
        when(moveRepository.save(any(Move.class))).thenReturn(move);
        Move createdMove = moveService.createMove(new Move());
        assertEquals(move, createdMove);
        verify(moveRepository, times(1)).save(any(Move.class));
    }

    @Test
    void deleteMove() {
        Long moveId = 1L;
        moveService.deleteMove(moveId);
        verify(moveRepository, times(1)).deleteById(moveId);
    }

    @Test
    void getMovesInGame() {
        Long gameId = 1L;
        List<Move> moves = new ArrayList<>();
        moves.add(new Move());
        when(moveRepository.findByGame_Id(gameId)).thenReturn(moves);
        List<Move> retrievedMoves = moveService.getMovesInGame(gameId);
        assertEquals(moves, retrievedMoves);
        verify(moveRepository, times(1)).findByGame_Id(gameId);
    }

    @Test
    void findMoveByUserId() {
        Long userId = 1L;
        Optional<Move> move = Optional.of(new Move());
        when(moveRepository.findByUserEntity_Id(userId)).thenReturn(move);
        Optional<Move> foundMove = moveService.findMoveByUserId(userId);
        assertEquals(move, foundMove);
        verify(moveRepository, times(1)).findByUserEntity_Id(userId);
    }
}
