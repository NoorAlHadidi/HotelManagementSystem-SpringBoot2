package com.brightskies.hotelsystem.DTO;

import com.brightskies.hotelsystem.Enum.RoomStatus;
import com.brightskies.hotelsystem.Enum.RoomType;

public record RoomDTO(String section, RoomType type, RoomStatus status) {
}
