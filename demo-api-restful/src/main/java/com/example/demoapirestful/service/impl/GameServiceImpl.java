package com.example.demoapirestful.service.impl;

import com.example.demoapirestful.domain.Game;
import com.example.demoapirestful.exception.GameNotFoundException;
import com.example.demoapirestful.repository.GameRepository;
import com.example.demoapirestful.service.GameService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public List<Game> filterGamesByGenre(String genre) {
        return gameRepository.findAll().stream()
            .filter(game -> game.getGenre().toLowerCase().contains(genre.toLowerCase()))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void updateGame(Long id, Game game) {
        Optional<Game> gameById = gameRepository.findById(id);
        if (gameById.isEmpty()) {
            throw new GameNotFoundException((id));
        }
        gameRepository.update(id, game);
    }

    @Override
    public void deleteGame(Long id) {
        Optional<Game> gameById = gameRepository.findById(id);
        if (gameById.isEmpty()) {
            throw new GameNotFoundException((id));
        }
        gameRepository.delete(id);
    }

}
