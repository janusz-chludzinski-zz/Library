package com.ohgianni.tin.Service;

import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ohgianni.tin.Enum.Status.RESERVED;

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
}
