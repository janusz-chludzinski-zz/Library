package com.ohgianni.tin.Service;

import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    private BookService bookService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BookService bookService) {
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
    }

    public List<Reservation> getAllReservationsForClient(Client client) {
        List<Reservation> reservations = reservationRepository.findAllByClientId(client.getId());
        setDaysLeft(reservations);
        encodeCovers(reservations);

        return reservations;
    }

    private void setDaysLeft(List<Reservation> reservations) {
        reservations.forEach(reservation -> {

            reservation.setDaysLeft(ChronoUnit.DAYS.between(LocalDateTime.now(), reservation.getReturnTime()));

        });
    }

    private void encodeCovers(List<Reservation> reservations) {
        reservations.forEach(reservation -> bookService.encodeBookCover(reservation.getBook()));
    }
}
