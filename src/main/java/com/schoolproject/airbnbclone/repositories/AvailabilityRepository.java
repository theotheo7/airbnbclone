package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}
