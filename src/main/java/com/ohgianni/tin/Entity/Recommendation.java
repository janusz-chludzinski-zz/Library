package com.ohgianni.tin.Entity;

import static javax.persistence.GenerationType.AUTO;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

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

    @ManyToMany
    @JoinTable(
            name="recommendation_client",
            joinColumns = @JoinColumn(name = "recommendation_id", referencedColumnName = "recommendationId"),
            inverseJoinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id")
    )
    private Set<Client> voters;

    public void addVoter(Client client) {
        voters.add(client);
    }

}
