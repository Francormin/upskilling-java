package com.example.demoapirestful.repository.impl;

import com.example.demoapirestful.domain.Game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private GameRepositoryImpl gameRepositoryImpl;

    @Test
    @DisplayName("Test find all games")
    void testFindAllGames() {
        // GIVEN
        Game game1 = new Game();
        game1.setId(1L);
        game1.setTitle("The Witcher 3");
        game1.setGenre("RPG");
        game1.setPrice(49.99);

        Game game2 = new Game();
        game2.setId(2L);
        game2.setTitle("Cyberpunk 2077");
        game2.setGenre("RPG");
        game2.setPrice(59.99);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
            .thenReturn(List.of(game1, game2));

        // WHEN
        List<Game> games = gameRepositoryImpl.findAll();

        // THEN
        assertEquals(2, games.size());
        assertEquals("The Witcher 3", games.get(0).getTitle());
        assertEquals("Cyberpunk 2077", games.get(1).getTitle());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class));
    }

    @Test
    @DisplayName("Test find game by ID")
    void testFindById() {
        // GIVEN
        Game game = new Game();
        game.setId(1L);
        game.setTitle("Cyberpunk 2077");
        game.setGenre("RPG");
        game.setPrice(59.99);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
            .thenReturn(List.of(game));

        // WHEN
        Optional<Game> foundGame = gameRepositoryImpl.findById(1L);

        // THEN
        assertTrue(foundGame.isPresent());
        assertEquals("Cyberpunk 2077", foundGame.get().getTitle());
        assertEquals("RPG", foundGame.get().getGenre());
        assertEquals(59.99, foundGame.get().getPrice());
        verify(jdbcTemplate, times(1)).query(anyString(), any(RowMapper.class), eq(1L));
    }

    @Test
    @DisplayName("Test save game")
    void testSaveGame() {
        // GIVEN
        Game game = new Game();
        game.setTitle("The Witcher 3");
        game.setGenre("RPG");
        game.setPrice(49.99);

        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
            .thenAnswer(invocation -> {
                KeyHolder providedKeyHolder = invocation.getArgument(1, KeyHolder.class);
                providedKeyHolder.getKeyList().add(Map.of("id", 1L));
                return 1;
            });

        // WHEN
        Game savedGame = gameRepositoryImpl.save(game);

        // THEN
        assertNotNull(savedGame.getId());
        assertEquals(1L, savedGame.getId());
        assertEquals("The Witcher 3", savedGame.getTitle());
        assertEquals("RPG", savedGame.getGenre());
        assertEquals(49.99, savedGame.getPrice());
        verify(jdbcTemplate, times(1))
            .update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    @DisplayName("Test update game")
    void testUpdateGame() {
        // GIVEN
        Game game = new Game();
        game.setId(1L);
        game.setTitle("Far Cry 6");
        game.setGenre("FPS");
        game.setPrice(69.99);

        when(jdbcTemplate.update(anyString(), any(), any(), any(), anyLong()))
            .thenReturn(1);

        // WHEN
        gameRepositoryImpl.update(1L, game);

        // THEN
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), anyLong());
    }

    @Test
    @DisplayName("Test delete game")
    void testDeleteGame() {
        // GIVEN
        Game game = new Game();
        game.setId(1L);
        game.setTitle("Red Dead Redemption 2");
        game.setGenre("Adventure");
        game.setPrice(14.99);

        when(jdbcTemplate.update(anyString(), anyLong()))
            .thenReturn(1);

        // WHEN
        gameRepositoryImpl.delete(1L);

        // THEN
        verify(jdbcTemplate, times(1)).update(anyString(), anyLong());
    }

}
