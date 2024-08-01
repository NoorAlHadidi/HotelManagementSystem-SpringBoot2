package com.brightskies.hotelsystem.DTO;

import com.brightskies.hotelsystem.Enum.RoomSection;
import com.brightskies.hotelsystem.Enum.RoomStatus;
import com.brightskies.hotelsystem.Enum.RoomType;

public record RoomDTO(RoomSection section, RoomType type, RoomStatus status) {
}
