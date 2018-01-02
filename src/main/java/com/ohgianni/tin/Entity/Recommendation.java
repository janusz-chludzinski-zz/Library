package com.ohgianni.tin.Entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
public class Recommendation {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long recommendationId;

    @Column
    @NotEmpty
    @NotNull
    private String authorFirstName;

    @Column
    @NotEmpty
    @NotNull
    private String authorLastName;

    @Column
    @NotNull
    private Long isbn;

    @Column
    @NotEmpty
    @NotNull
    private String publisher;

    @Column
    @NotEmpty
    @NotNull
    private String comment;

    @ManyToOne
    @JoinColumn(name = "id")
    private Client client;

    @Column
    private LocalDateTime recommendationTime;

    @Column
    private Long votes;

}
