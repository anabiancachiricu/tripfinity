package com.unibuc.tripfinity.controller;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Destination;
import com.amadeus.resources.FlightDestination;
import com.amadeus.resources.Location;
import com.unibuc.tripfinity.model.FlightDestinationDTO;
import com.unibuc.tripfinity.service.CityService;
import com.unibuc.tripfinity.service.FlightService;
import org.json.JSONException;
import org.json.JSONObject;
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
    private CityService cityService;

    @GetMapping("/search")
    public ResponseEntity<List<FlightDestinationDTO>> searchFlightsFrom(@RequestParam String origin) throws ResponseException, JSONException {
        try {
            List<FlightDestination> responseMessage = flightService.searchFlights(origin);
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
    public ResponseEntity<List<String>> originAirportSearch(@RequestParam String term) {
        System.out.println("here X2");
        try {
            List<String> airports = cityService.getAirports(term);
            return ResponseEntity.ok(airports);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

}
