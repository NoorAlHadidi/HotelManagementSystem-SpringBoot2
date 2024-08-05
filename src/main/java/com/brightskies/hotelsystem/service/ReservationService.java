package com.brightskies.hotelsystem.service;

import com.brightskies.hotelsystem.dto.ReservationDTO;
import com.brightskies.hotelsystem.enums.ReservationStatus;
import com.brightskies.hotelsystem.model.Reservation;
import com.brightskies.hotelsystem.repository.ReservationRepo;
import com.brightskies.hotelsystem.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private ReservationRepo reservationRepository;
    private RoomRepo roomRepository;

    @Autowired
    public ReservationService(ReservationRepo reservationRepository, RoomRepo roomRepository) {
        this.reservationRepository =  reservationRepository;
        this.roomRepository = roomRepository;
    }

    public List<ReservationDTO> displayReservations() {
        return (reservationRepository.findAll())
                .stream().
                map(reservation -> new ReservationDTO(reservation.getUser(), reservation.getRoom(), reservation.getCheckin(), reservation.getCheckout(), reservation.getStatus())).
                collect(Collectors.toList());
    }

    public List<ReservationDTO> filterByDate(String checkin, String checkout) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkinDate = LocalDate.parse(checkin, formatter);
        LocalDate checkoutDate = LocalDate.parse(checkout, formatter);
        return (reservationRepository.findByDate(Date.valueOf(checkinDate), Date.valueOf(checkoutDate)))
                .stream().
                map(reservation -> new ReservationDTO(reservation.getUser(), reservation.getRoom(), reservation.getCheckin(), reservation.getCheckout(), reservation.getStatus()))
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> filterByStatus(ReservationStatus status) {
        return (reservationRepository.findByStatus(status))
                .stream()
                .map(reservation -> new ReservationDTO(reservation.getUser(), reservation.getRoom(), reservation.getCheckin(), reservation.getCheckout(), reservation.getStatus()))
                .collect(Collectors.toList());
    }

    public boolean addReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation(reservationDTO.user(), reservationDTO.room(), reservationDTO.checkin(), reservationDTO.checkout(), reservationDTO.status());
        if(reservationRepository.findOverlappingUserAndDates(reservation.getUser(), reservation.getCheckin(), reservation.getCheckout()).isEmpty() && roomRepository.checkAvailability(reservation.getRoom()).isPresent()) {
            reservationRepository.save(reservation);
            roomRepository.bookRoom(reservation.getRoom());
            return true;
        }
        return false;
    }

    public int cancelReservation(Long id) {
        if(reservationRepository.findById(id).isPresent()) {
            if(reservationRepository.findById(id).get().getStatus().equals(ReservationStatus.pending)) {
                reservationRepository.cancelReservation(id);
                roomRepository.freeRoom(reservationRepository.findById(id).get().getRoom());
                return 1;
            }
            return 0;
        }
        return -1;
    }

    public int completeReservation(Long id) {
        if(reservationRepository.findById(id).isPresent()) {
            if(reservationRepository.findById(id).get().getStatus().equals(ReservationStatus.pending)) {
                reservationRepository.completeReservation(id);
                roomRepository.freeRoom(reservationRepository.findById(id).get().getRoom());
                return 1;
            }
            return 0;
        }
        return -1;
    }

    public int updateReservationDates(Long id, String checkin, String checkout) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkinDate = LocalDate.parse(checkin, formatter);
        LocalDate checkoutDate = LocalDate.parse(checkout, formatter);
        if(reservationRepository.findById(id).isPresent()) {
            Reservation reservation = reservationRepository.findById(id).get();
            reservation.setCheckin(Date.valueOf(checkinDate));
            reservation.setCheckout(Date.valueOf(checkoutDate));
            if(reservationRepository.findOverlappingUserAndDates(reservation.getUser(), reservation.getCheckin(), reservation.getCheckout()).isEmpty()) {
                reservationRepository.save(reservation);
                return 1;
            }
            return 0;
        }
        return -1;
    }

    public int updateReservationRoom(Long id, Long room) {
        if(reservationRepository.findById(id).isPresent()) {
            Reservation reservation = reservationRepository.findById(id).get();
            if(roomRepository.checkAvailability(room).isPresent()) {
                reservation.setRoom(room);
                reservationRepository.save(reservation);
                roomRepository.bookRoom(room);
                return 1;
            }
            return 0;
        }
        return -1;
    }
}
