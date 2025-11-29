package ar.uba.fi.algohoot.repository;

import ar.uba.fi.algohoot.model.Game;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsByCode(String code);

    Optional<Game> findByCode(String code);
}
