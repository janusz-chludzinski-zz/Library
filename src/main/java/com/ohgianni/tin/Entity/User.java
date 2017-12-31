package com.ohgianni.tin.Entity;

import lombok.Data;
import org.thymeleaf.expression.Sets;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Data
public abstract class User extends Person {

    @Column(unique = true)
    protected String email;

    @Column
    protected String password;

}
