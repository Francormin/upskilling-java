package com.example.homeworkjdbctemplate.service;

import com.example.homeworkjdbctemplate.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<Movie> getAll();

    Optional<Movie> getById(Long id);

    Optional<Movie> getByTitle(String title);

    int create(Movie movie);

    int update(Long id, Movie movie);

    int delete(Long id);

}
