package com.unibuc.tripfinity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity(name = "favourite")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "favouriteId")
public class Favourite {
    @Id
    @Column(name = "favourite_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int favouriteId;

    private String city;
    private String activId;
    private String activityName;

    @ManyToOne
    @JoinColumn(name = "email")
    @JsonIgnore
//    @JsonBackReference(value = "user_favs")
    private UserInfo user;
}
