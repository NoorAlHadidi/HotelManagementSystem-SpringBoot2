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
    private Integer id;
    private Integer user;
    private Integer room;
    private Date checkin;
    private Date checkout;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public enum ReservationStatus {
        pending,
        booked,
        cancelled
    }
}
