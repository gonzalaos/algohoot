package ar.uba.fi.algohoot.repository;

import ar.uba.fi.algohoot.model.Player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    // boolean existsByUsername(String username);
}
