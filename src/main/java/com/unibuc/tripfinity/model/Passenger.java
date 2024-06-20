package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.*;
import com.unibuc.tripfinity.validator.Email;
import com.unibuc.tripfinity.validator.OnlyLetters;
import com.unibuc.tripfinity.validator.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Entity(name = "passenger")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "passengerId")
public class Passenger {

    @Id
    @Column(name = "passenger_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int passengerId;

    @NotNull
    @OnlyLetters
    private String firstName;

    @NotNull
    @OnlyLetters
    private String lastName;

    @NotNull
    private String gender;

    @NotNull
    @Email
    private String email;

    @NotNull
//    @PhoneNumber
    private String phoneNumber;

    @ManyToMany(mappedBy = "passengerList")
//    @JsonManagedReference(value = "passenger-booking")
    @JsonIgnore
    private List<FlightBooking> flightBookings;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    @JsonManagedReference(value = "documents")
    private List<Document> documents;

}
