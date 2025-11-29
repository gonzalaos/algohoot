package ar.uba.fi.algohoot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.uba.fi.algohoot.dto.CreateGameRequestDTO;
import ar.uba.fi.algohoot.model.Player;
import ar.uba.fi.algohoot.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    public void createNewPlayerTest() {
        CreateGameRequestDTO requestDTO = new CreateGameRequestDTO("Gonza25");
        Player expected = Player.builder().username("Gonza25").build();
        when(playerRepository.save(any(Player.class))).thenReturn(expected);

        Player result = playerService.createPlayer(requestDTO.username());

        assertEquals(expected.getUsername(), result.getUsername());
        verify(playerRepository).save(any(Player.class));
    }    
}
