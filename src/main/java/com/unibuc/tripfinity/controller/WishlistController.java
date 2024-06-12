package com.unibuc.tripfinity.controller;

import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.model.Wishlist;
import com.unibuc.tripfinity.model.WishlistItem;
import com.unibuc.tripfinity.repository.UserInfoRepository;
import com.unibuc.tripfinity.service.WishlistService;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserInfoRepository userInfoRepository;

    public WishlistController(WishlistService wishlistService, UserInfoRepository userInfoRepository) {
        this.wishlistService = wishlistService;
        this.userInfoRepository = userInfoRepository;
    }

    @GetMapping("/getWishlistsByUser")
    public ResponseEntity<List<Wishlist>> getWishlistsByUser(Authentication authentication){
        String username = authentication.getName();
        List<Wishlist> wishlists = wishlistService.getWishlistByUserEmail(username);
        return new ResponseEntity<>(wishlists, HttpStatus.OK);
    }

    @PostMapping("/addNewWishlist")
    public ResponseEntity<Wishlist> addNewWishlist(Authentication authentication, @RequestParam String name) throws JSONException {
        String username = authentication.getName();

        try{
            Wishlist wishlist = Wishlist.builder()
                    .name(name)
                    .user(userInfoRepository.findByUsername(username).get())
                    .build();
            Wishlist wishlistAdded = wishlistService.addWishList(wishlist);
            return new ResponseEntity<>(wishlistAdded, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/addItemToWishlist")
    public ResponseEntity<Wishlist> addItemToWishlist(Authentication authentication,
                                                      @RequestParam String activityId,
                                                      @RequestParam String city,
                                                      @RequestParam String wishlistName) throws JSONException {
        String username = authentication.getName();
        Optional<UserInfo> user = userInfoRepository.findByUsername(username);
        try{
            Optional<Wishlist> wishlist = wishlistService.getByNameAndAndUser(wishlistName, user.get());
            Wishlist selectedWishlist;
            if(wishlist.isEmpty()){
                 selectedWishlist = wishlistService.addWishList(Wishlist.builder()
                                .user(user.get())
                                .name(wishlistName)
                        .build());
            }
            else {
                 selectedWishlist = wishlist.get();
            }
            WishlistItem item = WishlistItem.builder()
                    .activityId(activityId)
                    .city(city)
                    .wishlist(selectedWishlist)
                    .build();
            System.out.println("item :"+ item);
            selectedWishlist = wishlistService.addItemToWishlist(selectedWishlist, item);
            System.out.println("selectedWishlist :"+ selectedWishlist);
            return new ResponseEntity<>(selectedWishlist, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }




}
