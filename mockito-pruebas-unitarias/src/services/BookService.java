package services;

import entities.Book;
import repositories.BookRepository;

public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBookDetails(String isbn) {
        return bookRepository.findBookByIsbn(isbn);
    }

    public void addBook(Book book) {
        bookRepository.saveBook(book);
    }

}
