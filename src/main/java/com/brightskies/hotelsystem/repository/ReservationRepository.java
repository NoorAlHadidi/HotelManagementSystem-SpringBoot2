package com.brightskies.hotelsystem.repository;

import com.brightskies.hotelsystem.enums.ReservationStatus;
import com.brightskies.hotelsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Reservation r SET r.status = 'cancelled' WHERE r.id = :id")
    void cancelReservation(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query("UPDATE Reservation r SET r.status = 'completed' WHERE r.id = :id")
    void completeReservation(@Param("id") Long id);
    @Query("SELECT r FROM Reservation r WHERE r.user = :user AND ((r.checkin BETWEEN :checkin AND :checkout) OR (r.checkin < :checkin AND r.checkout > :checkin))")
    List<Reservation> findOverlappingUserAndDates(@Param("user") Long user, @Param("checkin") Date checkin, @Param("checkout") Date checkout);
    @Query("SELECT r FROM Reservation r WHERE (r.checkin BETWEEN :checkin AND :checkout) AND (r.checkout BETWEEN :checkin AND :checkout)")
    List<Reservation> findByDate(@Param("checkin") Date checkin, @Param("checkout") Date checkout);
    List<Reservation> findByStatus(ReservationStatus status);
}
