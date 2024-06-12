package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.Favourite;
import com.unibuc.tripfinity.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> getFavouriteByUser_email(String email);

    Optional<Favourite> findFavouriteByActivIdAndUser(String activityId, UserInfo userInfo);


}
