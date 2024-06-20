package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.unibuc.tripfinity.validator.OnlyNumbers;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@Entity(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "paymentId")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @NotNull
    @OnlyNumbers
    private String cardNumber;

    @NotNull
    private String expiryDate;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = true)
//    @JsonBackReference(value = "payment")
    private HotelBooking hotelBooking;

    @OneToOne
    @JoinColumn(name = "flight_booking_id", nullable = true)
//    @JsonBackReference(value = "flight_payment")
    private FlightBooking flightBooking;
}
