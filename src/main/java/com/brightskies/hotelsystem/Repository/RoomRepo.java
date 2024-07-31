package com.brightskies.hotelsystem.Repository;

import com.brightskies.hotelsystem.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Integer> {
    List<Room> findByStatus(Room.RoomStatus status);
    List<Room> findByType(Room.RoomType type);

}
