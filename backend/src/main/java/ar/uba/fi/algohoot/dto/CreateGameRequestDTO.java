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
    public Game toEntityGame(Player hostPlayer, String uniqueCode) {
        Game game = Game.builder().playerHost(hostPlayer).code(uniqueCode).build();
        game.addPlayer(hostPlayer);
        return game;
    }

    public interface NotBlankGroup {}
    public interface SizeGroup {}
}
