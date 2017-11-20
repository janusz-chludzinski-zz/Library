package com.ohgianni.tin.Entity;

import com.ohgianni.tin.Enum.CoverType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Book {

    @Column
    CoverType coverType;

    @ManyToOne
    @JoinColumn(name = "publisherId")
    private Publisher publisher;

    @GeneratedValue
    @Id
    private Long bookId;

    @Column
    private String title;

    @ManyToMany
    @JoinTable(
            name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "BOOK_ID", referencedColumnName = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "id")
    )
    private List<Author> authors;

    @Column
    private int pages;

    @Column
    private int edition;

    @Column
    private byte[] coverImage;

    @Transient
    private String imageUrl;

    public Book() {

    }
}

