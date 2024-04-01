package hala.hala.services;

import hala.hala.models.Move;
import hala.hala.repository.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MoveService {
    private final MoveRepository moveRepository;

    @Autowired
    public MoveService(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    @Transactional
    public Move createMove(Move move) {
        return moveRepository.save(move);
    }

    @Transactional
    public void deleteMove(Long moveId) {
        moveRepository.deleteById(moveId);
    }

    public List<Move> getMovesInGame(Long gameId) {
        return moveRepository.findByGame_Id(gameId);
    }

    public Optional<Move> findMoveByUserId(Long userId) {
        return moveRepository.findByUserEntity_Id(userId);
    }
}
