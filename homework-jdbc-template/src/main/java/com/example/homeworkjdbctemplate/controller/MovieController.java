package com.example.homeworkjdbctemplate.controller;

import com.example.homeworkjdbctemplate.dto.MovieDto;
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
    public ResponseEntity<List<MovieDto>> getAll() {
        List<MovieDto> moviesDto = movieService.getAll();
        return ResponseEntity.ok().body(moviesDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MovieDto> getById(@PathVariable Long id) {
        MovieDto movieDto = movieService.getById(id);
        return movieDto != null
            ? ResponseEntity.ok().body(movieDto)
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/get")
    public ResponseEntity<MovieDto> getByTitle(@RequestParam String title) {
        MovieDto movieDto = movieService.getByTitle(title);
        return movieDto != null
            ? ResponseEntity.ok().body(movieDto)
            : ResponseEntity.notFound().build();
    }

    /**
     * Build URI with ServletUriComponentsBuilder:
     * <p><ul>
     * <li>fromCurrentRequest() gets the base URI of the current request (/api/v1/movies).
     * <li>path("/get/{id}") appends the /get/{id} path to the URI.
     * <li>buildAndExpand(result[1]) replaces {id} with the actual ID of the new movie.
     * <li>toUri() converts it to a URI object.
     * </ul><p>
     * <p>
     * Return ResponseEntity.created(location).body((MovieDto) result[0]):
     * <p><ul>
     * <li>created(location) sets the 201 Created status and adds the Location header.
     * <li>body((MovieDto) result[0]) includes the new Movie object in the response body.
     * </ul><p>
     */
    @PostMapping
    public ResponseEntity<MovieDto> create(@RequestBody MovieDto movieDto) {
        Object[] result = movieService.create(movieDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/get/{id}")
            .buildAndExpand(result[1])
            .toUri();

        return ResponseEntity.created(location).body((MovieDto) result[0]);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody MovieDto movieDto) {
        int result = movieService.update(id, movieDto);
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
