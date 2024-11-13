package com.example.homeworkjdbctemplate.service.impl;

import com.example.homeworkjdbctemplate.dto.MovieDto;
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

    private MovieDto fromMovieToMovieDto(Movie movie) {
        return new MovieDto(
            movie.getTitle(),
            movie.getDirector(),
            movie.getReleaseYear()
        );
    }

    private Movie fromMovieDtoToMovie(MovieDto movieDto) {
        return new Movie(
            movieDto.getTitle(),
            movieDto.getDirector(),
            movieDto.getReleaseYear()
        );
    }

    @Override
    public List<MovieDto> getAll() {
        List<Movie> movies = movieRepository.findAll();
        return !movies.isEmpty()
            ? movies.stream()
                .map(this::fromMovieToMovieDto)
                .toList()
            : List.of();
    }

    @Override
    public MovieDto getById(Long id) {
        Optional<Movie> movieFound = movieRepository.findById(id);
        return movieFound.map(this::fromMovieToMovieDto).orElse(null);
    }

    @Override
    public MovieDto getByTitle(String title) {
        Optional<Movie> movieFound = movieRepository.findByTitle(title);
        return movieFound.map(this::fromMovieToMovieDto).orElse(null);
    }

    @Override
    public Object[] create(MovieDto movieDto) {
        Movie movieToSave = fromMovieDtoToMovie(movieDto);
        Movie movieCreated = movieRepository.save(movieToSave);
        return new Object[]{
            fromMovieToMovieDto(movieCreated),
            movieCreated.getId()
        };
    }

    @Override
    public int update(Long id, MovieDto movieDto) {
        Movie movieToUpdate = fromMovieDtoToMovie(movieDto);
        return movieRepository.update(id, movieToUpdate);
    }

    @Override
    public int delete(Long id) {
        return movieRepository.deleteById(id);
    }

}
