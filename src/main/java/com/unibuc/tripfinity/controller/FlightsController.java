package com.unibuc.tripfinity.controller;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Destination;
import com.amadeus.resources.FlightDestination;
import com.amadeus.resources.FlightOfferSearch;
import com.unibuc.tripfinity.model.AirportInfo;
import com.unibuc.tripfinity.model.DirectDestinationDTO;
import com.unibuc.tripfinity.model.FlightDestinationDTO;
import com.unibuc.tripfinity.model.FlightOfferDTO;
import com.unibuc.tripfinity.service.AirportService;
import com.unibuc.tripfinity.service.FlightService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightsController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private AirportService cityService;

    @GetMapping("/search")
    public ResponseEntity<List<FlightDestinationDTO>> searchFlightsFrom(@RequestParam String origin) throws ResponseException, JSONException {
        try {
            List<FlightDestination> responseMessage = flightService.searchFlightsFromAirport(origin);
            System.out.println(responseMessage);
            List<FlightDestinationDTO> response = flightService.convertToMyFlightList(responseMessage);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResponseException e) {
            // Handle ResponseException appropriately
            e.printStackTrace(); // Log the exception for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/origin_airport_search")
    public ResponseEntity<List<AirportInfo>> originAirportSearch(@RequestParam String term) {
        System.out.println("here X2");
        try {
            List<AirportInfo> airports = cityService.getAirports(term);
            return ResponseEntity.ok(airports);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/search_specific_flight")
    public ResponseEntity<List<FlightOfferDTO>> searchFlightsFrom(@RequestParam String origin,
                                                                  @RequestParam String destination,
                                                                  @RequestParam String departureDate,
                                                                  @RequestParam String returnDate,
                                                                  @RequestParam int adults) throws ResponseException, JSONException {
        try {
            List<FlightOfferDTO> responseMessage = flightService.searchSpecificFlight(origin, destination, departureDate, returnDate, adults);
            System.out.println(responseMessage);
//            List<FlightDestinationDTO> response = flightService.convertToMyFlightList(responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (ResponseException e) {
            // Handle ResponseException appropriately
            e.printStackTrace(); // Log the exception for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search_direct_destinations")
    public ResponseEntity<List<DirectDestinationDTO>> searchDirectDestinations(@RequestParam String airportCode) throws ResponseException, JSONException {
        try {
            List<DirectDestinationDTO> responseMessage = flightService.searchDirectDestinations(airportCode);
            System.out.println(responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (ResponseException e) {
            // Handle ResponseException appropriately
            e.printStackTrace(); // Log the exception for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
