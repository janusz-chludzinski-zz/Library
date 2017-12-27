package com.ohgianni.tin.Exception;

public class ReservationNotFoundException extends Exception {

    public ReservationNotFoundException() {
        super("Nie znaleziono wskazanej rezerwacji");
    }

}
