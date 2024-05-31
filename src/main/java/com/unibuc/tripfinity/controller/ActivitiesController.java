package com.unibuc.tripfinity.controller;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Activity;
import com.amadeus.resources.FlightDestination;
import com.unibuc.tripfinity.model.ActivityDTO;
import com.unibuc.tripfinity.model.FlightDestinationDTO;
import com.unibuc.tripfinity.service.ActivitiesService;
import com.unibuc.tripfinity.service.AirportService;
import com.unibuc.tripfinity.service.CityService;
import com.unibuc.tripfinity.service.FlightService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivitiesController {

    @Autowired
    private CityService cityService;

    @Autowired
    private ActivitiesService activitiesService;



    @GetMapping("/search")
    public ResponseEntity<List<ActivityDTO>> searchActivities(@RequestParam String city) throws ResponseException, JSONException {
        try {
            List<ActivityDTO> responseMessage = activitiesService.getActivities(city);
            System.out.println(responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (ResponseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchByIdAndCity")
    public ResponseEntity<ActivityDTO> searchActivityByIdAndCity(@RequestParam String id, @RequestParam String city) throws ResponseException, JSONException {
        try {
            ActivityDTO responseMessage = activitiesService.searchActivityById(id, city);
            System.out.println(responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (ResponseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
