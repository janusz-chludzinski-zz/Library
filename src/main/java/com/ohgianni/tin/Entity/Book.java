package com.ohgianni.tin.Entity;

import com.ohgianni.tin.Enum.CoverType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Book {

    @GeneratedValue
    @Id
    private Long bookId;

    @Column(name = "Title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private Author author;

    @Column(name = "pages")
    private int pages;

    @Column(name = "coverType")
    CoverType coverType;

    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
    }

}

