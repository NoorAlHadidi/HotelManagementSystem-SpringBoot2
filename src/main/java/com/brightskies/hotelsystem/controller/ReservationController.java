package com.brightskies.hotelsystem.controller;

import com.brightskies.hotelsystem.dto.ReservationDTO;
import com.brightskies.hotelsystem.enums.ReservationStatus;
import com.brightskies.hotelsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.NoSuchObjectException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
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

    @GetMapping("/filter/date")
    public ResponseEntity<List<ReservationDTO>> filterReservationsByDate(@RequestParam String checkin, @RequestParam String checkout) {
        return ResponseEntity.ok(reservationService.filterByDate(checkin, checkout));
    }

    @GetMapping("/display/status/{status}")
    public ResponseEntity<List<ReservationDTO>> filterReservationsByStatus(@PathVariable String status) {
        ReservationStatus reservationStatus;
        try {
            reservationStatus = ReservationStatus.valueOf(status.toLowerCase());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reservationService.filterByStatus(reservationStatus));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            reservationService.addReservation(reservationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
        }
        catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long id) {
        try {
            reservationService.cancelReservation(id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (NoSuchObjectException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @PatchMapping("/complete/{id}")
    public ResponseEntity<?> completeReservation(@PathVariable Long id) {
        try {
            reservationService.completeReservation(id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (NoSuchObjectException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @PatchMapping("/update/dates/{id}")
    public ResponseEntity<?> updateReservationDates(@PathVariable Long id, @RequestParam String checkin, @RequestParam String checkout) {
        try {
            reservationService.updateReservationDates(id, checkin, checkout);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (NoSuchObjectException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

    @PatchMapping("/update/room/{id}")
    public ResponseEntity<?> updateReservationRoom(@PathVariable Long id, @RequestParam Long room) {
        try {
            reservationService.updateReservationRoom(id, room);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (NoSuchObjectException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }
}
