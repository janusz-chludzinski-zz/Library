package com.ohgianni.tin.Entity;

import com.ohgianni.tin.Enum.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.ohgianni.tin.Enum.Status.*;

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
