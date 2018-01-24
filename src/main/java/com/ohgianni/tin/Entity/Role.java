package com.ohgianni.tin.Entity;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

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
