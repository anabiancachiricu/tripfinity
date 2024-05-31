package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String cardNumber;

    private String expiryDate;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    @JsonBackReference(value = "payment")
    private HotelBooking hotelBooking;

}
