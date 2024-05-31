package com.unibuc.tripfinity.mapper;

import com.amadeus.resources.HotelOfferSearch;
import com.unibuc.tripfinity.model.HotelOfferDTO;
import org.springframework.stereotype.Component;

@Component
public class HotelOfferMapper {

    public HotelOfferDTO convertToDto(HotelOfferSearch hotelOfferSearch){
        int beds;
        if (hotelOfferSearch.getOffers()[0].getRoom().getTypeEstimated().getBeds() != null)
        {
            beds = hotelOfferSearch.getOffers()[0].getRoom().getTypeEstimated().getBeds();
        }
        else {
            beds =0;
        }
        HotelOfferDTO hotelOfferDTO = HotelOfferDTO.builder()
                .hotelId(hotelOfferSearch.getHotel().getHotelId())
                .dupeId(hotelOfferSearch.getHotel().getDupeId())
                .cityCode(hotelOfferSearch.getHotel().getCityCode())
                .hotelName(hotelOfferSearch.getHotel().getName())
                .available(hotelOfferSearch.isAvailable())
                .offerId(hotelOfferSearch.getOffers()[0].getId())
                .checkInDate(hotelOfferSearch.getOffers()[0].getCheckInDate())
                .checkOutDate(hotelOfferSearch.getOffers()[0].getCheckOutDate())
                .description(hotelOfferSearch.getOffers()[0].getDescription().getText())
                .roomCategory(hotelOfferSearch.getOffers()[0].getRoom().getTypeEstimated().getCategory())
                .bedType(hotelOfferSearch.getOffers()[0].getRoom().getTypeEstimated().getBedType())
                .noOfBeds(beds)
                .noOfGuests(hotelOfferSearch.getOffers()[0].getGuests().getAdults())
                .price(hotelOfferSearch.getOffers()[0].getPrice().getTotal())
                .currency(hotelOfferSearch.getOffers()[0].getPrice().getCurrency())
                .build();

        return hotelOfferDTO;
    }

}
