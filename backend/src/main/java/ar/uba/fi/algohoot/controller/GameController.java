package ar.uba.fi.algohoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import ar.uba.fi.algohoot.service.GameService;
import ar.uba.fi.algohoot.dto.GameCreateDto;
import ar.uba.fi.algohoot.model.Game;

@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping
    public ResponseEntity<?> createGame(@Valid @RequestBody GameCreateDto gameCreateDTO) {
        Game game = gameService.createGame(gameCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }
}
