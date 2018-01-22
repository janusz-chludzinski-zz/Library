package com.ohgianni.tin.DTO;

import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Entity.Publisher;
import com.ohgianni.tin.Service.PublisherService;
import lombok.Data;

import java.util.List;

import static com.ohgianni.tin.Enum.CoverType.getAllAsString;
import static com.ohgianni.tin.Service.PublisherService.*;

@Data
public class BookDTO {

    private Book book;

    private List<Book> books;

    private String coverType;

    private List<String> coverTypes;

    private String publisher;

    private List<Publisher> publishers;

    public BookDTO() {
        this.publishers = findAllPublishers();
        this.coverTypes = getAllAsString();
    }

    public BookDTO(Book book, List<Book> books, List<Publisher> publishers) {
        this.book = book;
        this.books = books;
        this.publishers = findAllPublishers();
        this.coverTypes = getAllAsString();
    }


}
