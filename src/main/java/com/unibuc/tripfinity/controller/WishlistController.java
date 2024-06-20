package com.unibuc.tripfinity.controller;

import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.model.Wishlist;
import com.unibuc.tripfinity.model.WishlistItem;
import com.unibuc.tripfinity.repository.UserInfoRepository;
import com.unibuc.tripfinity.repository.WishlistItemRepository;
import com.unibuc.tripfinity.service.WishlistService;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserInfoRepository userInfoRepository;
    private final WishlistItemRepository wishlistItemRepository;

    public WishlistController(WishlistService wishlistService, UserInfoRepository userInfoRepository,
                              WishlistItemRepository wishlistItemRepository) {
        this.wishlistService = wishlistService;
        this.userInfoRepository = userInfoRepository;
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @GetMapping("/getWishlistsByUser")
    public ResponseEntity<List<Wishlist>> getWishlistsByUser(Authentication authentication){
        String username = authentication.getName();
        List<Wishlist> wishlists = wishlistService.getWishlistByUserEmail(username);
        return new ResponseEntity<>(wishlists, HttpStatus.OK);
    }

    @GetMapping("/getWishlistsNameByUser")
    public ResponseEntity<List<String>> getWishlistsNameByUser(Authentication authentication){
        String username = authentication.getName();
        List<Wishlist> wishlists = wishlistService.getWishlistByUserEmail(username);
        List<String> wishlistNames = new ArrayList<>();
        for (Wishlist wishlist : wishlists){
            wishlistNames.add(wishlist.getName());
        }
        return new ResponseEntity<>(wishlistNames, HttpStatus.OK);
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
                                                      @RequestParam String activityName,
                                                      @RequestParam String city,
                                                      @RequestParam String wishlistName) throws JSONException {

        String username = authentication.getName();
        Optional<UserInfo> user = userInfoRepository.findByUsername(username);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            Wishlist selectedWishlist;

            Optional<Wishlist> wishlist = wishlistService.getByNameAndAndUser(wishlistName, user.get());
            if (wishlist.isEmpty()) {
                selectedWishlist = wishlistService.addWishList(Wishlist.builder()
                        .user(user.get())
                        .name(wishlistName)
                        .wishlistItems(new ArrayList<>())
                        .build());
            } else {
                selectedWishlist = wishlist.get();
            }

            WishlistItem item = WishlistItem.builder()
                    .activityId(activityId)
                    .activityName(activityName)
                    .city(city)
                    .wishlist(selectedWishlist)
                    .build();

            item = wishlistItemRepository.save(item);
            selectedWishlist = wishlistService.addItemToWishlist(selectedWishlist, item);

            return new ResponseEntity<>(selectedWishlist, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
