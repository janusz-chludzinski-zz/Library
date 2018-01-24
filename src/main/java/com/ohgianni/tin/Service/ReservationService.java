package com.ohgianni.tin.Service;

import static com.ohgianni.tin.Enum.Status.RENTED;
import static com.ohgianni.tin.Enum.Status.RESERVED;
import static com.ohgianni.tin.Enum.Status.RETURNED;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Enum.BookStatus;
import com.ohgianni.tin.Enum.Status;
import com.ohgianni.tin.Exception.ReservationNotFoundException;
import com.ohgianni.tin.Repository.ReservationRepository;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    private ImageService imageService;

    private BookService bookService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, BookService bookService, ImageService imageService) {
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
        this.imageService = imageService;
    }

    public List<Reservation> getAllReservationsForClient(Client client) {
        List<Reservation> reservations = reservationRepository.findAllByClientId(client.getId());
        setDaysLeft(reservations);

        return reservations;
    }

    @Transactional
    public List<Reservation> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findReservationsByStatus(RESERVED);
        setDaysLeft(reservations);

        return reservations;
    }

    @Transactional
    public Reservation rentBook(Long id) throws ReservationNotFoundException{
        Reservation reservation = reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);

        reservation.setStatus(RENTED);
        bookService.setStatusAndSave(reservation.getBook(), BookStatus.RENTED);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAllRentals() {
        List<Reservation> rentals = reservationRepository.findReservationsByStatus(RENTED);
        setDaysLeft(rentals);

        return rentals;
    }

    @Transactional
    public Reservation returnBook(Long rentId) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(rentId).orElseThrow(ReservationNotFoundException::new);

        reservation.setStatus(RETURNED);
        bookService.setStatusAndSave(reservation.getBook(), BookStatus.AVAILABLE);

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(Long id, RedirectAttributes redirectAttributes) {
        try {
            Reservation reservation = reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
            reservation.setStatus(Status.CANCELED);
            bookService.setStatusAndSave(reservation.getBook(), BookStatus.AVAILABLE);
            redirectAttributes.addFlashAttribute("message", "Rezerwacja została pomyślnie odwołana");

        } catch (ReservationNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
    }

    private void setDaysLeft(List<Reservation> reservations) {
        reservations.forEach(reservation ->  reservation.setDaysLeft(ChronoUnit.DAYS.between(LocalDateTime.now(), reservation.getReturnTime())));
    }
}

