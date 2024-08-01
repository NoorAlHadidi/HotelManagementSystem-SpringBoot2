package com.brightskies.hotelsystem.Controller;

import com.brightskies.hotelsystem.DTO.ReservationDTO;
import com.brightskies.hotelsystem.Model.Reservation;
import com.brightskies.hotelsystem.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/reservation")
@RestController
public class ReservationController {
    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/display")
    public ResponseEntity<List<ReservationDTO>> displayReservations() {
        return ResponseEntity.ok(reservationService.displayReservations());
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addReservation(@RequestBody ReservationDTO reservation) {
        if(reservationService.addReservation(new Reservation(reservation.user(), reservation.room(), reservation.checkin(), reservation.checkout(), reservation.status()))) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        if(reservationService.cancelReservation(id)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
