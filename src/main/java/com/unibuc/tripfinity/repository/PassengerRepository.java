package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
