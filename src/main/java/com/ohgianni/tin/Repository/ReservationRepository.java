package com.ohgianni.tin.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Enum.Status;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findAllByClientId(Long id);

    List<Reservation> findReservationsByStatus(Status status);
}
