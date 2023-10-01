package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.models.SearchHistory;
import com.schoolproject.airbnbclone.models.SearchHistoryID;
import com.schoolproject.airbnbclone.models.User;
import com.schoolproject.airbnbclone.repositories.SearchHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;

    @Transactional
    public void recordUserInteraction(User user, Listing listing) {
        final Long interactionsCeiling = 10L;
        SearchHistoryID searchHistoryID = new SearchHistoryID(listing.getId(), user.getId());
        if (this.searchHistoryRepository.existsById(searchHistoryID))
            this.searchHistoryRepository.updateInteractionsById(searchHistoryID, interactionsCeiling);
        else
            this.searchHistoryRepository.save(new SearchHistory(searchHistoryID, listing, user));
    }
}
