package com.ohgianni.tin.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long publisherId;

    @Column
    private String name;

    @Column
    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

}
