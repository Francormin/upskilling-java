package com.example.demoapirestful.service;

import com.example.demoapirestful.domain.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {

    List<Game> getAllGames();

    List<Game> filterGamesByGenre(String genre);

    Optional<Game> getGameById(Long id);

    Game createGame(Game game);

    void updateGame(Long id, Game game);

    void deleteGame(Long id);

}
