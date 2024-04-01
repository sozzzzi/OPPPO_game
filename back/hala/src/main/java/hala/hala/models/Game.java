package hala.hala.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "games")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lobby_id")
    private Lobby lobby;

    @ManyToMany
    @JoinTable(
            name = "game_user",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> users;

    private Integer cardCount;

    @Column(name = "game_in_progress")
    private Boolean gameInProgress;

    @Column(name = "game_ended")
    private Timestamp gameEnded;

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", lobby=" + lobby +
                ", cardCount=" + cardCount +
                ", gameInProgress=" + gameInProgress +
                ", gameEnded=" + gameEnded +
                '}';
    }
}
