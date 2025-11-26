package ar.uba.fi.algohoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.algohoot.model.Game;
import ar.uba.fi.algohoot.model.Player;
import ar.uba.fi.algohoot.repository.GameRepository;
import ar.uba.fi.algohoot.dto.GameCreateDto;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerService playerService;

    public Game createGame(GameCreateDto gameCreateDTO) {
        Player host_player = findIdHostPlayer(gameCreateDTO.idHostPlayer());

        Game game = gameCreateDTO.toEntity(host_player);

        return gameRepository.save(game);
    }

    public Player findIdHostPlayer(Long id) {
        return playerService.findPlayer(id);
    }
}
