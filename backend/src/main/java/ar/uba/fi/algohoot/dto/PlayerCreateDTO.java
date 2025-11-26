package ar.uba.fi.algohoot.dto;

import ar.uba.fi.algohoot.model.Player;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;

@GroupSequence({PlayerCreateDTO.class, PlayerCreateDTO.NotBlankGroup.class, PlayerCreateDTO.SizeGroup.class})
public record PlayerCreateDTO(
    @NotBlank(message = "Username is required", groups = NotBlankGroup.class)
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters", groups = SizeGroup.class)
    String username
) {
    public Player toEntity() {
        Player player = Player.builder().username(username).build();
        return player;
    }

    public interface NotBlankGroup {}
    public interface SizeGroup {}
}
