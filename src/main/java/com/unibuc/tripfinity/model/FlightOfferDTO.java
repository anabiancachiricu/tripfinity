package com.unibuc.tripfinity.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FlightOfferDTO {

    String id;
    String duration;

    String departIataCode;
    String departTerminal;
    String departTime;
    String departArrilvalIataCode;
    String departArrivalTime;
    String departCarrierCode;
    String departFlightNumber;
    int departStops;

    String returnIataCode;
    String returnTerminal;
    String returnTime;
    String returnArrilvalIataCode;
    String returnArrivalTime;
    int returnStops;
    String returnCarrierCode;
    String returnFlightNumber;

    String totalPrice;

    int baggageQuantity;
    int baggageWeight;
    String baggageWeightUnit;



}
