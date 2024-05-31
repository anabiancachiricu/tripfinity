package com.unibuc.tripfinity.repository;

import com.unibuc.tripfinity.model.HotelDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelDTO, Long> {
}
