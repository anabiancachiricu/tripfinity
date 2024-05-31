package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Entity(name = "flight")
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    @Id
    @Column(name = "flight_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightId;

    private String duration;
    private String departIataCode;
    private String departTerminal;
    private String departTime;
    private String arrilvalIataCode; // Note: Should be "arrivalIataCode"
    private String arrivalTime;
    private String carrierCode;
    private String flightNumber;
    private int departStops;
    private String totalPrice;
    private int baggageQuantity;
    private int baggageWeight;
    private String baggageWeightUnit;

    @OneToMany(mappedBy = "departureFlight")
    @JsonManagedReference (value = "departure_flight")
    private List<FlightBooking> departureFlightBookings;

    @OneToMany(mappedBy = "returnFlight")
    @JsonManagedReference(value = "return_flight")
    private List<FlightBooking> returnFlightBookings;

}