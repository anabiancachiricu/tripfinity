package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
}
