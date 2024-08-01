package com.brightskies.hotelsystem.Repository;

import com.brightskies.hotelsystem.Enum.RoomSection;
import com.brightskies.hotelsystem.Enum.RoomStatus;
import com.brightskies.hotelsystem.Enum.RoomType;
import com.brightskies.hotelsystem.Model.Reservation;
import com.brightskies.hotelsystem.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    List<Room> findByStatus(RoomStatus status);
    List<Room> findByType(RoomType type);
    Optional<Room> findBySectionAndType(RoomSection section, RoomType type);
    @Query("SELECT r FROM Room r WHERE r.id = :id AND r.status = 'available'")
    Optional<Room> checkAvailability(@Param("id") Long id);

}