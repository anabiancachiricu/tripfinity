package com.unibuc.tripfinity.service;

import com.unibuc.tripfinity.model.Flight;
import com.unibuc.tripfinity.model.FlightBooking;
import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.repository.FlightBookingRepository;
import com.unibuc.tripfinity.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightBookingService {

    private final FlightBookingRepository flightBookingRepository;
    private final UserInfoRepository userInfoRepository;

    public FlightBookingService(FlightBookingRepository flightBookingRepository, UserInfoRepository userInfoRepository) {
        this.flightBookingRepository = flightBookingRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public FlightBooking addFlightBooking(String username, FlightBooking flightBooking){
        Optional<UserInfo> user = userInfoRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        else {
            System.out.println("USER : "+ user.get());
            flightBooking.setUser(user.get());
        }
        return flightBookingRepository.save(flightBooking);
    }

    public List<FlightBooking> getFlightsForUser(String email){
        return flightBookingRepository.findFlightBookingByUserEmail(email);
    }

    public Optional<FlightBooking> getFlightBookingById(String email, int flightBookingId){
        return flightBookingRepository.findFlightBookingByUserEmailAndFlightBookingId(email, flightBookingId);
    }

}
