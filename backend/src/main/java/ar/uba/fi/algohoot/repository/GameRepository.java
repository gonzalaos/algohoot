package ar.uba.fi.algohoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.uba.fi.algohoot.model.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
