package services;

import entities.Book;
import repositories.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios para evaluar los métodos de la clase BookService")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Test para validar el ingreso de un nuevo libro")
    public void testAddBook() {
        // GIVEN
        Book book = new Book("Mockito for dummies", "John Doe", "1-56619-909-3");

        // No need to stub saveBook since it's void, and we can directly verify the method call.
        // doNothing().when(bookRepository).saveBook(any(Book.class));

        // WHEN
        bookService.addBook(book);

        // THEN
        verify(bookRepository, times(1)).saveBook(book);
    }

    @Test
    @DisplayName("Test para validar la obtención de un libro a través de su código ISBN")
    public void testGetBookDetails() {
        // GIVEN
        String isbn = "1-56619-909-3";
        Book expectedBook = new Book("Mockito for dummies", "John Doe", isbn);

        // Stubbing before invoking the method
        when(bookRepository.findBookByIsbn(isbn)).thenReturn(expectedBook);

        // WHEN
        Book actualBook = bookService.getBookDetails(isbn);

        // THEN
        verify(bookRepository, times(1)).findBookByIsbn(isbn);
        Assertions.assertEquals(expectedBook, actualBook);
    }

}
