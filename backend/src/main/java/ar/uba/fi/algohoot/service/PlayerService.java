package ar.uba.fi.algohoot.service;

import ar.uba.fi.algohoot.dto.CreateGameRequestDTO;
import ar.uba.fi.algohoot.exception.PlayerAlreadyExistsException;
import ar.uba.fi.algohoot.exception.PlayerNotExist;
import ar.uba.fi.algohoot.model.Player;
import ar.uba.fi.algohoot.repository.PlayerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player identifyPlayer(CreateGameRequestDTO requestDTO) {
        String username = requestDTO.username();
        if (existsPlayer(username))
            throw new PlayerAlreadyExistsException("Player with username " + username + " already exists");
        
        Player player = requestDTO.toEntityPlayer();
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
