package ar.uba.fi.algohoot.service;

import ar.uba.fi.algohoot.dto.CreateGameRequestDTO;
import ar.uba.fi.algohoot.dto.CreateGameResponseDTO;
import ar.uba.fi.algohoot.dto.JoinGameRequestDTO;
import ar.uba.fi.algohoot.exception.GameCodeNotExist;
import ar.uba.fi.algohoot.exception.PlayerAlreadyExistsException;
import ar.uba.fi.algohoot.model.Game;
import ar.uba.fi.algohoot.model.GameStatus;
import ar.uba.fi.algohoot.model.Player;
import ar.uba.fi.algohoot.repository.GameRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    private final PlayerService playerService;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
    private static final int CODE_LENGTH = 6;
    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public CreateGameResponseDTO createGame(CreateGameRequestDTO requestDTO) {
        Player hostPlayer = playerService.createPlayer(requestDTO.username());
        String uniqueCode = generateUniqueCode();
        Game newGame = requestDTO.toEntityGame(hostPlayer, uniqueCode);
        newGame = gameRepository.save(newGame);

        return CreateGameResponseDTO.builder()
            .gameId(newGame.getId())
            .hostPlayerId(hostPlayer.getId())
            .gameCode(newGame.getCode())
            .build();
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = generateRandomString();
        } while(gameRepository.existsByCode(code));

        return code;
    }

    private String generateRandomString() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for(int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    @Transactional
    public CreateGameResponseDTO joinGame(JoinGameRequestDTO requestDTO) {
        Game game = gameRepository.findByCode(requestDTO.gameCode())
            .orElseThrow(() -> new GameCodeNotExist("No game was found with the code: " + requestDTO.gameCode()));

        if(game.getStatus() != GameStatus.WAITING)
            throw new IllegalStateException("Game has already started");

        if(game.isUsernameTaken(requestDTO.username()))
            throw new PlayerAlreadyExistsException("The username " + requestDTO.username() + " is already taken in this game.");

        Player newPlayer = playerService.createPlayer(requestDTO.username());
        
        game.addPlayer(newPlayer);
        gameRepository.save(game);
        return CreateGameResponseDTO.builder()
            .gameId(game.getId())
            .hostPlayerId(game.getId())
            .gameCode(game.getCode())
            .build();
    }
}
