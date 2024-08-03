package com.brightskies.hotelsystem.Controller;

import com.brightskies.hotelsystem.DTO.ReservationDTO;
import com.brightskies.hotelsystem.Enum.ReservationStatus;
import com.brightskies.hotelsystem.Service.ReservationService;
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

    @GetMapping("/filter/date/{checkin}/{checkout}")
    public ResponseEntity<List<ReservationDTO>> filterReservationsByDate(@PathVariable("checkin") String checkin, @PathVariable("checkout") String chechkout) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkinDate = LocalDate.parse(checkin, formatter);
        LocalDate checkoutDate = LocalDate.parse(chechkout, formatter);
        return ResponseEntity.ok(reservationService.filterByDate(Date.valueOf(checkinDate), Date.valueOf(checkoutDate)));
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
    public ResponseEntity<ReservationDTO> updateReservationDates(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        if (reservationService.updateReservationDates(id, reservationDTO.checkin(), reservationDTO.checkout()) == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
        } else if (reservationService.updateReservationDates(id, reservationDTO.checkin(), reservationDTO.checkout()) == 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/update/room/{id}")
    public ResponseEntity<ReservationDTO> updateReservationRoom(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        if (reservationService.updateReservationRoom(id, reservationDTO.room()) == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
        } else if (reservationService.updateReservationDates(id, reservationDTO.checkin(), reservationDTO.checkout()) == 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
