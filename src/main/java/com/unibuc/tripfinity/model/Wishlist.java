package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@Data
@Entity(name = "wishlist")
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist {

    @Id
    @Column(name = "wishlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishlistId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "email")
    @JsonBackReference(value = "user_wishlist")
    private UserInfo user;

    @OneToMany(mappedBy = "wishlist")
    @JsonManagedReference (value = "wishlist")
    private List<WishlistItem> wishlistItems;

}
