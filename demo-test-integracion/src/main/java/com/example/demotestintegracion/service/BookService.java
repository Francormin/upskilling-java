package com.example.demotestintegracion.service;

import com.example.demotestintegracion.entity.Book;

public interface BookService {

    Book saveBook(Book book);

    Book getBookById(Long id);

}
