package com.unibuc.tripfinity.mapper;

import com.unibuc.tripfinity.model.DirectDestinationDTO;
import com.amadeus.resources.Destination;
import org.springframework.stereotype.Component;

@Component
public class DirectDestinationMapper {

    public DirectDestinationDTO mapToDTO(Destination destination) {
        DirectDestinationDTO directDestinationDTO = DirectDestinationDTO.builder()
                .cityName(destination.getName())
                .iataCode(destination.getIataCode())
                .build();
        return directDestinationDTO;
    }
}