package com.ohgianni.tin.Exception;

public class BookNotFoundException extends Exception {

    public BookNotFoundException() {
        super("Książka o podany ISBN nie została znaleziona");
    }
}
