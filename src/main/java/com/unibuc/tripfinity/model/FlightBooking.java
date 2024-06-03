package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Entity(name = "flight_booking")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "flightBookingId")
public class FlightBooking {

    @Id
    @Column(name = "flight_booking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flightBookingId;

    @ManyToOne
//    @JsonBackReference(value = "departure_flight")
    @JoinColumn(name = "departure_flight_id")
    private Flight departureFlight;

    @ManyToOne
//    @JsonBackReference(value = "return_flight")
    @JoinColumn(name = "return_flight_id")
    private Flight returnFlight;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "flight_booking_passenger",
            joinColumns = @JoinColumn(name = "flight_booking_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id")
    )
//    @JsonBackReference(value = "passenger-booking")
//    @JsonIgnore
    private List<Passenger> passengerList;

    @OneToOne(mappedBy = "flightBooking", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @JsonManagedReference(value = "flight_payment")
    private Payment payment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email")
    @JsonIgnore
//    @JsonBackReference(value = "email_flight")
    private UserInfo user;
}
