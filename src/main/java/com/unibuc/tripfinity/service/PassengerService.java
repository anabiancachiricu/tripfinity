package com.unibuc.tripfinity.service;

import com.unibuc.tripfinity.model.Passenger;
import com.unibuc.tripfinity.repository.PassengerRepository;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Passenger addPassanger(Passenger passenger){
        System.out.println("ADD PASSENGER ");
        return passengerRepository.save(passenger);
    }

}
