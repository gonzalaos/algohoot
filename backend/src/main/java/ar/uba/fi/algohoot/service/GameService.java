package ar.uba.fi.algohoot.service;

import ar.uba.fi.algohoot.dto.CreateGameRequestDTO;
import ar.uba.fi.algohoot.dto.CreateGameResponseDTO;
import ar.uba.fi.algohoot.model.Game;
import ar.uba.fi.algohoot.model.Player;
import ar.uba.fi.algohoot.repository.GameRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    private final PlayerService playerService;

    @Transactional
    public CreateGameResponseDTO createGame(CreateGameRequestDTO requestDTO) {
        Player hostPlayer = playerService.identifyPlayer(requestDTO);

        Game newGame = requestDTO.toEntityGame(hostPlayer);
        newGame = gameRepository.save(newGame);

        return CreateGameResponseDTO.builder().gameId(newGame.getId()).hostPlayerId(hostPlayer.getId()).build();
    }

    public Player findIdHostPlayer(Long id) {
        return playerService.findPlayer(id);
    }
}
