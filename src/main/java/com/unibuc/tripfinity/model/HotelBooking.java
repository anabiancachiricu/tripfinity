package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity(name = "hotel_booking")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookingId")
public class HotelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "hotel_offer_id", nullable = false)
//    @JsonBackReference(value = "hoteloffer")
    private HotelOfferDTO hotelOfferDTO;

    @ManyToOne
    @JoinColumn(name = "hotel_guest_id", nullable = false)
//    @JsonBackReference (value = "guest")
    private HotelGuest hotelGuest;

    @OneToOne(mappedBy = "hotelBooking", cascade = CascadeType.ALL)
//    @JsonManagedReference(value = "payment")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "email", nullable = false)
//    @JsonBackReference(value = "email")
    @JsonIgnore
    private UserInfo user;

}
