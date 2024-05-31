package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> getWishlistsByUserEmail (String email);

    Optional<Wishlist> findByNameAndAndUser(String name, UserInfo userInfo);
}
