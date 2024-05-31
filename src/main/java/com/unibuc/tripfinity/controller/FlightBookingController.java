package com.unibuc.tripfinity.controller;

import com.unibuc.tripfinity.model.*;
import com.unibuc.tripfinity.repository.FlightRepository;
import com.unibuc.tripfinity.service.DocumentService;
import com.unibuc.tripfinity.service.FlightBookingService;
import com.unibuc.tripfinity.service.FlightService;
import com.unibuc.tripfinity.service.PassengerService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flightBooking")
public class FlightBookingController {

    private final FlightBookingService flightBookingService;
    private final FlightService flightService;
    private final PassengerService passengerService;
    private final DocumentService documentService;
    private final FlightRepository flightRepository;

    public FlightBookingController(FlightBookingService flightBookingService, FlightService flightService, PassengerService passengerService, DocumentService documentService,
                                   FlightRepository flightRepository) {
        this.flightBookingService = flightBookingService;
        this.flightService = flightService;
        this.passengerService = passengerService;
        this.documentService = documentService;
        this.flightRepository = flightRepository;
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<FlightBooking> createFlightBooking(Authentication authentication, @RequestBody FlightBooking flightBooking) {
        if (flightBooking == null || flightBooking.getDepartureFlight() == null ||
                flightBooking.getReturnFlight() == null || flightBooking.getPassengerList() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String username = authentication.getName();


        try {
            handleFlights(flightBooking);
            handlePassengers(flightBooking);

            FlightBooking booking = flightBookingService.addFlightBooking(username,flightBooking);
            return new ResponseEntity<>(booking, HttpStatus.CREATED);

        } catch (Exception e) {
            System.out.println("Error creating flight booking");
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void handleFlights(FlightBooking flightBooking) throws Exception {
        Optional<Flight> departureFlight = flightService.getFlightByCarrierCodeAndAndFlightNumber(
                flightBooking.getDepartureFlight().getCarrierCode(),
                flightBooking.getDepartureFlight().getFlightNumber());

        Optional<Flight> retFlight = flightService.getFlightByCarrierCodeAndAndFlightNumber(
                flightBooking.getReturnFlight().getCarrierCode(),
                flightBooking.getReturnFlight().getFlightNumber());

        if (departureFlight.isEmpty()) {
            Flight dep = flightService.addFlight(flightBooking.getDepartureFlight());
            flightBooking.setDepartureFlight(dep);
        } else {
            flightBooking.setDepartureFlight(departureFlight.get());
        }

        if (retFlight.isEmpty()) {
            Flight ret = flightService.addFlight(flightBooking.getReturnFlight());
            flightBooking.setReturnFlight(ret);
        } else {
            flightBooking.setReturnFlight(retFlight.get());
        }
    }

    private void handlePassengers(FlightBooking flightBooking) throws Exception {
        List<Passenger> passengers = new ArrayList<>();
        for (Passenger pass : flightBooking.getPassengerList()) {
            // First, save the passenger
            Passenger savedPassenger = passengerService.addPassanger(pass);

            // Now, handle the documents for this saved passenger
            List<Document> docs = new ArrayList<>();
            for (Document doc : savedPassenger.getDocuments()) {
                doc.setPassenger(savedPassenger); // Ensure the document is associated with the saved passenger
                Document addedDoc = documentService.addDocument(doc);
                docs.add(addedDoc);
            }
            savedPassenger.setDocuments(docs);

            passengers.add(savedPassenger);
        }
        flightBooking.setPassengerList(passengers);
    }

    @GetMapping("/getFlightBookingsByEmail")
    public ResponseEntity<List<FlightBooking>> getFlightBookingByEmail(Authentication authentication){
        String username = authentication.getName();
        List<FlightBooking> flightBookings = flightBookingService.getFlightsForUser(username);
        return ResponseEntity.ok(flightBookings);
    }


}
