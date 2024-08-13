package com.brightskies.hotelsystem.controller;

import com.brightskies.hotelsystem.dto.ReservationDTO;
import com.brightskies.hotelsystem.enums.ReservationStatus;
import com.brightskies.hotelsystem.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.rmi.NoSuchObjectException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.List;

@RequestMapping("/api/reservation")
@RestController
@Tag(name = "Reservation Controller", description = "API for managing reservations")
public class ReservationController {
    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Adds a new reservation", description = "Reservation room must be available and reservation dates must not overlap with another reservation of the same user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Room is not available"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json") })
    })
    @PreAuthorize("hasAnyRole('admin', 'customer')")
    @PostMapping("/add")
    public ResponseEntity<?> addReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            reservationService.addReservation(reservationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservationDTO);
        }
        catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @Operation(summary = "Displays all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displayed", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDTO.class, type = "array")) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json")})
    })
    @PreAuthorize("hasAnyRole('admin', 'staff')")
    @GetMapping("/display")
    public ResponseEntity<List<ReservationDTO>> displayReservations() {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.displayReservations());
    }

    @Operation(summary = "Filters reservations by dates", description = "Displays all reservations lying within the interval of the request parameters' dates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displayed", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDTO.class, type = "array")) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json")})
    })
    @PreAuthorize("hasAnyRole('admin', 'staff')")
    @GetMapping("/filter/date")
    public ResponseEntity<List<ReservationDTO>> filterReservationsByDate(@RequestParam String checkin, @RequestParam String checkout) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.filterByDate(checkin, checkout));
    }

    @Operation(summary = "Filters reservations by status", description = "Reservation status must be pending, completed or cancelled")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Displayed", content =
                    { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationDTO.class, type = "array")) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json")})
    })
    @PreAuthorize("hasAnyRole('admin', 'staff')")
    @GetMapping("/display/status/{status}")
    public ResponseEntity<List<ReservationDTO>> filterReservationsByStatus(@PathVariable String status) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.filterByStatus(status));
    }

    @Operation(summary = "Cancels a reservation", description = "Reservation must be pending in order to be cancelled, frees the associated room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cancelled"),
            @ApiResponse(responseCode = "400", description = "Reservation does not exist"),
            @ApiResponse(responseCode = "409", description = "Reservation is not pending"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json") })
    })
    @PreAuthorize("hasAnyRole('admin', 'customer')")
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

    @Operation(summary = "Completes a reservation", description = "Reservation must be pending in order to be completed, frees the associated room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Completed"),
            @ApiResponse(responseCode = "400", description = "Reservation does not exist"),
            @ApiResponse(responseCode = "409", description = "Reservation is not pending"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json") })
    })
    @PreAuthorize("hasAnyRole('admin', 'customer')")
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

    @Operation(summary = "Updates reservation dates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Updated"),
            @ApiResponse(responseCode = "400", description = "Reservation does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json") })
    })
    @PreAuthorize("hasAnyRole('admin', 'customer')")
    @PatchMapping("/update/dates/{id}")
    public ResponseEntity<?> updateReservationDates(@PathVariable Long id, @RequestParam String checkin, @RequestParam String checkout) {
        try {
            reservationService.updateReservationDates(id, checkin, checkout);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
    }

    @Operation(summary = "Updates reservation room", description = "New room must be available")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Updated"),
            @ApiResponse(responseCode = "400", description = "Reservation does not exist"),
            @ApiResponse(responseCode = "409", description = "Room is not available"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json") })
    })
    @PreAuthorize("hasAnyRole('admin', 'customer')")
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
