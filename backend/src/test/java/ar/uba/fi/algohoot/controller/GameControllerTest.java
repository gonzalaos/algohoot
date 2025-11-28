package ar.uba.fi.algohoot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import ar.uba.fi.algohoot.dto.CreateGameRequestDTO;
import ar.uba.fi.algohoot.dto.CreateGameResponseDTO;
import ar.uba.fi.algohoot.exception.PlayerAlreadyExistsException;
import ar.uba.fi.algohoot.service.GameService;
import ar.uba.fi.algohoot.service.PlayerService;

@WebMvcTest(GameController.class)
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerService playerService;

    @MockitoBean
    private GameService gameService;

    @Test
    public void createGameWithValidDataReturnsCreatedAndGameData() throws Exception {
        CreateGameResponseDTO responseDTO = CreateGameResponseDTO.builder().gameId(10L).hostPlayerId(1L).build();

        when(gameService.createGame(any(CreateGameRequestDTO.class))).thenReturn(responseDTO);
       
        String jsonContent = """
            {
                "username": "Gonza25"
            }    
            """;
        
        mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.gameId").value(10L))
                .andExpect(jsonPath("$.hostPlayerId").value(1L));
    }

    @Test
    public void createGameWithEmptyUsernameReturnsBadRequest() throws Exception {
        String jsonContent = """
            {
                "username": ""
            }    
            """;

        mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username is required"));
    }

    @Test
    public void createGameWithShortUsernameReturnsBadRequest() throws Exception {
        String jsonContent = """
            {
                "username": "ab"
            }    
            """;

        mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username must be between 3 and 20 characters"));
    }

    @Test
    public void createGameWithLongUsernameReturnsBadRequest() throws Exception {
        String jsonContent = """
            {
                "username": "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            }    
            """;
    
        mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username must be between 3 and 20 characters"));
    }

    @Test
    public void createGameWithDuplicateUsernameReturnsConflictTest() throws Exception {
        when(gameService.createGame(any(CreateGameRequestDTO.class)))
            .thenThrow(new PlayerAlreadyExistsException("Player with username Gonza25 already exists"));
        String jsonContent = """
            {
                "username": "Gonza25"
            }    
            """;

        mockMvc.perform(post("/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Player with username Gonza25 already exists"));
    }
}
