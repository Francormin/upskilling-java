package com.example.demoapirestful.repository.impl;

import com.example.demoapirestful.domain.Game;
import com.example.demoapirestful.repository.GameRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class GameRepositoryImpl implements GameRepository {

    private final static String SELECT_ALL_GAMES = "SELECT * FROM games";
    private final static String SELECT_GAME_BY_ID = "SELECT * FROM games WHERE id = ?";
    private final static String INSERT_GAME = "INSERT INTO games (title, genre, price) VALUES (?, ?, ?)";
    private final static String UPDATE_GAME = "UPDATE games SET title = ?, genre = ?, price = ? WHERE id = ?";
    private final static String DELETE_GAME = "DELETE FROM games WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public GameRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Game> rowMapper = (rs, rowNum) -> {
        Game game = new Game();
        game.setId(rs.getLong("id"));
        game.setTitle(rs.getString("title"));
        game.setGenre(rs.getString("genre"));
        game.setPrice(rs.getDouble("price"));
        return game;
    };

    @Override
    public List<Game> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GAMES, rowMapper);
    }

    @Override
    public Optional<Game> findById(Long id) {
        return jdbcTemplate
            .query(SELECT_GAME_BY_ID, rowMapper, id)
            .stream()
            .findFirst();
    }

    @Override
    public Game save(Game game) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_GAME, new String[]{"id"});
            ps.setString(1, game.getTitle());
            ps.setString(2, game.getGenre());
            ps.setDouble(3, game.getPrice());
            return ps;
        }, keyHolder);

        game.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return game;
    }

    @Override
    public void update(Long id, Game game) {
        jdbcTemplate.update(UPDATE_GAME, game.getTitle(), game.getGenre(), game.getPrice(), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_GAME, id);
    }

}
