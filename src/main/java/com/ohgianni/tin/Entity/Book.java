package com.ohgianni.tin.Entity;

import com.ohgianni.tin.Enum.BookStatus;
import com.ohgianni.tin.Enum.CoverType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Book {

    @Column
    CoverType coverType;

    @ManyToOne
    @JoinColumn(name = "publisherId")
    private Publisher publisher;

    @GeneratedValue
    @Id
    private Long bookId;

    @Column
    private String title;

    @ManyToMany
    @JoinTable(
            name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "BOOK_ID", referencedColumnName = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "id")
    )
    private List<Author> authors;

    @Column
    private int pages;

    @Column
    private int edition;

    @Column
    private byte[] coverImage;

    @Column
    private Long isbn;

    @Column
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @OneToMany(mappedBy = "book")
    private List<Reservation> reservations;

    @Transient
    private String imageUrl;

    @Transient
    private int availableSpecimen;

    @Transient
    private int totalSpecimen;

    public Book() {

    }

    public CoverType getCoverType() {
        return coverType;
    }

}

