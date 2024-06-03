package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.FlightBooking;
import com.unibuc.tripfinity.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    List<FlightBooking> findFlightBookingByUserEmail (String email);

    Optional<FlightBooking> findFlightBookingByUserEmailAndFlightBookingId(String email, int flightBookingId);
}
