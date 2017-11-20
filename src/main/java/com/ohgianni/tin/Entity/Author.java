package com.ohgianni.tin.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class Author extends Person {

    @ManyToMany(mappedBy = "authors")
    private Collection<Book> books;

}
