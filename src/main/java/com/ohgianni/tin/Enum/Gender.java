package com.ohgianni.tin.Enum;

import javax.persistence.Entity;

public enum Gender {
    MALE("MALE"), FEMALE("FEMALE");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }
}
