package com.unibuc.tripfinity.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AirportInfo {
    private String iataCode;
    private String cityName;
    private String airportName;
}
