package ar.uba.fi.algohoot.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.*;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Player host;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private GameStatus status = GameStatus.WAITING;

    @Column(name = "max_rounds", nullable = false)
    @Builder.Default
    private int maxRounds = 5;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonManagedReference
    private List<GamePlayer> participants = new ArrayList<>();

    public void addPlayer(Player player) {
        GamePlayer gp = GamePlayer.builder()
            .game(this)
            .player(player)
            .build();
        participants.add(gp);
    }
}
