package com.ohgianni.tin.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter @Setter
public class Role {

    @Id
    @GeneratedValue
    private Long roleId;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<Client> clients;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }
}
