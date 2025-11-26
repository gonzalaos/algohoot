package ar.uba.fi.algohoot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ar.uba.fi.algohoot.dto.PlayerCreateDTO;
import ar.uba.fi.algohoot.exception.PlayerAlreadyExistsException;
import ar.uba.fi.algohoot.model.Player;
import ar.uba.fi.algohoot.service.PlayerService;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerService playerService;

    @Test
    public void identifyPlayerWithValidDataReturnsCreatedTest() throws Exception {
        Player player = Player.builder().username("Gonza25").build();
        when(playerService.identifyPlayer(any(PlayerCreateDTO.class))).thenReturn(player);
        String jsonContent = """
            {
                "username": "Gonza25"
            }    
            """;
        
        mockMvc.perform(post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("Gonza25"));
    }

    @Test
    public void identifyPlayerWithBlankUsernameReturnsBadRequestTest() throws Exception {
        String jsonContent = """
            {
                "username": ""
            }    
            """;

        mockMvc.perform(post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username is required"));
    }

    @Test
    public void identifyPlayerWithShortUsernameReturnsBadRequestTest() throws Exception {
        String jsonContent = """
            {
                "username": "ab"
            }    
            """;

        mockMvc.perform(post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username must be between 3 and 20 characters"));
    }

    @Test
    public void identifyPlayerWithLongUsernameReturnsBadRequestTest() throws Exception {
        String jsonContent = """
            {
                "username": "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            }    
            """;
    
        mockMvc.perform(post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username must be between 3 and 20 characters"));
    }

    @Test
    public void identifyPlayerWithDuplicateUsernameReturnsConflictTest() throws Exception {
        when(playerService.identifyPlayer(any(PlayerCreateDTO.class)))
            .thenThrow(new PlayerAlreadyExistsException("Player with username Gonza25 already exists"));
        String jsonContent = """
            {
                "username": "Gonza25"
            }    
            """;

        mockMvc.perform(post("/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Player with username Gonza25 already exists"));
    }
}
