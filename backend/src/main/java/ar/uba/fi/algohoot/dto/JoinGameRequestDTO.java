package ar.uba.fi.algohoot.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@GroupSequence({JoinGameRequestDTO.class, JoinGameRequestDTO.NotBlankGroup.class, JoinGameRequestDTO.SizeGroup.class})
public record JoinGameRequestDTO(
    @NotBlank(message = "Username is required", groups = NotBlankGroup.class)
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters", groups = SizeGroup.class)
    String username,

    @NotBlank(message = "The game code is required", groups = NotBlankGroup.class)
    @Size(min = 6, max = 6, message = "The code must be 6 characters long", groups = SizeGroup.class)
    String gameCode
) {
    public interface NotBlankGroup {}
    public interface SizeGroup {}
}
