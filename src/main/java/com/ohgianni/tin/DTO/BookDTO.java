package com.ohgianni.tin.DTO;

import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Entity.Publisher;
import lombok.Data;

import java.util.List;

import static com.ohgianni.tin.Enum.CoverType.getAllAsString;

@Data
public class BookDTO {

    private Book book;

    private List<Book> books;

    private String coverType;

    private List<String> coverTypes;

    private String publisher;

    private String newPublisher;

    private List<Publisher> publishers;

    private String Marta;

    public BookDTO(Book book, List<Book> books, List<Publisher> publishers) {
        this.book = book;
        this.books = books;
        this.publishers = publishers;
        this.coverTypes = getAllAsString();
    }


}
