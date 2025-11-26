package ar.uba.fi.algohoot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "game_players", uniqueConstraints = @UniqueConstraint(columnNames = {"game_id", "player_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JsonBackReference
    private Game game;

    @ManyToOne(optional = false)
    private Player player;

    @Column(nullable = false)
    @Builder.Default
    private int score = 0;

    public void addScore(int delta) {
        this.score += delta;
    }
}
