package com.example.demoapirestful.repository;

import com.example.demoapirestful.domain.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

    List<Game> findAll();

    Optional<Game> findById(Long id);

    Game save(Game game);

    void update(Long id, Game game);

    void delete(Long id);

}
