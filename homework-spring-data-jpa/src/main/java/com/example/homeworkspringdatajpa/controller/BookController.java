package com.example.homeworkspringdatajpa.controller;

import com.example.homeworkspringdatajpa.dto.BookDTO;
import com.example.homeworkspringdatajpa.service.BookService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    /*
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String author) {

        if (title != null) {
            return ResponseEntity.ok(bookService.getBooksByTitle(title));
        } else if (author != null) {
            return ResponseEntity.ok(bookService.getBooksByAuthor(author));
        } else {
            return ResponseEntity.badRequest().build();
        }

    }
    */

    @GetMapping("/by-author")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@RequestParam String author) {
        List<BookDTO> filteredBooks = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(filteredBooks);
    }

    @GetMapping("/by-title")
    public ResponseEntity<List<BookDTO>> getBooksByTitle(@RequestParam String title) {
        List<BookDTO> filteredBooks = bookService.getBooksByTitle(title);
        return ResponseEntity.ok(filteredBooks);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
