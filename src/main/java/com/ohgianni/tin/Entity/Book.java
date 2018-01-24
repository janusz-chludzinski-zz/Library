package com.ohgianni.tin.Entity;

import static javax.persistence.EnumType.STRING;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.ohgianni.tin.Enum.BookStatus;
import com.ohgianni.tin.Enum.CoverType;
import lombok.Getter;
import lombok.Setter;

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

