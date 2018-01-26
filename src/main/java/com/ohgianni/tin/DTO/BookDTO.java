package com.ohgianni.tin.DTO;

import static com.ohgianni.tin.Enum.CoverType.getAllAsString;
import static com.ohgianni.tin.Service.PublisherService.findAllPublishers;

import java.util.List;

import com.ohgianni.tin.Entity.Author;
import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Entity.Publisher;
import lombok.Data;

@Data
public class BookDTO {

    private List<Author> authors;

    private Book book;

    private List<Book> books;

    private String coverType;

    private List<String> coverTypes;

    private String publisher;

    private List<Publisher> publishers;

    private Long currentIsbn;

    public BookDTO() {

    }

    public BookDTO(List<Author> authors) {
        this.authors = authors;
        this.publishers = findAllPublishers();
        this.coverTypes = getAllAsString();
    }

    public BookDTO(Book book, List<Book> books) {
        this.book = book;
        this.books = books;
        this.currentIsbn = book.getIsbn();
        this.publishers = findAllPublishers();
        this.coverTypes = getAllAsString();
    }


}
