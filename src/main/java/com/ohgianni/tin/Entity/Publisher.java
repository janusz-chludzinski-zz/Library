package com.ohgianni.tin.Entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

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
