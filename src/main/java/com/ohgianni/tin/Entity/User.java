package com.ohgianni.tin.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Data
public abstract class User extends Person {

    @Column
    protected String email;

    @Column
    protected String password;

//    private Collection<Role> roles;


}
