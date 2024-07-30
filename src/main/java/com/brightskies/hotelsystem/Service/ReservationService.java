package com.brightskies.hotelsystem.Service;

import com.brightskies.hotelsystem.Model.Reservation;
import com.brightskies.hotelsystem.Repository.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private ReservationRepo reservationRepository;

    @Autowired
    public ReservationService(ReservationRepo reservationRepository) {
        this.reservationRepository =  reservationRepository;
    }

}
