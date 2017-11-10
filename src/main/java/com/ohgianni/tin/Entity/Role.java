package com.ohgianni.tin.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
