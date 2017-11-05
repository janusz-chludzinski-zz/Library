package com.ohgianni.tin.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long personId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "person")
    private Collection<Book> books;

    public Person(String name, String surname, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    public void addBook(Book book) {
        if(books == null) books = new ArrayList<>();
        books.add(book);
    }
}

