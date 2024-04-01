package hala.hala.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "lobbies")
@Data
public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "lobby_name")
    private String lobbyName;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @Column(name = "key")
    private String key;
}
