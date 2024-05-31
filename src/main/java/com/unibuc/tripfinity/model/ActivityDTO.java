package com.unibuc.tripfinity.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.List;


@Getter
@Setter
@Builder
public class ActivityDTO {

    private int id;
    private String activityId;
    private String city;
    private String name;
    private String shortDescription;
    private String description;
    private String rating;
    private String bookingLink;

    private double latitude;
    private double longitude;
    private String price;
    private String currencyCode;
    private String[] pictures;



}
