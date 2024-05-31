package com.unibuc.tripfinity.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Entity(name = "flight_booking")
@AllArgsConstructor
@NoArgsConstructor
public class FlightBooking {

    @Id
    @Column(name = "flight_booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightBookingId; // Changed from flightId to flightBookingId for clarity

    @ManyToOne
    @JsonBackReference(value = "departure_flight")
    @JoinColumn(name = "departure_flight_id") // Specify the join column name
    private Flight departureFlight;

    @ManyToOne
    @JsonBackReference(value = "return_flight")
    @JoinColumn(name = "return_flight_id") // Specify the join column name
    private Flight returnFlight;

    @ManyToMany
    @JoinTable(
            name = "flight_booking_passenger", // Specify the join table name
            joinColumns = @JoinColumn(name = "flight_booking_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
    @JsonManagedReference(value = "passenger-booking")
    private List<Passenger> passengerList;

    @ManyToOne
    @JoinColumn(name = "email")
    @JsonBackReference(value = "email_flight")
    private UserInfo user;

}