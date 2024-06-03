package com.unibuc.tripfinity.service;

import com.unibuc.tripfinity.model.WishlistItem;
import com.unibuc.tripfinity.repository.WishlistItemRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistItemService {

    private final WishlistItemRepository wishlistItemRepository;


    public WishlistItemService(WishlistItemRepository wishlistItemRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
    }

    public WishlistItem addWishlistItem(WishlistItem wishlistItem){

        return wishlistItemRepository.save(wishlistItem);
    }


}
