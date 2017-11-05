package com.ohgianni.tin.Entity;

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
    @JoinColumn(name = "personId")
    Person person;

    public Book(String title, Person person) {
        this.title = title;
        this.person = person;
    }

}

