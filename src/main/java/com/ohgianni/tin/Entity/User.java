package com.ohgianni.tin.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public abstract class User extends Person {

    @Column(unique = true)
    protected String email;

    @Column
    protected String password;

}
