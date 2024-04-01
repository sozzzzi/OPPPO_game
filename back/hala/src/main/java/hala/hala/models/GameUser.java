package hala.hala.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "game_user")
@Data
public class GameUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
