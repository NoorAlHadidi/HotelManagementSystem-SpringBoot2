package com.brightskies.hotelsystem.service;

import com.brightskies.hotelsystem.dto.ReservationDTO;
import com.brightskies.hotelsystem.enums.ReservationStatus;
import com.brightskies.hotelsystem.model.Reservation;
import com.brightskies.hotelsystem.repository.ReservationRepo;
import com.brightskies.hotelsystem.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
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

    public void addReservation(ReservationDTO reservationDTO) throws Exception {
        Reservation reservation = new Reservation(reservationDTO.user(), reservationDTO.room(), reservationDTO.checkin(), reservationDTO.checkout(), reservationDTO.status());
        if(reservationRepository.findOverlappingUserAndDates(reservation.getUser(), reservation.getCheckin(), reservation.getCheckout()).isEmpty() && roomRepository.checkAvailability(reservation.getRoom()).isPresent()) {
            reservationRepository.save(reservation);
            roomRepository.bookRoom(reservation.getRoom());
            return;
        }
        else if(roomRepository.checkAvailability(reservation.getRoom()).isEmpty()) {
            throw new Exception("Selected room is already booked.");
        }
        else {
            throw new Exception("User already has a reservation within these dates.");
        }
    }

    public void cancelReservation(Long id) throws Exception {
        if(reservationRepository.findById(id).isPresent()) {
            if(reservationRepository.findById(id).get().getStatus().equals(ReservationStatus.pending)) {
                reservationRepository.cancelReservation(id);
                roomRepository.freeRoom(reservationRepository.findById(id).get().getRoom());
                return;
            }
            else {
                if(reservationRepository.findById(id).get().getStatus().equals(ReservationStatus.completed)) {
                    throw new Exception("Reservation was completed.");
                }
                else {
                    throw new Exception("Reservation has already been cancelled.");
                }
            }
        }
        throw new NoSuchObjectException("Reservation does not exist");
    }

    public void completeReservation(Long id) throws Exception {
        if(reservationRepository.findById(id).isPresent()) {
            if(reservationRepository.findById(id).get().getStatus().equals(ReservationStatus.pending)) {
                reservationRepository.completeReservation(id);
                roomRepository.freeRoom(reservationRepository.findById(id).get().getRoom());
                return;
            }
            else {
                if(reservationRepository.findById(id).get().getStatus().equals(ReservationStatus.completed)) {
                    throw new Exception("Reservation has already been completed.");
                }
                else {
                    throw new Exception("Reservation was cancelled.");
                }
            }
        }
        throw new NoSuchObjectException("Reservation does not exist");
    }

    public void updateReservationDates(Long id, String checkin, String checkout) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkinDate = LocalDate.parse(checkin, formatter);
        LocalDate checkoutDate = LocalDate.parse(checkout, formatter);
        if(reservationRepository.findById(id).isPresent()) {
            Reservation reservation = reservationRepository.findById(id).get();
            reservation.setCheckin(Date.valueOf(checkinDate));
            reservation.setCheckout(Date.valueOf(checkoutDate));
            if(reservationRepository.findOverlappingUserAndDates(reservation.getUser(), reservation.getCheckin(), reservation.getCheckout()).isEmpty()) {
                reservationRepository.save(reservation);
                return;
            }
            throw new Exception("User already has a reservation within these dates.");
        }
        throw new NoSuchObjectException("Reservation does not exist");
    }

    public void updateReservationRoom(Long id, Long room) throws Exception {
        if(reservationRepository.findById(id).isPresent()) {
            Reservation reservation = reservationRepository.findById(id).get();
            if(roomRepository.checkAvailability(room).isPresent()) {
                reservation.setRoom(room);
                reservationRepository.save(reservation);
                roomRepository.bookRoom(room);
                return;
            }
            throw new Exception("Selected room is already booked.");
        }
        throw new NoSuchObjectException("Reservation does not exist");
    }
}
