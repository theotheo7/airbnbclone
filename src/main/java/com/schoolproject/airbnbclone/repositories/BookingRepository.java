package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
