package com.brightskies.hotelsystem.Model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

@Component
@Entity
public class Reservation {
    @Id
    private int id;
    private int user;
    private int room;
    private Date checkin;
    private Date checkout;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    enum ReservationStatus {
        pending,
        booked,
        cancelled
    }
}
