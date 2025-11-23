package ar.uba.fi.algohoot.dto;

import ar.uba.fi.algohoot.model.Player;
import jakarta.validation.constraints.*;

public record PlayerCreateDTO(
    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username must not exceed 20 characters")
    String username
) {
    public Player toEntity() {
        Player player = Player.builder().username(username).build();
        return player;
    }
}
