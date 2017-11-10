package com.ohgianni.tin.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class Author extends Person {

//    @Id
//    @GeneratedValue
//    private Long authorId;


    @OneToMany(mappedBy = "author")
    private Collection<Book> books;

}
