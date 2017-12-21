package com.ohgianni.tin.Repository;

import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Enum.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findAllByClientId(Long id);

    List<Reservation> findReservationsByStatus(Status status);
}
