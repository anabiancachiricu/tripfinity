package com.unibuc.tripfinity.controller;

import com.unibuc.tripfinity.model.ActivityDTO;
import com.unibuc.tripfinity.model.Favourite;
import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.repository.UserInfoRepository;
import com.unibuc.tripfinity.service.FavouriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favourite")
public class FavouriteController {

    private final FavouriteService favouriteService;
    private final UserInfoRepository userInfoRepository;

    public FavouriteController(FavouriteService favouriteService,
                               UserInfoRepository userInfoRepository) {
        this.favouriteService = favouriteService;
        this.userInfoRepository = userInfoRepository;
    }

    @GetMapping("/getFavouritesForUser")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<Favourite>> getFavourites(Authentication authentication) {
        String username = authentication.getName();
        try{
            List<Favourite> favourites = favouriteService.getFavourites(username);
            return new ResponseEntity<>(favourites, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addToFavouritesForUser")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Favourite> addToFavourite(Authentication authentication, @RequestParam String activityId,
                                                    @RequestParam String city, @RequestParam String activityName) {
        city = city.toUpperCase();
        System.out.println("AM INTRAT IN addToFavouritesForUser");
        String username = authentication.getName();
        System.out.println("USERNAMME: "+ username);
        try{
            Favourite favouriteToAdd = Favourite.builder()
                    .user(userInfoRepository.findByUsername(username).get())
                    .activId(activityId)
                    .city(city)
                    .activityName(activityName)
                    .build();
            System.out.println("favouriteToAdd: "+ favouriteToAdd.toString());
            Favourite favouriteAdded = favouriteService.addToFavourite(favouriteToAdd);
            System.out.println("favouriteAdded: "+ favouriteAdded.toString());
            return new ResponseEntity<>(favouriteAdded, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
