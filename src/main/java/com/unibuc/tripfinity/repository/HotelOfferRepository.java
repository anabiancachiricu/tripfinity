package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.HotelOfferDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelOfferRepository extends JpaRepository<HotelOfferDTO, String> {
}
