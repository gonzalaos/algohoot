package ar.uba.fi.algohoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.algohoot.model.Player;
import ar.uba.fi.algohoot.repository.PlayerRepository;
import ar.uba.fi.algohoot.dto.PlayerCreateDTO;
import ar.uba.fi.algohoot.exception.PlayerAlreadyExistsException;
import ar.uba.fi.algohoot.exception.PlayerNotExist;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public Player identifyPlayer(PlayerCreateDTO playerCreateDTO) {
        String username = playerCreateDTO.username();

        if (existsPlayer(username))
            throw new PlayerAlreadyExistsException("Player with username " + username + " already exists");
        
        Player player = playerCreateDTO.toEntity();
        return playerRepository.save(player);
    }

    public boolean existsPlayer(String username) {
        return playerRepository.existsByUsername(username);
    }

    public Player findPlayer(Long id) {
        return playerRepository.findById(id)
            .orElseThrow(() -> new PlayerNotExist("Player with id " + id +  " not found"));
    }
}
