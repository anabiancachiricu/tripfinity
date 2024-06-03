package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Entity(name = "hotel")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "hotelId")
public class HotelDTO {

    @Id
    @Column(name = "hotel_id")
    private String hotelId;

    private String city;
    private String iataCode;

    private String name;
    private String countryCode;
    private double distanceFromCityCenter;
    private String distanceUnit;

    private double longitude;
    private double latitude;

    @OneToMany(mappedBy = "hotelDTO")
//    @JsonManagedReference("hoteldto-offer")
    private List<HotelOfferDTO> hotelOffers;


}
