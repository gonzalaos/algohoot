package ar.uba.fi.algohoot.dto;

import lombok.Builder;

@Builder
public record CreateGameResponseDTO(
    Long gameId,
    // String gameCode
    Long hostPlayerId
) {
}
