package com.unibuc.tripfinity.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightDestination;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unibuc.tripfinity.mapper.FlightDestinationMapper;
import com.unibuc.tripfinity.model.FlightDestinationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FlightService {

    @Value("${amadeus.api.key}")
    private String apiKey;

    @Value("${amadeus.api.secret}")
    private String apiSecret;
    @Autowired
    private FlightDestinationMapper flightDestinationMapper;

    //direct destinations from origin airport
    public List<FlightDestination> searchFlights(String origin) throws ResponseException {
        Amadeus amadeus = Amadeus.builder(apiKey, apiSecret).build();

        Params params = Params.with("origin", origin);
        FlightDestination[] flightDestinations = amadeus.shopping.flightDestinations.get(params);

//        System.out.println("OFFERS:"+ Arrays.stream(amadeus.shopping.flightOffersSearch.get(params)).toList());

        if (flightDestinations[0].getResponse().getStatusCode() != 200) {
            System.out.println("Wrong status code for Flight Inspiration Search: " + flightDestinations[0].getResponse().getStatusCode());
            System.exit(-1);
        }

        System.out.println(Arrays.stream(flightDestinations).toList());
        System.out.println(flightDestinations[0]);
        System.out.println(flightDestinations.length);

        return  Arrays.stream(flightDestinations).toList();
    }
    
    public List<FlightDestinationDTO> convertToMyFlightList(List<FlightDestination> flightDestinationList){
        List<FlightDestinationDTO> flightsDTO = new ArrayList<>();

        for ( FlightDestination fd : flightDestinationList){
            flightsDTO.add(flightDestinationMapper.mapToDTO(fd));
        }
        return flightsDTO;
    }


}
