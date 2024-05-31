package com.unibuc.tripfinity.service;

import com.unibuc.tripfinity.model.Favourite;
import com.unibuc.tripfinity.model.UserInfo;
import com.unibuc.tripfinity.repository.FavouriteRepository;
import com.unibuc.tripfinity.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final UserInfoRepository userInfoRepository;

    public FavouriteService(FavouriteRepository favouriteRepository,
                            UserInfoRepository userInfoRepository) {
        this.favouriteRepository = favouriteRepository;
        this.userInfoRepository = userInfoRepository;
    }

    public Favourite addToFavourite(Favourite favourite) throws Exception {
        System.out.println("SUNT IN addToFavourite");
        Optional<Favourite> favOpt = getByActivity_idAndUser(favourite.getActivId(), favourite.getUser());
        if(!favOpt.isPresent()){
            return favouriteRepository.save(favourite);
        }
        else {
            throw new Exception("Exista deja in lista de favorite");
        }

    }

    public List<Favourite>  getFavourites(String email){
        return favouriteRepository.getFavouriteByUser_email(email);
    }

    public Optional<Favourite> getByActivity_idAndUser(String activityId, UserInfo user){
        return favouriteRepository.findFavouriteByActivIdAndUser(activityId, user);
    }


}
