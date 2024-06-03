package com.unibuc.tripfinity.controller;

import com.unibuc.tripfinity.model.*;
import com.unibuc.tripfinity.repository.FlightRepository;
import com.unibuc.tripfinity.repository.PaymentRepository;
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
    private final PaymentRepository paymentRepository;


    public FlightBookingController(FlightBookingService flightBookingService, FlightService flightService, PassengerService passengerService, DocumentService documentService,
                                   FlightRepository flightRepository,
                                   PaymentRepository paymentRepository) {
        this.flightBookingService = flightBookingService;
        this.flightService = flightService;
        this.passengerService = passengerService;
        this.documentService = documentService;
        this.flightRepository = flightRepository;
        this.paymentRepository = paymentRepository;
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<FlightBooking> createFlightBooking(Authentication authentication, @RequestBody FlightBooking flightBooking) {
        if (flightBooking == null || flightBooking.getDepartureFlight() == null ||
                flightBooking.getReturnFlight() == null || flightBooking.getPassengerList() == null) {
            System.out.println("CEVA E NULL");
            System.out.println("flightBooking: " + flightBooking);
            System.out.println("DEPARTURE FLIGHT" + flightBooking.getDepartureFlight());
            System.out.println("RETURN FLIGHT" + flightBooking.getReturnFlight());
            System.out.println("PASSENGERS" + flightBooking.getPassengerList());
            System.out.println("PAYMENT " + flightBooking.getPayment());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String username = authentication.getName();
        System.out.println("USERNAME : " + username);


        try {
            handleFlights(flightBooking);
            System.out.println("AM IESIT DIN HANDLE FLIGHTS");
            System.out.println(flightBooking.getDepartureFlight());
            System.out.println(flightBooking.getReturnFlight());
            handlePassengers(flightBooking);
            System.out.println("AM IESIT DIN HANDLE PASSENGERS");
            System.out.println(flightBooking.getPassengerList());
            handlePayment(flightBooking);
            System.out.println("AM IESIT DIN HANDLE PAYMENT");
            System.out.println(flightBooking.getPayment());

            FlightBooking booking = flightBookingService.addFlightBooking(username,flightBooking);
            System.out.println("AM TRECUT DE ADDFLIGHTBOOKING");
            System.out.println(flightBooking);
            flightBooking.getPayment().setFlightBooking(flightBooking);
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
        System.out.println("SUNT IN HANDLE PASSENGERS");
        for (Passenger pass : flightBooking.getPassengerList()) {
            // First, save the passenger
            System.out.println("PASSENGER: "+ pass);
            Passenger passenger  = Passenger.builder().email(pass.getEmail())
                    .firstName(pass.getFirstName())
                    .lastName(pass.getLastName())
                    .gender(pass.getGender())
                    .phoneNumber(pass.getGender()).build();
            Passenger savedPassenger = passengerService.addPassanger(passenger);
            System.out.println("SAVED PASSENGER: "+savedPassenger);

            // Now, handle the documents for this saved passenger
            List<Document> docs = new ArrayList<>();
            for (Document doc : pass.getDocuments()) {
                System.out.println("DOC: "+ doc);
                doc.setPassenger(savedPassenger); // Ensure the document is associated with the saved passenger
                Document addedDoc = documentService.addDocument(doc);
                docs.add(addedDoc);
            }
            savedPassenger.setDocuments(docs);

            passengers.add(savedPassenger);
        }
        flightBooking.setPassengerList(passengers);
    }

    private void handlePayment(FlightBooking flightBooking) throws Exception {
        Payment payment = new Payment();
        payment.setPaymentType(flightBooking.getPayment().getPaymentType());
        payment.setCardNumber(flightBooking.getPayment().getCardNumber());
        payment.setExpiryDate(flightBooking.getPayment().getExpiryDate());
        Payment savedPayment = paymentRepository.save(payment);
        flightBooking.setPayment(savedPayment);

    }

    @GetMapping("/getFlightBookingsByEmail")
    public ResponseEntity<List<FlightBooking>> getFlightBookingByEmail(Authentication authentication){
        String username = authentication.getName();
        List<FlightBooking> flightBookings = flightBookingService.getFlightsForUser(username);
        return ResponseEntity.ok(flightBookings);
    }

    @GetMapping("/getFlightBookingByEmailAndId")
    public ResponseEntity<FlightBooking> getFlightBookingByEmailAndId(Authentication authentication,@RequestParam int flightBookingId){
        String username = authentication.getName();
        Optional<FlightBooking> flightBooking = flightBookingService.getFlightBookingById(username, flightBookingId);
        if(flightBooking.isPresent()){
            return ResponseEntity.ok(flightBooking.get());
        }
        else {
            return ResponseEntity.status(500).build();
        }

    }




}
