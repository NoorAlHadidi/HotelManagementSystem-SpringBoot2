package com.brightskies.hotelsystem.Repository;

import com.brightskies.hotelsystem.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    /*@Modifying
    @Transactional
    @Query("UPDATE reservation AS r SET r.status = 'cancelled' WHERE r.id = :id")
    void cancelReservation(@Param("id") Long id);
    @Query("SELECT * FROM reservation AS r WHERE r.user = :user AND (r.checkin BETWEEN :checkin AND :checkout) OR (r.checkin < :checkin AND NOT (r.checkout < :checkin))")
    Optional<Reservation> findOverlappingUserAndDates(@Param("user") Long user, @Param("checkin") Date checkin, @Param("checkout") Date checkout);*/
}
