package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.Favourite;
import com.unibuc.tripfinity.model.HotelBooking;
import com.unibuc.tripfinity.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {

    List<HotelBooking> findAllByUser_Email(String email);
    List<HotelBooking> findAllByUser_EmailAndBookingId(String email, Long id);
}
