package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "wishlistId")
public class Wishlist {

    @Id
    @Column(name = "wishlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishlistId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "email")
    @JsonIgnore
//    @JsonBackReference(value = "user_wishlist")
    private UserInfo user;

    @OneToMany(mappedBy = "wishlist")
    @JsonManagedReference (value = "wishlist")
    private List<WishlistItem> wishlistItems;

}
