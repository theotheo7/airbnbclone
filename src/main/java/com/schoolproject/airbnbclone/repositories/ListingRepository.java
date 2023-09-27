package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.Listing;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Integer>, PagingAndSortingRepository<Listing, Integer> {

    @Transactional
    @Query("SELECT l FROM Listing l")
    List<Listing> fetchAllListings();

    @NonNull
    @Transactional
    @Query("SELECT l FROM Listing l JOIN FETCH l.location JOIN FETCH l.images JOIN FETCH l.host images WHERE l.host.username = :username")
    Page<Listing> findAllByHostName(@NonNull Pageable pageable, @Param("username") String username);

    @Modifying
    @Transactional
    @Query("DELETE FROM Listing l WHERE l.id = ?1")
    Integer removeListingById(Integer Id);

}
