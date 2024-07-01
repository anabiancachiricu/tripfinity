package com.unibuc.tripfinity.service;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Activity;
import com.unibuc.tripfinity.mapper.ActivityMapper;
import com.unibuc.tripfinity.model.ActivityDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ActivitiesService {

//    @Value("${amadeus.api.key}")
//    private String apiKey;
//
//    @Value("${amadeus.api.secret}")
//    private String apiSecret;

    @Value("${amadeus.api.key.prod}")
    private String apiKey;

    @Value("${amadeus.api.secret.prod}")
    private String apiSecret;

    private final CityService cityService;
    private final ActivityMapper activityMapper;

    public ActivitiesService(CityService cityService, ActivityMapper activityMapper) {
        this.cityService = cityService;
        this.activityMapper = activityMapper;
    }

    public List<ActivityDTO> getActivities(String cityName) throws ResponseException {

        cityName= cityName.toUpperCase();
        Amadeus amadeus = Amadeus
                .builder(apiKey, apiSecret)
                .setHostname("production")
                .build();

        Map<String, Double> cityCoordinates = cityService.getCityCoordinates(cityName);
        Params params = Params
                .with("latitude", cityCoordinates.get("Latitude"))
                .and("longitude", cityCoordinates.get("Longitude"));

        System.out.println("in activities service: ");
        System.out.println("latitude : "+ cityCoordinates.get("Latitude"));
        System.out.println("longitude: "+ cityCoordinates.get("Longitude"));

        List<Activity> activities = Arrays.stream(amadeus.shopping.activities.get(params)).toList();
        List<ActivityDTO> activityDTOList = convertToActivityDTO(activities, cityName);
        System.out.println(activityDTOList);
        return activityDTOList;
    }


    public List<ActivityDTO> convertToActivityDTO(List<Activity> activityList, String city){
        List<ActivityDTO> activityDTOs = new ArrayList<>();

        for (Activity actv: activityList){
            activityDTOs.add(activityMapper.mapToDTO(actv, city));
        }
        return activityDTOs;
    }

    public ActivityDTO searchActivityById(String activityId, String city) throws ResponseException {
        Amadeus amadeus = Amadeus
                .builder(apiKey, apiSecret)
                .setHostname("production")
                .build();

        Activity activity = amadeus.shopping.activity(activityId).get();
        return activityMapper.mapToDTO(activity, city );
    }


}
