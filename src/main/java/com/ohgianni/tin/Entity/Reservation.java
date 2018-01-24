package com.ohgianni.tin.Entity;

import static com.ohgianni.tin.Enum.Status.RESERVED;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.ohgianni.tin.Enum.Status;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Reservation {

    @Id
    @GeneratedValue
    private long reservationId;

    @Column
    private LocalDateTime reservationTime;

    @Column
    private LocalDateTime collectionTime;

    @Column
    private LocalDateTime returnTime;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "id")
    private Client client;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Transient
    private Long daysLeft;

    public Reservation() {

    }

    public Reservation(Book book, Client client) {
        this.book = book;
        this.client = client;
        reservationTime = LocalDateTime.now();
        returnTime = reservationTime.plusDays(14l);
        status = RESERVED;
    }

}
