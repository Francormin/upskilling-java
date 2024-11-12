package com.example.homeworkjdbctemplate.repository;

import com.example.homeworkjdbctemplate.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {

    List<Movie> findAll();

    Optional<Movie> findById(Long id);

    Optional<Movie> findByTitle(String title);

    Movie save(Movie movie);

    int update(Long id, Movie movie);

    int deleteById(Long id);

}
