package com.example.homeworkspringdatajpa.controller;

import com.example.homeworkspringdatajpa.dto.BookDTO;
import com.example.homeworkspringdatajpa.service.BookService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    private BookDTO createBookDTO(
        String title,
        String author,
        Integer publishYear, Integer pageQuantity) {

        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(title);
        bookDTO.setAuthor(author);
        bookDTO.setPublishYear(publishYear);
        bookDTO.setPageQuantity(pageQuantity);
        return bookDTO;

    }

    @Test
    void shouldReturnListOfBooks() throws Exception {
        // Given
        BookDTO bookDTO1 = createBookDTO(
            "Book One",
            "Author One",
            2020,
            200
        );

        BookDTO bookDTO2 = createBookDTO(
            "Book Two",
            "Author Two",
            2021,
            300
        );

        List<BookDTO> books = List.of(bookDTO1, bookDTO2);

        when(bookService.getAllBooks()).thenReturn(books);

        // When & Then
        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("[0].title").value("Book One"))
            .andExpect(jsonPath("[1].title").value("Book Two"));
    }

    @Test
    void shouldReturnBookById() throws Exception {
        // Given
        Long bookId = 1L;
        BookDTO bookDTO = createBookDTO(
            "Title1",
            "Author1",
            2020,
            300
        );

        when(bookService.getBookById(bookId)).thenReturn(bookDTO);

        // When & Then
        mockMvc.perform(get("/api/books/{id}", bookId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Title1"))
            .andExpect(jsonPath("$.author").value("Author1"));
    }

    @Test
    void shouldReturnBooksByAuthor() throws Exception {
        // Given
        BookDTO bookDTO1 = createBookDTO(
            "Book One",
            "Author One",
            2020,
            200
        );

        BookDTO bookDTO2 = createBookDTO(
            "Book Two",
            "Author One",
            2021,
            300
        );

        List<BookDTO> books = List.of(bookDTO1, bookDTO2);

        when(bookService.getBooksByAuthor("Author One")).thenReturn(books);

        // When & Then
        mockMvc.perform(get("/api/books/by-author")
                .param("author", "Author One"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("[0].author").value("Author One"))
            .andExpect(jsonPath("[1].author").value("Author One"));
    }

    @Test
    void shouldReturnBooksByTitle() throws Exception {
        // Given
        BookDTO bookDTO1 = createBookDTO(
            "Valid Title",
            "Author One",
            2020,
            200
        );

        BookDTO bookDTO2 = createBookDTO(
            "Valid Title",
            "Author Two",
            2021,
            300
        );

        List<BookDTO> books = List.of(bookDTO1, bookDTO2);

        when(bookService.getBooksByTitle("Valid Title")).thenReturn(books);

        // When & Then
        mockMvc.perform(get("/api/books/by-title")
                .param("title", "Valid Title"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("[0].title").value("Valid Title"))
            .andExpect(jsonPath("[1].title").value("Valid Title"));
    }

    @Test
    void shouldCreateBook() throws Exception {
        // Given
        BookDTO bookDTO = createBookDTO(
            "New Book",
            "New Author",
            2023,
            250
        );

        String bookJson = """
                {
                    "title": "New Book",
                    "author": "New Author",
                    "publishYear": 2023,
                    "pageQuantity": 250
                }
            """;

        when(bookService.createBook(bookDTO)).thenReturn(bookDTO);

        // When & Then
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("New Book"))
            .andExpect(jsonPath("$.author").value("New Author"))
            .andExpect(jsonPath("$.publishYear").value(2023))
            .andExpect(jsonPath("$.pageQuantity").value(250));
    }

    @Test
    void shouldReturnBadRequestWhenTitleIsBlank() throws Exception {
        // Given
        String bookJson = """
                {
                    "title": "",
                    "author": "Valid Author",
                    "publishYear": 2020,
                    "pageQuantity": 200
                }
            """;

        // When & Then
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$", containsInAnyOrder(
                containsString("title: Title cannot be null nor blank"),
                containsString("title: Title must have between 2 and 60 characters")
            )));
    }

    @Test
    void shouldReturnBadRequestWhenAuthorIsBlank() throws Exception {
        // Given
        String bookJson = """
                {
                    "title": "Valid Title",
                    "author": "",
                    "publishYear": 2020,
                    "pageQuantity": 200
                }
            """;

        // When & Then
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$", containsInAnyOrder(
                containsString("author: Author cannot be null nor blank"),
                containsString("author: Author must have between 2 and 30 characters")
            )));
    }

    @Test
    void shouldReturnBadRequestWhenPublishYearIsInvalid() throws Exception {
        // Given
        String bookJson = """
                {
                    "title": "Valid Title",
                    "author": "Valid Author",
                    "publishYear": 1500,
                    "pageQuantity": 200
                }
            """;

        // When & Then
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0]")
                .value("publishYear: Publish year must be equal or greater than 1600"));
    }

    @Test
    void shouldReturnBadRequestWhenPageQuantityIsInvalid() throws Exception {
        // Given
        String bookJson = """
                {
                    "title": "Valid Title",
                    "author": "Valid Author",
                    "publishYear": 2020,
                    "pageQuantity": 10
                }
            """;

        // When & Then
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[0]")
                .value("pageQuantity: Page quantity must be equal or greater than 15"));
    }

    @Test
    void shouldUpdateBook() throws Exception {
        // Given
        Long bookId = 1L;
        BookDTO bookDTO = createBookDTO(
            "Updated Title",
            "Updated Author",
            2022,
            350
        );

        String updatedBookJson = """
                {
                    "title": "Updated Title",
                    "author": "Updated Author",
                    "publishYear": 2022,
                    "pageQuantity": 350
                }
            """;

        when(bookService.updateBook(bookId, bookDTO)).thenReturn(bookDTO);

        // When & Then
        mockMvc.perform(put("/api/books/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedBookJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Title"))
            .andExpect(jsonPath("$.author").value("Updated Author"))
            .andExpect(jsonPath("$.publishYear").value(2022));
    }

    @Test
    void shouldDeleteBook() throws Exception {
        // Given
        Long bookId = 1L;

        // When & Then
        mockMvc.perform(delete("/api/books/{id}", bookId))
            .andExpect(status().isNoContent());
    }

}
