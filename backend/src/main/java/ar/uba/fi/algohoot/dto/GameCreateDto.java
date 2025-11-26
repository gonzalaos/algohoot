package ar.uba.fi.algohoot.dto;

import ar.uba.fi.algohoot.model.Game;
import ar.uba.fi.algohoot.model.Player;
import jakarta.validation.constraints.*;

public record GameCreateDto(
    @NotNull(message = "Id host player is required")
    @Positive(message = "Id host player must be positive")
    Long idHostPlayer
) {
    public Game toEntity(Player hostPlayer) {
        Game game = Game.builder().host(hostPlayer).build();
        game.addPlayer(hostPlayer);
        
        return game;
    }
}
