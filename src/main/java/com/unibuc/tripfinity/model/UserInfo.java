package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.*;
import com.unibuc.tripfinity.validator.Email;
import com.unibuc.tripfinity.validator.OnlyLetters;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email")

public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;

    @NotNull
    @OnlyLetters
    private String firstName;

    @NotNull
    @OnlyLetters
    private String lastName;

    @Column(unique = true, nullable = false)
    @Email
    private String email;
    private String password;
    private String roles;
    private LocalDate birthDate;
    private String description;

    private String profilePictureEtag;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference(value = "user_wishlist")
    private List<Wishlist> wishlists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference(value = "user_favs")
    private List<Favourite> favourites;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference(value = "email")
    private List<HotelBooking> hotelBookingList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference(value = "email_flight")
    private List<FlightBooking> flightBookingList;

}
