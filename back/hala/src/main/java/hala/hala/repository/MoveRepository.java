package hala.hala.repository;

import hala.hala.models.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
    List<Move> findByGame_Id(Long gameId);
    Optional<Move> findByUserEntity_Id(Long userId);
}