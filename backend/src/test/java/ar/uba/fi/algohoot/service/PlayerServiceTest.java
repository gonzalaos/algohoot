package ar.uba.fi.algohoot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ar.uba.fi.algohoot.dto.PlayerCreateDTO;
import ar.uba.fi.algohoot.exception.PlayerAlreadyExistsException;
import ar.uba.fi.algohoot.model.Player;
import ar.uba.fi.algohoot.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    public void identifyNewPlayerTest() {
        PlayerCreateDTO playerCreateDTO = new PlayerCreateDTO("Gonza25");
        Player expected = Player.builder().username("Gonza25").build();
        when(playerRepository.existsByUsername("Gonza25")).thenReturn(false);
        when(playerRepository.save(any(Player.class))).thenReturn(expected);

        Player result = playerService.identifyPlayer(playerCreateDTO);

        assertEquals(expected.getUsername(), result.getUsername());
        verify(playerRepository).existsByUsername("Gonza25");
        verify(playerRepository).save(any(Player.class));
    }    

    @Test
    public void identifyPlayerWithDuplicateUsernameThrowsExceptionTest() {
        PlayerCreateDTO playerCreateDTO = new PlayerCreateDTO("Gonza25");
        when(playerRepository.existsByUsername("Gonza25")).thenReturn(true);

        PlayerAlreadyExistsException exception = assertThrows(
            PlayerAlreadyExistsException.class,
            () -> playerService.identifyPlayer(playerCreateDTO)
        );

        assertEquals("Player with username Gonza25 already exists", exception.getMessage());
        verify(playerRepository).existsByUsername("Gonza25");
        verify(playerRepository, never()).save(any(Player.class));
    }
}
