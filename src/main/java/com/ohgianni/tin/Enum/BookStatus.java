package com.ohgianni.tin.Enum;

public enum BookStatus {

    AVAILABLE("RESERVED"), RESERVED("RESERVED"), RENTED("RENTED");

    private final String status;

    BookStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
