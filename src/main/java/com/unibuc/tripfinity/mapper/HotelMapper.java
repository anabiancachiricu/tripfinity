package com.unibuc.tripfinity.mapper;

import com.amadeus.resources.Hotel;
import com.unibuc.tripfinity.model.HotelDTO;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    public HotelDTO mapToDto(Hotel hotel, String city){
        double dist;
        String unit;

        HotelDTO hotelDTO = HotelDTO.builder()
                .city(city)
                .name(hotel.getName())
                .iataCode(hotel.getIataCode())
                .hotelId(hotel.getHotelId())
                .longitude(hotel.getGeoCode().getLongitude())
                .latitude(hotel.getGeoCode().getLatitude())
                .build();
        if(hotel.getDistance() != null){
            hotelDTO.setDistanceFromCityCenter(hotel.getDistance().getValue());
            hotelDTO.setDistanceUnit(hotel.getDistance().getUnit());
        }

        return hotelDTO;
    }

}
