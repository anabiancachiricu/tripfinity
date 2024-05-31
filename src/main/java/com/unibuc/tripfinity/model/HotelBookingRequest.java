package com.unibuc.tripfinity.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HotelBookingRequest {

    private HotelOfferDTO hotelOffer;
    private HotelGuest hotelGuest;
    private Payment payment;
    private String city;

}
