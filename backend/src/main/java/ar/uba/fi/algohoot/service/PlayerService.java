package ar.uba.fi.algohoot.service;

// import ar.uba.fi.algohoot.exception.PlayerNotExist;
import ar.uba.fi.algohoot.model.Player;
import ar.uba.fi.algohoot.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player createPlayer(String username) {
        Player player = Player.builder().username(username).build();
        return playerRepository.save(player);
    }

    // public Player findPlayer(Long id) {
    //     return playerRepository.findById(id)
    //         .orElseThrow(() -> new PlayerNotExist("Player with id " + id +  " not found"));
    // }
}
