package com.brightskies.hotelsystem.dto;

import com.brightskies.hotelsystem.enums.RoomStatus;
import com.brightskies.hotelsystem.enums.RoomType;

public record RoomDTO(String section, RoomType type, RoomStatus status) {
}
