package com.example.demoorm.service;

import com.example.demoorm.entity.Author;

import java.util.List;

public interface AuthorService {

    Author createAuthor(Author author);

    List<Author> getAllAuthors();

}
