package com.unibuc.tripfinity.service;

import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.model.Wishlist;
import com.unibuc.tripfinity.model.WishlistItem;
import com.unibuc.tripfinity.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist addWishList(Wishlist wishlist){
        return wishlistRepository.save(wishlist);
    }

    public List<Wishlist> getWishlistByUserEmail(String email){
        return wishlistRepository.getWishlistsByUserEmail(email);
    }

    public Optional<Wishlist> getByNameAndAndUser(String name, UserInfo userInfo){
        System.out.println("IN SERVICE getByNameAndAndUser");
        System.out.println("NAME: " + name);
        System.out.println("USER INFO "+ userInfo);
        Optional<Wishlist> result = wishlistRepository.findByNameAndAndUser(name, userInfo);
        System.out.println("RESULT "+ result);
        return result;
    }

    public Wishlist addItemToWishlist(Wishlist wishlist, WishlistItem wishlistItem){
        List<WishlistItem> items = wishlist.getWishlistItems();
        items.add(wishlistItem);
        wishlist.setWishlistItems(items);
        System.out.println("wishlist items"+ items);
        return wishlist;
    }





}
