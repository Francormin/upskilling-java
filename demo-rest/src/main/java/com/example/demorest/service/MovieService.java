package com.example.demorest.service;

import com.example.demorest.model.Movie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private List<Movie> movies = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private File moviesFile;

    @PostConstruct
    public void init() {
        try {
            moviesFile = new ClassPathResource("movies.json").getFile();
            movies = objectMapper.readValue(moviesFile, new TypeReference<List<Movie>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> findAll() {
        return movies;
    }

    public Optional<Movie> findById(int id) {
        return movies.stream()
            .filter(movie -> movie.getId() == id)
            .findFirst();
    }

    public void save(Movie movie) {
        movies.add(movie);
        saveToFile();
    }

    public Optional<Movie> updateById(int id, Movie movieDetails) {
        Optional<Movie> existingMovie = findById(id);
        if (existingMovie.isPresent()) {
            Movie movie = existingMovie.get();
            movie.setTitle(movieDetails.getTitle());
            movie.setDirector(movieDetails.getDirector());
            movie.setReleaseYear(movieDetails.getReleaseYear());
            saveToFile();
            return Optional.of(movie);
        }
        return Optional.empty();
    }

    public void deleteById(int id) {
        Optional<Movie> movie = findById(id);
        if (movie.isPresent()) {
            movies.remove(movie.get());
            saveToFile();
        }
    }

    private void saveToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(moviesFile, movies);
            System.out.println("Movies saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
