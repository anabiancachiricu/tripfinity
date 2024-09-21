package com.unibuc.tripfinity.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.City;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CityService {

    Dotenv amadeusEnv = Dotenv.load();
    private String apiKey = amadeusEnv.get("AMADEUS_API_KEY_PROD");
    private String apiSecret = amadeusEnv.get("AMADEUS_API_SECRET_PROD");


    public Map<String, Double> getCityCoordinates(String city) throws ResponseException {

        Amadeus amadeus = Amadeus
                .builder(apiKey, apiSecret)
                .setHostname("production")
                .build();
        System.out.println("AMADEUS: " + amadeus);

        try {
            City[] cities = amadeus.referenceData.locations.cities.get(
                    Params.with("keyword", city)
            );


            if (cities[0].getResponse().getStatusCode() != 200) {
                System.out.println("Wrong status code: " + cities[0].getResponse().getStatusCode());
                System.exit(-1);
            }

            Map<String, Double> cityCoordinates = new HashMap<>();
            cityCoordinates.put("Latitude", cities[0].getGeoCode().getLatitude());
            cityCoordinates.put("Longitude", cities[0].getGeoCode().getLongitude());
            System.out.println("in cityservice: ");
            System.out.println("latitude : " + cityCoordinates.get("Latitude"));
            System.out.println("longitude: " + cityCoordinates.get("Longitude"));

            return cityCoordinates;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e.getMessage());
        }


    }


}
