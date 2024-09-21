package com.unibuc.tripfinity.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Hotel;
import com.amadeus.resources.HotelOfferSearch;
import com.unibuc.tripfinity.mapper.HotelMapper;
import com.unibuc.tripfinity.mapper.HotelOfferMapper;
import com.unibuc.tripfinity.model.HotelDTO;
import com.unibuc.tripfinity.model.HotelOfferDTO;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class HotelService {

    Dotenv amadeusEnv = Dotenv.load();
    private String apiKey = amadeusEnv.get("AMADEUS_API_KEY_PROD");
    private String apiSecret = amadeusEnv.get("AMADEUS_API_SECRET_PROD");

    private final CityService cityService;
    private final HotelMapper hotelMapper;
    private final HotelOfferMapper hotelOfferMapper;


    public HotelService(CityService cityService, HotelMapper hotelMapper, HotelOfferMapper hotelOfferMapper) {
        this.cityService = cityService;
        this.hotelMapper = hotelMapper;
        this.hotelOfferMapper = hotelOfferMapper;
    }

    public List<HotelDTO> getHotels(String cityName) throws ResponseException {

        cityName= cityName.toUpperCase();
        Amadeus amadeus = Amadeus
                .builder(apiKey, apiSecret)
                .setHostname("production")
                .build();

        Map<String, Double> cityCoordinates = cityService.getCityCoordinates(cityName);
        Params params = Params
                .with("latitude", cityCoordinates.get("Latitude"))
                .and("longitude", cityCoordinates.get("Longitude"));

        System.out.println("in activities service: ");
        System.out.println("latitude : "+ cityCoordinates.get("Latitude"));
        System.out.println("longitude: "+ cityCoordinates.get("Longitude"));

        List<Hotel> hotels = Arrays.stream(amadeus.referenceData.locations.hotels.byGeocode.get(params)).toList();
//        System.out.println(hotels);
        List<HotelDTO> hotelDTOList = convertToHotelDTO(hotels, cityName);
//        System.out.println(hotelDTOList);

        return hotelDTOList;

    }



    public List<HotelDTO> convertToHotelDTO(List<Hotel> hotelList, String city){
        List<HotelDTO> hotelDTOS = new ArrayList<>();

        for (Hotel hot: hotelList){
            hotelDTOS.add(hotelMapper.mapToDto(hot, city));
        }
        return hotelDTOS;
    }

    public List<HotelOfferDTO> searchOffer(String hotelId, String checkIn, String checkOut, int adults) throws ResponseException {
        Amadeus amadeus = Amadeus
                .builder(apiKey, apiSecret)
                .setHostname("production")
                .build();

        //Setarea parametrilor
        Params params = Params.with("hotelIds", hotelId)
                .and("checkInDate", checkIn)
                .and("checkOutDate", checkOut)
                .and("adults", adults);
        //Obținerea rezultatelor
        HotelOfferSearch[] offers =  amadeus.shopping.hotelOffersSearch.get(params);
        List<HotelOfferSearch> searchOffers = Arrays.stream(offers).toList();
        //Maparea rezultatelor
        List<HotelOfferDTO> hotelOfferDTOS = convertToHotelOfferDTO(searchOffers);
        return hotelOfferDTOS;

    }

    public List<HotelOfferDTO> convertToHotelOfferDTO(List<HotelOfferSearch> hotelList){
        List<HotelOfferDTO> hotelDTOS = new ArrayList<>();

        for (HotelOfferSearch hot: hotelList){
            hotelDTOS.add(hotelOfferMapper.convertToDto(hot));
        }
        return hotelDTOS;
    }

    public HotelDTO searchHotelById(String hotelId, String city) throws ResponseException {
        Amadeus amadeus = Amadeus
                .builder(apiKey, apiSecret)
                .setHostname("production")
                .build();
        System.out.println("HOTEL ID IN SEARCH: "+ hotelId);
        List<String> hotelIdList = new ArrayList<>();
        hotelIdList.add(hotelId);
        System.out.println("HOTEL ID LIST IN SEARCH: "+ hotelIdList);

        Params params = Params.with("hotelIds",hotelIdList );
        List<Hotel> hotels = Arrays.stream(amadeus.referenceData.locations.hotels.byHotels.get(params)).toList();
        System.out.println("HOTELS "+hotels);
        List<HotelDTO> hotelDTOS = convertToHotelDTO(hotels, city);
        return hotelDTOS.get(0);
    }


}
