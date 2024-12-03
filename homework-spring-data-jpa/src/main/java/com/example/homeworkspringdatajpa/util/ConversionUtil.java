package com.example.homeworkspringdatajpa.util;

import com.example.homeworkspringdatajpa.dto.BookDTO;
import com.example.homeworkspringdatajpa.entity.Book;

public class ConversionUtil {

    public static BookDTO fromEntityToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublishYear(book.getPublishYear());
        dto.setPageQuantity(book.getPageQuantity());
        return dto;
    }

    public static Book fromDTOToEntity(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublishYear(dto.getPublishYear());
        book.setPageQuantity(dto.getPageQuantity());
        return book;
    }

}
