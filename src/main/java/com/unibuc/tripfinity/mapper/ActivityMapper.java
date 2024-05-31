package com.unibuc.tripfinity.mapper;

import com.amadeus.resources.Activity;
import com.unibuc.tripfinity.model.ActivityDTO;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityDTO mapToDTO(Activity activity, String city){
        ActivityDTO activityDTO = ActivityDTO.builder()
                .activityId(activity.getId())
                .city(city)
                .name(activity.getName())
                .shortDescription(activity.getShortDescription())
                .description(activity.getDescription())
                .rating(activity.getRating())
                .bookingLink(activity.getBookingLink())
                .latitude(activity.getGeoCode().getLatitude())
                .longitude(activity.getGeoCode().getLongitude())
                .price(activity.getPrice().getAmount())
                .currencyCode(activity.getPrice().getCurrencyCode())
                .pictures(activity.getPictures())
                .build();

        return activityDTO;
    }



}
