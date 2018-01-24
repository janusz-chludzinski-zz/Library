package com.ohgianni.tin.Entity;

import com.ohgianni.tin.Enum.BookStatus;
import com.ohgianni.tin.Enum.CoverType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Book {

    @Column
    private CoverType coverType;

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
    private String coverImage;

    @Column
    private Long isbn;

    @Column
    @Enumerated(STRING)
    private BookStatus status;

    @OneToMany(mappedBy = "book")
    private List<Reservation> reservations;

    @Transient
    private int availableSpecimen;

    @Transient
    private int totalSpecimen;

    @Transient
    private MultipartFile multipartImage;

    public Book() {

    }
}

