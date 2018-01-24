package com.ohgianni.tin.Entity;

import static java.time.LocalDate.parse;

import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

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
