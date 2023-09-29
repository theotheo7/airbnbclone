package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.listing.id = ?1")
    void deleteByListing(Long listingID);

    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.user.id = ?1")
    void deleteByUser(Long userID);

}
