package com.example.homeworkjdbctemplate.repository.impl;

import com.example.homeworkjdbctemplate.constants.MovieSqlQueries;
import com.example.homeworkjdbctemplate.model.Movie;
import com.example.homeworkjdbctemplate.repository.MovieRepository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcMovieRepository implements MovieRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> findAll() {
        return jdbcTemplate.query(MovieSqlQueries.SELECT_ALL, new BeanPropertyRowMapper<>(Movie.class));
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return jdbcTemplate.query(
                MovieSqlQueries.SELECT_BY_ID,
                new BeanPropertyRowMapper<>(Movie.class),
                id)
            .stream()
            .findFirst();
    }

    @Override
    public Optional<Movie> findByTitle(String title) {
        return jdbcTemplate.query(
                MovieSqlQueries.SELECT_BY_TITLE,
                new BeanPropertyRowMapper<>(Movie.class),
                title)
            .stream()
            .findFirst();
    }

    /**
     * <p>KeyHolder: GeneratedKeyHolder captures the generated ID after insertion.
     * <p>Auto-Generated Column: The new String[] {"id"} tells the database that the id column
     * will auto-generate the key, which KeyHolder will store.
     * <p>Return the Movie Object: After the insert, set the generated ID on the movie object
     * and return it.
     */
    @Override
    public Movie save(Movie movie) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                MovieSqlQueries.INSERT,
                new String[]{"id"}
            );
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getDirector());
            ps.setInt(3, movie.getReleaseYear());
            return ps;
        }, keyHolder);

        movie.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return movie;
    }

    @Override
    public int update(Long id, Movie movie) {
        return jdbcTemplate.update(
            MovieSqlQueries.UPDATE,
            movie.getTitle(),
            movie.getDirector(),
            movie.getReleaseYear(),
            id
        );
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(MovieSqlQueries.DELETE, id);
    }

}
