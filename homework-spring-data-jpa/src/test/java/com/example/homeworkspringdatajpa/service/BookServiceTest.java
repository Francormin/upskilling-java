package com.example.homeworkspringdatajpa.service;

import com.example.homeworkspringdatajpa.dto.BookDTO;
import com.example.homeworkspringdatajpa.entity.Book;
import com.example.homeworkspringdatajpa.exception.BookNotFoundException;
import com.example.homeworkspringdatajpa.repository.BookRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book createBook(
        Long id,
        String title,
        String author,
        Integer publishYear,
        Integer pageQuantity) {

        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishYear(publishYear);
        book.setPageQuantity(pageQuantity);
        return book;

    }

    private BookDTO createBookDTO(
        String title,
        String author,
        Integer publishYear,
        Integer pageQuantity) {

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(title);
        bookDTO.setAuthor(author);
        bookDTO.setPublishYear(publishYear);
        bookDTO.setPageQuantity(pageQuantity);
        return bookDTO;

    }

    @Test
    void shouldGetAllBooks() {
        // Given
        Book book1 = createBook(
            1L,
            "Spring in Action",
            "Craig Walls",
            2018,
            450
        );

        Book book2 = createBook(
            2L,
            "Spring Boot Guide",
            "Mark Heckler",
            2020,
            320
        );

        List<Book> books = List.of(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        // When
        List<BookDTO> result = bookService.getAllBooks();

        // Then
        assertEquals(2, result.size());
        assertEquals("Spring in Action", result.get(0).getTitle());
        assertEquals("Spring Boot Guide", result.get(1).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void shouldGetBookById() {
        // Given
        Long bookId = 1L;
        Book book = createBook(
            bookId,
            "Spring in Action",
            "Craig Walls",
            2018,
            450
        );

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // When
        BookDTO result = bookService.getBookById(bookId);

        // Then
        assertEquals("Spring in Action", result.getTitle());
        assertEquals("Craig Walls", result.getAuthor());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void shouldThrowExceptionIfBookNotFoundById() {
        // Given
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // When & Then
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookById(bookId);
        });
        assertEquals("Book with ID " + bookId + " not found.", exception.getMessage());
    }

    @Test
    void shouldCreateBook() {
        // Given
        Book book = createBook(
            1L,
            "Spring Data JPA",
            "John Doe",
            2020,
            300
        );

        BookDTO dto = createBookDTO(
            "Spring Data JPA",
            "John Doe",
            2020,
            300
        );

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // When
        BookDTO createdBook = bookService.createBook(dto);

        // Then
        assertEquals(dto.getTitle(), createdBook.getTitle());
        assertEquals(dto.getAuthor(), createdBook.getAuthor());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldUpdateBook() {
        // Given
        Long bookId = 1L;
        Book existingBook = createBook(
            bookId,
            "Old Title",
            "Old Author",
            1990,
            200
        );

        BookDTO dto = createBookDTO(
            "Updated Title",
            "Updated Author",
            2020,
            400
        );

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        BookDTO updatedBook = bookService.updateBook(bookId, dto);

        // Then
        assertEquals(dto.getTitle(), updatedBook.getTitle());
        assertEquals(dto.getAuthor(), updatedBook.getAuthor());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void shouldDeleteBook() {
        // Given
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(true);

        // When
        bookService.deleteBook(bookId);

        // Then
        verify(bookRepository, times(1)).existsById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void shouldGetBooksByAuthor() {
        // Given
        String author = "John Doe";

        Book book1 = createBook(
            1L,
            "Book 1",
            author,
            2020,
            300
        );
        Book book2 = createBook(
            2L,
            "Book 2",
            author,
            2021,
            250
        );

        List<Book> books = List.of(book1, book2);

        when(bookRepository.findByAuthorContainingIgnoreCase(author)).thenReturn(books);

        // When
        List<BookDTO> result = bookService.getBooksByAuthor(author);

        // Then
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Book 2", result.get(1).getTitle());
        verify(bookRepository, times(1)).findByAuthorContainingIgnoreCase(author);
    }

    @Test
    void shouldThrowExceptionIfAuthorIsEmpty() {
        // Given
        String author = "";

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.getBooksByAuthor(author);
        });
        assertEquals("Author must not be empty.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfNoBooksFoundByAuthor() {
        // Given
        String author = "Nonexistent Author";
        when(bookRepository.findByAuthorContainingIgnoreCase(author)).thenReturn(List.of());

        // When & Then
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.getBooksByAuthor(author);
        });
        assertEquals("No books found for author: " + author, exception.getMessage());
    }

    @Test
    void shouldGetBooksByTitle() {
        // Given
        String title = "Spring";

        Book book1 = createBook(
            1L,
            "Spring in Action",
            "Craig Walls",
            2018,
            450
        );
        Book book2 = createBook(
            2L,
            "Spring Boot Guide",
            "Mark Heckler",
            2020,
            320
        );

        List<Book> books = List.of(book1, book2);

        when(bookRepository.findByTitleContainingIgnoreCase(title)).thenReturn(books);

        // When
        List<BookDTO> result = bookService.getBooksByTitle(title);

        // Then
        assertEquals(2, result.size());
        assertEquals("Spring in Action", result.get(0).getTitle());
        assertEquals("Spring Boot Guide", result.get(1).getTitle());
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase(title);
    }

    @Test
    void shouldThrowExceptionIfTitleIsEmpty() {
        // Given
        String title = "";

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.getBooksByTitle(title);
        });
        assertEquals("Title must not be empty.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfNoBooksFoundByTitle() {
        // Given
        String title = "Nonexistent Title";
        when(bookRepository.findByTitleContainingIgnoreCase(title)).thenReturn(List.of());

        // When & Then
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.getBooksByTitle(title);
        });
        assertEquals("No books found with title containing: " + title, exception.getMessage());
    }

}
