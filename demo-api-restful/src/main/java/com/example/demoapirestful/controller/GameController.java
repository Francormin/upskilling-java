package com.example.demoapirestful.controller;

import com.example.demoapirestful.domain.Game;
import com.example.demoapirestful.exception.GameNotFoundException;
import com.example.demoapirestful.service.GameService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Game>> filterGamesByGenre(@RequestParam String genre) {
        List<Game> filteredGames = gameService.filterGamesByGenre(genre);
        return ResponseEntity.ok(filteredGames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Game>> getGameById(@PathVariable Long id) {
        Optional<Game> gameById = gameService.getGameById(id);
        return gameById.isEmpty()
            ? ResponseEntity.notFound().build()
            : ResponseEntity.ok(gameById);
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game createdGame = gameService.createGame(game);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdGame.getId())
            .toUri();

        return ResponseEntity.created(location).body(createdGame);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateGame(@PathVariable Long id, @RequestBody Game game) {
        try {
            gameService.updateGame(id, game);
            return ResponseEntity.ok("Game updated successfully!");
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable Long id) {
        try {
            gameService.deleteGame(id);
            return ResponseEntity.ok("Game deleted successfully!");
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
