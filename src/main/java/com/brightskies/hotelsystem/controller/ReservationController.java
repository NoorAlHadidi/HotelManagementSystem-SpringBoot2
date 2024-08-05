package com.brightskies.hotelsystem.controller;

import com.brightskies.hotelsystem.dto.ReservationDTO;
import com.brightskies.hotelsystem.enums.ReservationStatus;
import com.brightskies.hotelsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ReservationDTO> addReservation(@RequestBody ReservationDTO reservationDTO) {
        if(reservationDTO.checkin().after(reservationDTO.checkout())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(reservationService.addReservation(reservationDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        if(reservationService.cancelReservation(id) == 1) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else if(reservationService.cancelReservation(id) == 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/complete/{id}")
    public ResponseEntity<Void> completeReservation(@PathVariable Long id) {
        if(reservationService.completeReservation(id) == 1) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else if(reservationService.completeReservation(id) == 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/update/dates/{id}")
    public ResponseEntity<Void> updateReservationDates(@PathVariable Long id, @RequestParam String checkin, @RequestParam String checkout) {
        if (reservationService.updateReservationDates(id, checkin, checkout) == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else if (reservationService.updateReservationDates(id, checkin, checkout) == 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/update/room/{id}")
    public ResponseEntity<Void> updateReservationRoom(@PathVariable Long id, @RequestParam Long room) {
        if (reservationService.updateReservationRoom(id, room) == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else if (reservationService.updateReservationRoom(id, room) == 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
