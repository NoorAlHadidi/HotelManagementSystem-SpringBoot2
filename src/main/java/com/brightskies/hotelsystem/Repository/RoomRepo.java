package com.brightskies.hotelsystem.Repository;

import com.brightskies.hotelsystem.Enum.RoomStatus;
import com.brightskies.hotelsystem.Enum.RoomType;
import com.brightskies.hotelsystem.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    List<Room> findByStatus(RoomStatus status);
    List<Room> findByType(RoomType type);
    List<Room> findBySection(String section);
    Optional<Room> findBySectionAndType(String section, RoomType type);
    @Query("SELECT DISTINCT r.section FROM Room r")
    List<String> findSections();
    @Query("SELECT r FROM Room r WHERE r.id = :id AND r.status = 'available'")
    Optional<Room> checkAvailability(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.status = 'booked' WHERE r.id = :id")
    void bookRoom(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.status = 'available' WHERE r.id = :id")
    void freeRoom(@Param("id") Long id);
}