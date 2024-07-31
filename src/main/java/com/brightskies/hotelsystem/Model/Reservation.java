package com.brightskies.hotelsystem.Model;

import com.brightskies.hotelsystem.Enum.ReservationStatus;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user;
    private Long room;
    private Date checkin;
    private Date checkout;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
