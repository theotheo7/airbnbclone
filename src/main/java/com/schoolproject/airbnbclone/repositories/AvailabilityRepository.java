package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.Availability;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Availability a SET a.available = false WHERE a.listing.id = ?1 AND a.date BETWEEN ?2 AND ?3")
    void updateAvailabilities(Long listingID, LocalDate fromDate, LocalDate toDate);

}
