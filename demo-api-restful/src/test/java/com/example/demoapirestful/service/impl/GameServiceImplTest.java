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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("Test para el caso de éxito al crear un registro Game")
    void testCreateGame_Success() {
        // GIVEN
        Game game = new Game();
        game.setTitle("Fallout 4");
        game.setGenre("First-Person Shooter");
        game.setPrice(45.00);

        Game savedGame = new Game();
        savedGame.setId(1L);
        savedGame.setTitle(game.getTitle());
        savedGame.setGenre(game.getGenre());
        savedGame.setPrice(game.getPrice());

        when(gameRepository.save(any(Game.class))).thenReturn(savedGame);

        // WHEN
        Game result = gameServiceImpl.createGame(game);

        // THEN
        assertEquals(1L, result.getId());
        assertEquals("Fallout 4", result.getTitle());
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    @DisplayName("Test para el caso de error al actualizar un registro Game")
    void testUpdateGame_NotFound() {
        // GIVEN
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(GameNotFoundException.class, () -> gameServiceImpl.updateGame(1L, any(Game.class)));
        verify(gameRepository, times(1)).findById(1L);
        verify(gameRepository, times(0)).update(anyLong(), any(Game.class));
    }

}
