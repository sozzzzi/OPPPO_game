package hala.hala.repository;

import hala.hala.models.GameUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameUserRepository extends JpaRepository<GameUser, Long> {
    List<GameUser> findByGame_Id(Long id);
}
