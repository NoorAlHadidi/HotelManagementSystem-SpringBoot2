package com.brightskies.hotelsystem.DTO;

import com.brightskies.hotelsystem.Enum.ReservationStatus;

import java.sql.Date;

public record ReservationDTO(Long user, Long room, Date checkin, Date checkout, ReservationStatus status) {

}
