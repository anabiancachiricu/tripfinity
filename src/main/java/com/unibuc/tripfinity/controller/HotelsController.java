package com.unibuc.tripfinity.controller;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Hotel;
import com.amadeus.resources.HotelOfferSearch;
import com.unibuc.tripfinity.model.ActivityDTO;
import com.unibuc.tripfinity.model.HotelDTO;
import com.unibuc.tripfinity.model.HotelOfferDTO;
import com.unibuc.tripfinity.service.CityService;
import com.unibuc.tripfinity.service.HotelService;
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
@RequestMapping("/hotels")
public class HotelsController {

    @Autowired
    private CityService cityService;

    @Autowired
    private HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<List<HotelDTO>> searchHotels(@RequestParam String city) throws ResponseException, JSONException {
        try {
            List<HotelDTO> responseMessage = hotelService.getHotels(city);
            System.out.println(responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (ResponseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("searchById")
    public ResponseEntity<List<HotelOfferDTO>> searchHotelOffer(@RequestParam String hotelId,
                                                                @RequestParam String checkIn,
                                                                @RequestParam String checkOut,
                                                                @RequestParam int adults){
        try {
            List<HotelOfferDTO> offers = hotelService.searchOffer(hotelId, checkIn, checkOut, adults);
            return new ResponseEntity<>(offers, HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
