package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Entity(name = "hotel_offer")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "offerId")
public class HotelOfferDTO {

    @Id
    @Column(name = "hotel_offer_id")
    private String offerId;

    @Column(name = "hotel_id", insertable = false, updatable = false)
    private String hotelId;
    private String dupeId;
    private String cityCode;
    private String hotelName;
    private boolean available;

    private String checkInDate;
    private String checkOutDate;
    private String description;

    private String roomCategory;
    private String bedType;
    private int noOfBeds;

    private int noOfGuests;
    private String price;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
//    @JsonBackReference(value = "hoteldto-offer")
    private HotelDTO hotelDTO;

//    @JsonIgnore
    @OneToMany(mappedBy = "hotelOfferDTO", cascade = CascadeType.ALL,  fetch=FetchType.LAZY)
//    @JsonManagedReference(value = "hoteloffer")
    private List<HotelBooking> hotelBookingList;
}