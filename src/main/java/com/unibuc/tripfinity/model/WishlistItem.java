package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity(name = "wishlist_item")
@AllArgsConstructor
@NoArgsConstructor
public class WishlistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String activityId;
    private String city;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    @JsonBackReference(value = "wishlist")
    private Wishlist wishlist;
}
