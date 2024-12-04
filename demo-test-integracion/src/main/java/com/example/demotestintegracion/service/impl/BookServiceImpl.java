package com.example.demotestintegracion.service.impl;

import com.example.demotestintegracion.entity.Book;
import com.example.demotestintegracion.repository.BookRepository;
import com.example.demotestintegracion.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

}
