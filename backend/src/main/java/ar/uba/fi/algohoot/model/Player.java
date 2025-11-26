package ar.uba.fi.algohoot.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "players")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String username;
}
