package com.unibuc.tripfinity.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Destination;
import com.amadeus.resources.FlightDestination;
import com.amadeus.resources.FlightOfferSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.unibuc.tripfinity.mapper.DirectDestinationMapper;
import com.unibuc.tripfinity.mapper.FlightDestinationMapper;
import com.unibuc.tripfinity.mapper.FlightOfferMapper;
import com.unibuc.tripfinity.model.DirectDestinationDTO;
import com.unibuc.tripfinity.model.FlightDestinationDTO;
import com.unibuc.tripfinity.model.FlightOfferDTO;
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

    @Autowired
    private DirectDestinationMapper directDestinationMapper;

    @Autowired
    private FlightOfferMapper flightOfferMapper;

    //direct destinations from origin airport
    public List<FlightDestination> searchFlightsFromAirport(String origin) throws ResponseException {
        Amadeus amadeus = Amadeus.builder(apiKey, apiSecret).build();

        Params params = Params.with("origin", origin);
        FlightDestination[] flightDestinations = amadeus.shopping.flightDestinations.get(params);

        if (flightDestinations[0].getResponse().getStatusCode() != 200) {
            System.out.println("Wrong status code for Flight Inspiration Search: " + flightDestinations[0].getResponse().getStatusCode());
            System.exit(-1);
        }

        System.out.println(Arrays.stream(flightDestinations).toList());
        System.out.println(flightDestinations[0]);
        System.out.println(flightDestinations.length);

        return  Arrays.stream(flightDestinations).toList();
    }


    //flights from an airport to another
    public List<FlightOfferDTO> searchSpecificFlight(String origin, String destination, String departureDate, String returnDate ) throws ResponseException {
        Amadeus amadeus = Amadeus.builder(apiKey, apiSecret).build();

        Params params = Params.with("originLocationCode", origin)
                .and("destinationLocationCode", destination)
                .and("departureDate",  departureDate)
                .and("returnDate", returnDate)
                .and("adults", 1)
                .and("max", 3);

        FlightOfferSearch[] flightOffers = amadeus.shopping.flightOffersSearch.get(params);

        if (flightOffers[0].getResponse().getStatusCode() != 200) {
            System.out.println("Wrong status code for Flight Inspiration Search: " + flightOffers[0].getResponse().getStatusCode());
            System.exit(-1);
        }

        System.out.println("List" + Arrays.stream(flightOffers).toList());
        System.out.println("First" + flightOffers[0]);
        System.out.println("flightOffers length"+flightOffers.length);
        System.out.println("Itineraries:"  + flightOffers[0].getItineraries());

        List<FlightOfferDTO> flightOfferDTOList = convertToFlightOfferDTO(Arrays.stream(flightOffers).toList());

        return flightOfferDTOList;
    }

    public List<DirectDestinationDTO> searchDirectDestinations(String airportCode) throws ResponseException {
        Amadeus amadeus = Amadeus.builder(apiKey, apiSecret).build();
        Params params = Params.with("departureAirportCode", airportCode);
        List<Destination> dests = Arrays.stream(amadeus.airport.directDestinations.get(params)).toList();
        List<DirectDestinationDTO> destinations = convertToDirectDestinationDTO(dests);
        return destinations;

    }


    public List<FlightDestinationDTO> convertToMyFlightList(List<FlightDestination> flightDestinationList){
        List<FlightDestinationDTO> flightsDTO = new ArrayList<>();

        for ( FlightDestination fd : flightDestinationList){
            flightsDTO.add(flightDestinationMapper.mapToDTO(fd));
        }
        return flightsDTO;
    }

    public List<DirectDestinationDTO> convertToDirectDestinationDTO(List<Destination> destinationList){
        List<DirectDestinationDTO> destinationDTOs = new ArrayList<>();

        for ( Destination d : destinationList){
            destinationDTOs.add(directDestinationMapper.mapToDTO(d));
        }
        return destinationDTOs;
    }

    public List<FlightOfferDTO> convertToFlightOfferDTO(List<FlightOfferSearch> flightOfferSearches){
        List<FlightOfferDTO> flightOfferDTOS = new ArrayList<>();

        for ( FlightOfferSearch f : flightOfferSearches){
            flightOfferDTOS.add(flightOfferMapper.mapToDTO(f));
        }
        return flightOfferDTOS;
    }


}
