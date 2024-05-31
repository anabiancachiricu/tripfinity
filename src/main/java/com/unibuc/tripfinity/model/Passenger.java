package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Entity(name = "passenger")
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {

    @Id
    @Column(name = "passenger_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int passengerId;

    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phoneNumber;

    @ManyToMany(mappedBy = "passengerList")
    @JsonBackReference(value = "passenger-booking")
    private List<FlightBooking> flightBookings;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "documents")
    private List<Document> documents;

}
