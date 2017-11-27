package com.ohgianni.tin.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
@Getter @Setter
public class Author extends Person {

    @ManyToMany(mappedBy = "authors")
    private Collection<Book> books;


}
