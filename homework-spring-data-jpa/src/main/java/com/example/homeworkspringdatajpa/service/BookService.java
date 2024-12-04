package com.example.homeworkspringdatajpa.service;

import com.example.homeworkspringdatajpa.dto.BookDTO;
import com.example.homeworkspringdatajpa.entity.Book;
import com.example.homeworkspringdatajpa.exception.BookNotFoundException;
import com.example.homeworkspringdatajpa.repository.BookRepository;
import com.example.homeworkspringdatajpa.util.ConversionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.homeworkspringdatajpa.util.ConversionUtil.fromEntityToDTO;
import static com.example.homeworkspringdatajpa.util.ConversionUtil.fromDTOToEntity;
import static com.example.homeworkspringdatajpa.util.ValidationUtil.validateNotEmpty;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
            .stream()
            .map(ConversionUtil::fromEntityToDTO)
            .toList();
    }

    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
            .map(ConversionUtil::fromEntityToDTO)
            .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found."));
    }

    public List<BookDTO> getBooksByTitle(String title) {
        validateNotEmpty(title, "Title");

        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with title containing: " + title);
        }

        return books.stream()
            .map(ConversionUtil::fromEntityToDTO)
            .toList();
    }

    public List<BookDTO> getBooksByAuthor(String author) {
        validateNotEmpty(author, "Author");

        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found for author: " + author);
        }

        return books.stream()
            .map(ConversionUtil::fromEntityToDTO)
            .toList();
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = fromDTOToEntity(bookDTO);
        book = bookRepository.save(book);
        return fromEntityToDTO(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found."));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setPublishYear(bookDTO.getPublishYear());
        existingBook.setPageQuantity(bookDTO.getPageQuantity());

        existingBook = bookRepository.save(existingBook);
        return fromEntityToDTO(existingBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        bookRepository.deleteById(id);
    }

}
