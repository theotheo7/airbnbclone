package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.SearchHistory;
import com.schoolproject.airbnbclone.models.SearchHistoryID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, SearchHistoryID> {

    @Transactional
    @Modifying
    @Query("UPDATE SearchHistory s SET s.interactions = s.interactions + 1 WHERE s.id = ?1 and s.interactions < ?2")
    void updateInteractionsById(SearchHistoryID searchHistoryID, Long interactionsCeiling);

    @Query("SELECT s FROM SearchHistory s WHERE s.id.listingID IN ?1")
    List<SearchHistory> findAllByListingId(List<Long> sortedListings);

}
