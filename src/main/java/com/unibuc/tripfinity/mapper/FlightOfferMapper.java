package com.unibuc.tripfinity.mapper;

import com.amadeus.resources.Destination;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.shopping.FlightOffers;
import com.unibuc.tripfinity.model.DirectDestinationDTO;
import com.unibuc.tripfinity.model.FlightOfferDTO;
import org.springframework.stereotype.Component;

@Component
public class FlightOfferMapper {

    public FlightOfferDTO mapToDTO(FlightOfferSearch flightOfferSearch){
        FlightOfferDTO flightOfferDTO = FlightOfferDTO.builder()
                .id(flightOfferSearch.getId())
                .duration(flightOfferSearch.getItineraries()[0].getDuration())
                .departIataCode(flightOfferSearch.getItineraries()[0].getSegments()[0].getDeparture().getIataCode())
                .departTerminal(flightOfferSearch.getItineraries()[0].getSegments()[0].getDeparture().getTerminal())
                .departTime(flightOfferSearch.getItineraries()[0].getSegments()[0].getDeparture().getAt())
                .departArrilvalIataCode(flightOfferSearch.getItineraries()[0].getSegments()[0].getArrival().getIataCode())
                .departArrivalTime(flightOfferSearch.getItineraries()[0].getSegments()[0].getArrival().getAt())
                .departStops(flightOfferSearch.getItineraries()[0].getSegments()[0].getNumberOfStops())
                .departCarrierCode(flightOfferSearch.getItineraries()[0].getSegments()[0].getCarrierCode())
                .departFlightNumber(flightOfferSearch.getItineraries()[0].getSegments()[0].getNumber())

                .returnIataCode((flightOfferSearch.getItineraries()[1].getSegments()[0].getDeparture().getIataCode()))
                .returnTerminal(flightOfferSearch.getItineraries()[1].getSegments()[0].getDeparture().getTerminal())
                .returnTime(flightOfferSearch.getItineraries()[1].getSegments()[0].getDeparture().getAt())
                .returnArrilvalIataCode(flightOfferSearch.getItineraries()[1].getSegments()[0].getArrival().getIataCode())
                .returnArrivalTime(flightOfferSearch.getItineraries()[1].getSegments()[0].getArrival().getAt())
                .returnStops(flightOfferSearch.getItineraries()[1].getSegments()[0].getNumberOfStops())
                .returnCarrierCode(flightOfferSearch.getItineraries()[1].getSegments()[0].getCarrierCode())
                .returnFlightNumber(flightOfferSearch.getItineraries()[1].getSegments()[0].getNumber())

                .totalPrice(flightOfferSearch.getPrice().getTotal())
                .build();

        return flightOfferDTO;

    }


}
