package com.unibuc.tripfinity.service;
import com.amadeus.exceptions.ResponseException;
import com.unibuc.tripfinity.model.*;
import com.unibuc.tripfinity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HotelBookingService {

    @Autowired
    private HotelBookingRepository hotelBookingRepository;
    @Autowired
    private HotelGuestRepository hotelGuestRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelOfferRepository hotelOfferRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private UserInfoRepository userInfoRepository;


    HotelDTO savedHotel;
    HotelOfferDTO hotelOffer;

    @Transactional
    public HotelBooking createBooking(String username, HotelBookingRequest bookingRequest) {

        System.out.println("HotelId: "+ bookingRequest.getHotelOffer().getHotelId());
        Optional<UserInfo> user = userInfoRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        try{
            hotelOffer = hotelOfferRepository.save(bookingRequest.getHotelOffer());
        }catch (Exception e){
            throw new RuntimeException("Invalid offer");
        }

        try {
            HotelDTO hotel = hotelService.searchHotelById(bookingRequest.getHotelOffer().getHotelId(), bookingRequest.getCity());
            savedHotel = hotelRepository.save(hotel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HotelGuest hotelGuest = new HotelGuest();
        hotelGuest.setFirstName(bookingRequest.getHotelGuest().getFirstName());
        hotelGuest.setLastName(bookingRequest.getHotelGuest().getLastName());
        hotelGuest.setPhoneNumber(bookingRequest.getHotelGuest().getPhoneNumber());
        hotelGuest.setEmail(bookingRequest.getHotelGuest().getEmail());
        hotelGuest.setNoOfAdditionalPeople(bookingRequest.getHotelGuest().getNoOfAdditionalPeople());
        hotelGuest = hotelGuestRepository.save(hotelGuest);



//        HotelOfferDTO hotelOffer = hotelOfferRepository.findById(bookingRequest.getHotelOffer().getOfferId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel offer ID"));


        HotelBooking hotelBooking = new HotelBooking();
        hotelBooking.setHotelOfferDTO(hotelOffer);
        hotelBooking.setHotelGuest(hotelGuest);
//        hotelBooking.setPayment(payment);
        hotelBooking.setUser(user.get());

        hotelBooking = hotelBookingRepository.save(hotelBooking);

        Payment payment = new Payment();
        payment.setPaymentType(bookingRequest.getPayment().getPaymentType());
        payment.setCardNumber(bookingRequest.getPayment().getCardNumber());
        payment.setExpiryDate(bookingRequest.getPayment().getExpiryDate());


        payment.setHotelBooking(hotelBooking);
        paymentRepository.save(payment);

        return hotelBooking;
    }

    public List<HotelBooking> getHotelBookingsByUser(String email){
        return hotelBookingRepository.findAllByUser_Email(email);
    }

    public List<HotelBooking> getHotelBookingsByUserAndBookingId(String email, Long id){
        return hotelBookingRepository.findAllByUser_EmailAndBookingId(email, id);
    }
}
