package ar.uba.fi.algohoot.dto;

import ar.uba.fi.algohoot.model.Game;
import ar.uba.fi.algohoot.model.Player;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@GroupSequence({ CreateGameRequestDTO.class, CreateGameRequestDTO.NotBlankGroup.class, CreateGameRequestDTO.SizeGroup.class })
public record CreateGameRequestDTO(
    @NotBlank(message = "Username is required", groups = NotBlankGroup.class) 
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters", groups = SizeGroup.class) 
    String username
) {
    public Player toEntityPlayer() {
        return Player.builder().username(username).build();
    }

    public Game toEntityGame(Player hostPlayer) {
        Game game = Game.builder().host(hostPlayer).build();
        game.addPlayer(hostPlayer);
        return game;
    }

    public interface NotBlankGroup {}
    public interface SizeGroup {}
}
