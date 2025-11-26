package ar.uba.fi.algohoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import ar.uba.fi.algohoot.dto.PlayerCreateDTO;
import ar.uba.fi.algohoot.service.PlayerService;
import ar.uba.fi.algohoot.model.Player;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping
    public ResponseEntity<?> identifyPlayer(@Valid @RequestBody PlayerCreateDTO playerCreateDTO) {
        Player player = playerService.identifyPlayer(playerCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(player);
    }
}
