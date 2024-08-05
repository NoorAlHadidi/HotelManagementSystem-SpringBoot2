package com.brightskies.hotelsystem.model;

import com.brightskies.hotelsystem.enums.ReservationStatus;

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

    public Reservation() {

    }

    public Reservation(Long user, Long room, Date checkin, Date checkout, ReservationStatus status) {
        this.user = user;
        this.room = room;
        this.checkin = checkin;
        this.checkout = checkout;
        this.status = status;
    }

    public void setRoom(Long room) {
        this.room = room;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public Long getId() {
        return id;
    }

    public Long getUser() {
        return user;
    }

    public Long getRoom() {
        return room;
    }

    public Date getCheckin() {
        return checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public ReservationStatus getStatus() {
        return status;
    }
}
