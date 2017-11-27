package com.ohgianni.tin.Enum;

public enum Status {

    RESERVED("RESERVED"), RENTED("RENTED"), RETURNED("RETURNED"), DELAYED("DELAYED");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
