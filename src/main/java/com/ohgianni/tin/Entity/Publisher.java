package com.ohgianni.tin.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
//@Data
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long publisherId;

    @Column
    private String name;

    @Column
    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
