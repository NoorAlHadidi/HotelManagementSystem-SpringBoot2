package com.brightskies.hotelsystem.Repository;

import com.brightskies.hotelsystem.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Integer> {
}
