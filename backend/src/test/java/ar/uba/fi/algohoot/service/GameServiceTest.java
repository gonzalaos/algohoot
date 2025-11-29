package ar.uba.fi.algohoot.service;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import ar.uba.fi.algohoot.dto.CreateGameRequestDTO;
import ar.uba.fi.algohoot.dto.CreateGameResponseDTO;
import ar.uba.fi.algohoot.model.Game;
import ar.uba.fi.algohoot.model.Player;
import ar.uba.fi.algohoot.repository.GameRepository;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private GameService gameService;

    @Test
    public void createNewGameTest() {
        CreateGameRequestDTO requestDTO = new CreateGameRequestDTO("Gonza25");
        Player playerHost = Player.builder().id(1L).username(requestDTO.username()).build();
        Game savedGame = Game.builder().id(1L).playerHost(playerHost).code("ANYCODE").build();
        CreateGameResponseDTO expected = CreateGameResponseDTO.builder()
            .gameId(savedGame.getId()).hostPlayerId(playerHost.getId()).gameCode(savedGame.getCode()).build();
        when(playerService.createPlayer(requestDTO.username())).thenReturn(playerHost);
        when(gameRepository.existsByCode(anyString())).thenReturn(false);
        when(gameRepository.save(any(Game.class))).thenReturn(savedGame);

        CreateGameResponseDTO result = gameService.createGame(requestDTO);

        assertEquals(expected.gameId(), result.gameId());

        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository).save(gameArgumentCaptor.capture());
        Game gameSentToDb = gameArgumentCaptor.getValue();
        assertNotNull(gameSentToDb.getCode(), "The service should have generated a code");
        assertEquals(6, gameSentToDb.getCode().length(), "The generated code must have 6 characters");
    }
}
