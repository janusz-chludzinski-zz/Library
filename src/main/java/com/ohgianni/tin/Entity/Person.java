package com.ohgianni.tin.Entity;

import com.ohgianni.tin.Enum.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Person {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(name = "name")
    @NotNull
    protected String name;

    @Column(name = "surname")
    @NotNull
    protected String surname;

    @Column(name = "dateOfBirth")
    @NotNull
    protected String dateOfBirth;

    @Column
    @NotNull
    protected Gender gender;

    public Person() {}

}

