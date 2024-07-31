package com.brightskies.hotelsystem.DTO;

import com.brightskies.hotelsystem.Enum.RoomStatus;
import com.brightskies.hotelsystem.Enum.RoomType;

public record RoomDTO(RoomType type, RoomStatus status) {
}
