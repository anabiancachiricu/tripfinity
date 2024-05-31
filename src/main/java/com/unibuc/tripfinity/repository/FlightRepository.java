package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByCarrierCodeAndAndFlightNumber(String carrierCode, String flightNumber);
}


