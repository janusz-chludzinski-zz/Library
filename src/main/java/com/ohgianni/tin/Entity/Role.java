package com.ohgianni.tin.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue
    private Long roleId;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<Client> clients;

    public Role() {};

    public Role(String name) {
        this.name = name;
    }
}
