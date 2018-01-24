package com.ohgianni.tin.Entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.ohgianni.tin.Enum.Gender;
import lombok.Data;

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

