package com.example.demoapirestful.service.impl;

import com.example.demoapirestful.domain.Game;
import com.example.demoapirestful.exception.GameNotFoundException;
import com.example.demoapirestful.repository.GameRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    @Test
    @DisplayName("Test for retrieving all games")
    void testGetAllGames() {
        // GIVEN
        Game game1 = new Game(1L, "Game1", "RPG", 60.00);
        Game game2 = new Game(2L, "Game2", "Action", 50.00);

        when(gameRepository.findAll()).thenReturn(Arrays.asList(game1, game2));

        // WHEN
        List<Game> games = gameServiceImpl.getAllGames();

        // THEN
        assertEquals(2, games.size());
        assertEquals("Game1", games.get(0).getTitle());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test for filtering games by genre")
    void testFilterGamesByGenre() {
        // GIVEN
        Game game1 = new Game(1L, "Game1", "RPG", 60.00);
        Game game2 = new Game(2L, "Game2", "Action", 50.00);
        Game game3 = new Game(3L, "Game3", "RPG", 45.00);

        when(gameRepository.findAll()).thenReturn(Arrays.asList(game1, game2, game3));

        // WHEN
        List<Game> rpgGames = gameServiceImpl.filterGamesByGenre("RPG");

        // THEN
        assertEquals(2, rpgGames.size());
        assertTrue(rpgGames.stream().allMatch(game -> game.getGenre().toLowerCase().contains("RPG".toLowerCase())));
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test for retrieving a game by ID")
    void testGetGameById_Success() {
        // GIVEN
        Game game = new Game(1L, "Game1", "RPG", 60.00);
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        // WHEN
        Optional<Game> foundGame = gameServiceImpl.getGameById(1L);

        // THEN
        assertTrue(foundGame.isPresent());
        assertEquals("Game1", foundGame.get().getTitle());
        verify(gameRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test for game not found by ID")
    void testGetGameById_NotFound() {
        // GIVEN
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN
        Optional<Game> foundGame = gameServiceImpl.getGameById(1L);

        // THEN
        assertFalse(foundGame.isPresent());
        verify(gameRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test for successful game creation")
    void testCreateGame_Success() {
        // GIVEN
        Game game = new Game();
        game.setTitle("NewGame");
        game.setGenre("RPG");
        game.setPrice(40.00);

        Game savedGame = new Game(1L, game.getTitle(), game.getGenre(), game.getPrice());
        when(gameRepository.save(any(Game.class))).thenReturn(savedGame);

        // WHEN
        Game result = gameServiceImpl.createGame(game);

        // THEN
        assertEquals(1L, result.getId());
        assertEquals("NewGame", result.getTitle());
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    @DisplayName("Test for successful game update")
    void testUpdateGame_Success() {
        // GIVEN
        Long gameId = 1L;
        Game existingGame = new Game(gameId, "Old Title", "Old Genre", 40.00);
        Game updatedGame = new Game(gameId, "New Title", "New Genre", 50.00);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(existingGame));

        // WHEN
        gameServiceImpl.updateGame(gameId, updatedGame);

        // THEN
        verify(gameRepository, times(1)).findById(gameId);
        verify(gameRepository, times(1)).update(gameId, updatedGame);
    }

    @Test
    @DisplayName("Test for updating a game that does not exist")
    void testUpdateGame_NotFound() {
        // GIVEN
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(GameNotFoundException.class, () -> gameServiceImpl.updateGame(1L, any(Game.class)));
        verify(gameRepository, times(1)).findById(1L);
        verify(gameRepository, times(0)).update(anyLong(), any(Game.class));
    }

    @Test
    @DisplayName("Test for successful game deletion")
    void testDeleteGame_Success() {
        // GIVEN
        when(gameRepository.findById(1L)).thenReturn(Optional.of(new Game()));

        // WHEN
        gameServiceImpl.deleteGame(1L);

        // THEN
        verify(gameRepository, times(1)).findById(1L);
        verify(gameRepository, times(1)).delete(1L);
    }

    @Test
    @DisplayName("Test for deleting a game that does not exist")
    void testDeleteGame_NotFound() {
        // GIVEN
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(GameNotFoundException.class, () -> gameServiceImpl.deleteGame(1L));
        verify(gameRepository, times(1)).findById(1L);
        verify(gameRepository, times(0)).delete(anyLong());
    }

}
