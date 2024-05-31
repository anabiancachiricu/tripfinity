package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.HotelGuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelGuestRepository extends JpaRepository<HotelGuest, Long> {
}
