package repositories;

import entities.Book;

public interface BookRepository {

    Book findBookByIsbn(String isbn);

    void saveBook(Book book);

}
