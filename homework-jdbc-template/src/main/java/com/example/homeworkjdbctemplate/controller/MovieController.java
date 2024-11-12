package com.example.homeworkjdbctemplate.controller;

import com.example.homeworkjdbctemplate.model.Movie;
import com.example.homeworkjdbctemplate.service.MovieService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAll() {
        List<Movie> movies = movieService.getAll();
        return ResponseEntity.ok().body(movies);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Movie> getById(@PathVariable Long id) {
        return movieService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get")
    public ResponseEntity<Movie> getByTitle(@RequestParam String title) {
        return movieService.getByTitle(title)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Movie movie) {
        int result = movieService.create(movie);
        return result > 0
            ? ResponseEntity.ok("Movie created successfully.")
            : ResponseEntity.status(500).body("Failed to create movie.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Movie movie) {
        movie.setId(id);
        int result = movieService.update(id, movie);
        return result > 0
            ? ResponseEntity.ok("Movie updated successfully.")
            : ResponseEntity.status(404).body("Movie with ID " + id + " not found.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        int result = movieService.delete(id);
        return result > 0
            ? ResponseEntity.ok("Movie deleted successfully.")
            : ResponseEntity.status(404).body("Movie with ID " + id + " not found.");
    }

}
