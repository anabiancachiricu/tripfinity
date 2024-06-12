package com.unibuc.tripfinity.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Destination;
import com.amadeus.resources.FlightDestination;
import com.amadeus.resources.FlightOfferSearch;
import com.unibuc.tripfinity.mapper.DirectDestinationMapper;
import com.unibuc.tripfinity.mapper.FlightDestinationMapper;
import com.unibuc.tripfinity.mapper.FlightOfferMapper;
import com.unibuc.tripfinity.model.DirectDestinationDTO;
import com.unibuc.tripfinity.model.Flight;
import com.unibuc.tripfinity.model.FlightDestinationDTO;
import com.unibuc.tripfinity.model.FlightOfferDTO;
import com.unibuc.tripfinity.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Value("${amadeus.api.key}")
    private String apiKey;

    @Value("${amadeus.api.secret}")
    private String apiSecret;

    private final FlightDestinationMapper flightDestinationMapper;


    private final DirectDestinationMapper directDestinationMapper;

    private final FlightOfferMapper flightOfferMapper;

    private final FlightRepository flightRepository;

    public FlightService(FlightDestinationMapper flightDestinationMapper,
                         DirectDestinationMapper directDestinationMapper,
                         FlightOfferMapper flightOfferMapper,
                         FlightRepository flightRepository) {
        this.flightDestinationMapper = flightDestinationMapper;
        this.directDestinationMapper = directDestinationMapper;
        this.flightOfferMapper = flightOfferMapper;
        this.flightRepository = flightRepository;
    }

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
    public List<FlightOfferDTO> searchSpecificFlight(String origin, String destination, String departureDate, String returnDate, int adults ) throws ResponseException {
        Amadeus amadeus = Amadeus.builder(apiKey, apiSecret).build();

        Params params = Params.with("originLocationCode", origin)
                .and("destinationLocationCode", destination)
                .and("departureDate",  departureDate)
                .and("returnDate", returnDate)
                .and("adults", adults)
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
        //Creare client Amadeus
        Amadeus amadeus = Amadeus.builder(apiKey, apiSecret).build();
        //Setarea parametrilor necesari
        Params params = Params.with("departureAirportCode", airportCode);
        //Request-ul realizat către Amadeus si salvarea lui într-o listă
        List<Destination> dests = Arrays.stream(amadeus.airport.directDestinations.get(params)).toList();
        //Maparea rezultatului primit la modelul de date creat de aplicația Tripfinity
        List<DirectDestinationDTO> destinations = convertToDirectDestinationDTO(dests);
        //Returnarea rezultatului final
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

    public Flight addFlight(Flight flight){
        return flightRepository.save(flight);
    }

    public Optional<Flight> getFlightByCarrierCodeAndAndFlightNumber(String carrierCode, String flightNumber){
        return flightRepository.findByCarrierCodeAndAndFlightNumber(carrierCode, flightNumber);
    }


}
