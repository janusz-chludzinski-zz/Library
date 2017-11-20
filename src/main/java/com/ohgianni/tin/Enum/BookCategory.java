package com.ohgianni.tin.Enum;

public enum BookCategory {

    JAVA("JAVA"), PYTHON("PYTHON"), CPP("C++");

    private final String category;

    BookCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
