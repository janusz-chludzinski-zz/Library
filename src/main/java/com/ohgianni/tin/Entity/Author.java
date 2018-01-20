package com.ohgianni.tin.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Collection;

import static java.time.LocalDate.*;

@Entity
@Getter @Setter
public class Author extends Person {

    @ManyToMany(mappedBy = "authors")
    private Collection<Book> books;

    @Transient
    private String dateOfBirthString;

    public void resolveDateOfBirth() {
        this.dateOfBirth = parse(dateOfBirthString);
    }

}
