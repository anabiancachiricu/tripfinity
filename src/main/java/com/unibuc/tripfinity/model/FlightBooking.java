package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int flightBookingId;

    @ManyToOne
    @JsonBackReference(value = "departure_flight")
    @JoinColumn(name = "departure_flight_id")
    private Flight departureFlight;

    @ManyToOne
    @JsonBackReference(value = "return_flight")
    @JoinColumn(name = "return_flight_id")
    private Flight returnFlight;

    @ManyToMany
    @JoinTable(
            name = "flight_booking_passenger",
            joinColumns = @JoinColumn(name = "flight_booking_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
    @JsonBackReference(value = "passenger-booking")
//    @JsonIgnore
    private List<Passenger> passengerList;

    @OneToOne(mappedBy = "flightBooking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "flight_payment")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "email")
    @JsonBackReference(value = "email_flight")
    private UserInfo user;
}
