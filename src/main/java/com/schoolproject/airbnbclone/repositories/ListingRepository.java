package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.Listing;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Integer> {

    @Transactional
    @Query("SELECT l FROM Listing l")
    List<Listing> fetchAllListings();

    @Modifying
    @Transactional
    @Query("DELETE FROM Listing l WHERE l.Id = ?1")
    Integer removeListingById(Integer Id);

}
