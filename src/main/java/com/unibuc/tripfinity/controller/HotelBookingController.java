package com.unibuc.tripfinity.controller;

import com.unibuc.tripfinity.model.HotelBooking;
import com.unibuc.tripfinity.model.HotelBookingRequest;
import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.repository.UserInfoRepository;
import com.unibuc.tripfinity.service.EmailService;
import com.unibuc.tripfinity.service.HotelBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotel")
public class HotelBookingController {
    @Autowired
    private HotelBookingService bookingService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/book")
    public ResponseEntity<HotelBooking> createBooking(Authentication authentication, @RequestBody HotelBookingRequest bookingRequest) {
        String username = authentication.getName();
        HotelBooking hotelBooking = bookingService.createBooking(username, bookingRequest);
        emailService.sendMail(hotelBooking.getHotelGuest().getEmail());
        return ResponseEntity.ok(hotelBooking);
    }

    @GetMapping("/getBookingByEmail")
    public ResponseEntity<List<HotelBooking>> getBookingByEmail(Authentication authentication){
        String username = authentication.getName();
        List<HotelBooking> bookings = bookingService.getHotelBookingsByUser(username);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/getBookingByEmailAndId")
    public ResponseEntity<List<HotelBooking>> getBookingByEmailAndId(Authentication authentication,@RequestParam Long bookingId){
        String username = authentication.getName();
        List<HotelBooking> bookings = bookingService.getHotelBookingsByUserAndBookingId(username, bookingId);
        return ResponseEntity.ok(bookings);
    }


}
