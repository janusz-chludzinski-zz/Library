package com.ohgianni.tin.Enum;

public enum BookStatus {

    AVAILABLE("AVAILABLE"), RESERVED("RESERVED"), RENTED("RENTED");

    private final String status;

    BookStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
