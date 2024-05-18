package com.unibuc.tripfinity.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DirectDestinationDTO {

    String cityName;
    String iataCode;
}
