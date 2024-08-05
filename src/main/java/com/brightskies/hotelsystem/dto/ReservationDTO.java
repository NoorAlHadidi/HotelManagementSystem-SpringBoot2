package com.brightskies.hotelsystem.dto;

import com.brightskies.hotelsystem.enums.ReservationStatus;

import java.sql.Date;

public record ReservationDTO(Long user, Long room, Date checkin, Date checkout, ReservationStatus status) {

}
