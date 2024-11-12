package com.example.homeworkjdbctemplate.repository.impl;

import com.example.homeworkjdbctemplate.model.Movie;
import com.example.homeworkjdbctemplate.repository.MovieRepository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMovieRepository implements MovieRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_MOVIES = "SELECT * FROM movies";
    private static final String GET_MOVIE_BY_ID = "SELECT * FROM movies WHERE id = ?";
    private static final String GET_MOVIE_BY_TITLE = "SELECT * FROM movies WHERE title LIKE '%' || ? || '%'";
    private static final String CREATE_MOVIE = "INSERT INTO movies (title, director, release_year) VALUES (?, ?, ?)";
    private static final String UPDATE_MOVIE = "UPDATE movies SET title = ?, director = ?, release_year = ? WHERE id = ?";
    private static final String DELETE_MOVIE = "DELETE FROM movies WHERE id = ?";

    public JdbcMovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> findAll() {
        return jdbcTemplate.query(GET_ALL_MOVIES, new BeanPropertyRowMapper<>(Movie.class));
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return jdbcTemplate.query(GET_MOVIE_BY_ID, new BeanPropertyRowMapper<>(Movie.class), id)
            .stream()
            .findFirst();
    }

    @Override
    public Optional<Movie> findByTitle(String title) {
        return jdbcTemplate.query(GET_MOVIE_BY_TITLE, new BeanPropertyRowMapper<>(Movie.class), title)
            .stream()
            .findFirst();
    }

    @Override
    public int save(Movie movie) {
        return jdbcTemplate.update(CREATE_MOVIE, movie.getTitle(), movie.getDirector(), movie.getReleaseYear());
    }

    @Override
    public int update(Long id, Movie movie) {
        return jdbcTemplate.update(UPDATE_MOVIE, movie.getTitle(), movie.getDirector(), movie.getReleaseYear(), id);
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_MOVIE, id);
    }

}
