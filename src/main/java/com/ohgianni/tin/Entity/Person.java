package com.ohgianni.tin.Entity;

import com.ohgianni.tin.Enum.Gender;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Person {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "surname")
    protected String surname;

    @Column(name = "dateOfBirth")
    protected LocalDate dateOfBirth;

    @Column
    protected Gender gender;

    public Person() {}

}

