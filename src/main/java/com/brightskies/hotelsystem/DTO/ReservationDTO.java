package com.brightskies.hotelsystem.DTO;

import java.sql.Date;

public record ReservationDTO(Integer userID, Integer roomID, Date checkin, Date checkout, ReservationStatus status) {
    public enum ReservationStatus {
        pending,
        booked,
        cancelled
    }
}
