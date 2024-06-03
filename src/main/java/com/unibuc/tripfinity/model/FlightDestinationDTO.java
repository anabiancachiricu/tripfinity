package com.unibuc.tripfinity.model;

import com.amadeus.resources.FlightDestination;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class FlightDestinationDTO {
    private String origin;
    private String destination;
    private Date departureDate;
    private Date returnDate;
    private double price;
}
