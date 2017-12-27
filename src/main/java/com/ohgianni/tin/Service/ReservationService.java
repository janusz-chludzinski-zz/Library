package com.ohgianni.tin.Service;

import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Enum.BookStatus;
import com.ohgianni.tin.Exception.ReservationNotFoundException;
import com.ohgianni.tin.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.ohgianni.tin.Enum.Status.RENTED;
import static com.ohgianni.tin.Enum.Status.RESERVED;
import static com.ohgianni.tin.Enum.Status.RETURNED;

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
        encodeCovers(reservations);

        return reservations;
    }

    private void setDaysLeft(List<Reservation> reservations) {
        reservations.forEach(reservation ->  reservation.setDaysLeft(ChronoUnit.DAYS.between(LocalDateTime.now(), reservation.getReturnTime())));
    }

    private void encodeCovers(List<Reservation> reservations) {
        reservations.forEach(reservation ->  {
            Book book = reservation.getBook();
            book.setImageUrl(imageService.getBookCoverUrl(book));
        });
    }

    @Transactional
    public List<Reservation> findAllReservations() {
        List<Reservation> reservations = reservationRepository.findReservationsByStatus(RESERVED);
        encodeCovers(reservations);

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
        return reservationRepository.findReservationsByStatus(RENTED);
    }

    @Transactional
    public Reservation returnBook(Long rentId) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(rentId).orElseThrow(ReservationNotFoundException::new);

        reservation.setStatus(RETURNED);
        bookService.setStatusAndSave(reservation.getBook(), BookStatus.AVAILABLE);

        return reservationRepository.save(reservation);
    }
}

