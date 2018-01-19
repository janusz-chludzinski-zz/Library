package com.ohgianni.tin.DTO;

import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Entity.Publisher;
import lombok.Data;

import java.util.List;

@Data
public class BookDTO {

    private Book book;

    private List<Book> books;

    private Publisher publisher;

    private List<Publisher> publishers;

    public BookDTO(Book book, List<Book> books, Publisher publisher, List<Publisher> publishers) {
        this.book = book;
        this.books = books;
        this.publisher = publisher;
        this.publishers = publishers;
    }
}
