package com.example.homeworkjdbctemplate.repository.impl;

import com.example.homeworkjdbctemplate.constants.MovieSqlQueries;
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

    public JdbcMovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> findAll() {
        return jdbcTemplate.query(MovieSqlQueries.GET_ALL, new BeanPropertyRowMapper<>(Movie.class));
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return jdbcTemplate.query(
                MovieSqlQueries.GET_BY_ID,
                new BeanPropertyRowMapper<>(Movie.class),
                id)
            .stream()
            .findFirst();
    }

    @Override
    public Optional<Movie> findByTitle(String title) {
        return jdbcTemplate.query(
                MovieSqlQueries.GET_BY_TITLE,
                new BeanPropertyRowMapper<>(Movie.class),
                title)
            .stream()
            .findFirst();
    }

    @Override
    public int save(Movie movie) {
        return jdbcTemplate.update(
            MovieSqlQueries.CREATE,
            movie.getTitle(),
            movie.getDirector(),
            movie.getReleaseYear()
        );
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
