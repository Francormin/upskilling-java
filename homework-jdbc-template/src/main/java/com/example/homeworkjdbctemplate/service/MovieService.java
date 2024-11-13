package com.example.homeworkjdbctemplate.service;

import com.example.homeworkjdbctemplate.dto.MovieDto;
import com.example.homeworkjdbctemplate.model.Movie;

import java.util.List;

public interface MovieService {

    List<MovieDto> getAll();

    MovieDto getById(Long id);

    MovieDto getByTitle(String title);

    Object[] create(MovieDto movieDto);

    int update(Long id, MovieDto movieDto);

    int delete(Long id);

}
