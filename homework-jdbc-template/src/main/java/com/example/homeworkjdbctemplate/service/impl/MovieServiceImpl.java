package com.example.homeworkjdbctemplate.service.impl;

import com.example.homeworkjdbctemplate.model.Movie;
import com.example.homeworkjdbctemplate.repository.MovieRepository;
import com.example.homeworkjdbctemplate.service.MovieService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> getById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public Optional<Movie> getByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public int update(Long id, Movie movie) {
        return movieRepository.update(id, movie);
    }

    @Override
    public int delete(Long id) {
        return movieRepository.deleteById(id);
    }

}
