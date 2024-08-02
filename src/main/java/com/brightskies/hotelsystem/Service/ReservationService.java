package com.brightskies.hotelsystem.Service;

import com.brightskies.hotelsystem.DTO.ReservationDTO;
import com.brightskies.hotelsystem.Model.Reservation;
import com.brightskies.hotelsystem.Repository.ReservationRepo;
import com.brightskies.hotelsystem.Repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
        return (reservationRepository.findAll()).stream().map(reservation -> new ReservationDTO(reservation.getUser(), reservation.getRoom(), reservation.getCheckin(), reservation.getCheckout(), reservation.getStatus())).collect(Collectors.toList());
    }

    public boolean addReservation(Reservation reservation) {
        if(reservationRepository.findOverlappingUserAndDates(reservation.getUser(), reservation.getCheckin(), reservation.getCheckout()).isEmpty() && roomRepository.checkAvailability(reservation.getRoom()).isPresent()) {
            reservationRepository.save(reservation);
            roomRepository.bookRoom(reservation.getRoom());
            return true;
        }
        return false;
    }

    public boolean cancelReservation(Long id) {
        if(reservationRepository.findById(id).isPresent()) {
            reservationRepository.cancelReservation(id);
            roomRepository.freeRoom(reservationRepository.findById(id).get().getRoom());
            return true;
        }
        return false;
    }

    public int updateReservationDates(Long id, Date checkin, Date checkout) {
        if(reservationRepository.findById(id).isPresent()) {
            Reservation reservation = reservationRepository.findById(id).get();
            reservation.setCheckin(checkin);
            reservation.setCheckout(checkout);
            if(reservationRepository.findOverlappingUserAndDates(reservation.getUser(), reservation.getCheckin(), reservation.getCheckout()).isEmpty()) {
                reservationRepository.save(reservation);
                return 1;
            }
            return 0;
        }
        return -1;
    }
}
