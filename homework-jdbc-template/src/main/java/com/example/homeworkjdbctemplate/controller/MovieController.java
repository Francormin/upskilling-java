package com.example.homeworkjdbctemplate.controller;

import com.example.homeworkjdbctemplate.model.Movie;
import com.example.homeworkjdbctemplate.service.MovieService;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    /**
     * Build URI with ServletUriComponentsBuilder:
     * <p><ul>
     * <li>fromCurrentRequest() gets the base URI of the current request (e.g., /movies).
     * <li>path("/{id}") appends the /{id} path to the URI.
     * <li>buildAndExpand(createdMovie.getId()) replaces {id} with the actual ID of the new movie.
     * <li>toUri() converts it to a URI object.
     * </ul><p>
     * <p>
     * Return ResponseEntity.created(location).body(createdMovie):
     * <p><ul>
     * <li>created(location) sets the 201 Created status and adds the Location header.
     * <li>.body(createdMovie) includes the new Movie object in the response body.
     * </ul><p>
     */
    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        Movie createdMovie = movieService.create(movie);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/get/{id}")
            .buildAndExpand(createdMovie.getId())
            .toUri();

        return ResponseEntity.created(location).body(createdMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Movie movie) {
        movie.setId(id);
        int result = movieService.update(id, movie);
        return result > 0
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        int result = movieService.delete(id);
        return result > 0
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

}
