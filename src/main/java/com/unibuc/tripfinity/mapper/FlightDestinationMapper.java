package com.unibuc.tripfinity.mapper;

import com.amadeus.resources.FlightDestination;
import com.unibuc.tripfinity.model.FlightDestinationDTO;
import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.model.UserInfoDTO;
import org.springframework.stereotype.Component;

@Component
public class FlightDestinationMapper {

    public FlightDestinationDTO mapToDTO (FlightDestination flightDestination){

        return FlightDestinationDTO.builder()
                .destination(flightDestination.getDestination())
                .departureDate(flightDestination.getDepartureDate())
                .origin(flightDestination.getOrigin())
                .price(flightDestination.getPrice().getTotal())
                .returnDate(flightDestination.getReturnDate())
                .build();

    }
}

