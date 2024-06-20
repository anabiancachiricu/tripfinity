package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.*;
import com.unibuc.tripfinity.validator.Email;
import com.unibuc.tripfinity.validator.OnlyLetters;
import com.unibuc.tripfinity.validator.PhoneNumber;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Entity(name = "hotel_guest")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "hotelGuestId")
public class HotelGuest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_guest_id")
    private Long hotelGuestId;

    @NonNull
    @OnlyLetters
    private String firstName;

    @NonNull
    @OnlyLetters
    private String lastName;

    @NonNull
//    @PhoneNumber
    private String phoneNumber;

    @NonNull
    @Email
    private String email;

    private int noOfAdditionalPeople;

    @OneToMany(mappedBy = "hotelGuest", cascade = CascadeType.ALL)
//    @JsonManagedReference(value = "guest")
//    @JsonIgnore
    private List<HotelBooking> hotelBookingList;

}
