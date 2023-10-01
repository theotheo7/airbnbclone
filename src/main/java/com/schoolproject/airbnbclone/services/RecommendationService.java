package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.dtos.listing.response.ListingBasicDetails;
import com.schoolproject.airbnbclone.exceptions.UserException;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.models.SearchHistory;
import com.schoolproject.airbnbclone.models.User;
import com.schoolproject.airbnbclone.repositories.ListingRepository;
import com.schoolproject.airbnbclone.repositories.SearchHistoryRepository;
import com.schoolproject.airbnbclone.repositories.UserRepository;
import com.schoolproject.airbnbclone.utils.recommendations.RecommendationMatrix;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final RecommendationMatrix recommendationMatrix;

    public RecommendationService(UserRepository userRepository, ListingRepository listingRepository, SearchHistoryRepository searchHistoryRepository) {
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
        this.searchHistoryRepository = searchHistoryRepository;
        this.recommendationMatrix = new RecommendationMatrix();
    }

    public List<ListingBasicDetails> recommend(Authentication authentication) {
        Optional<User> optionalUser = this.userRepository.findByUsername(authentication.getName());
        if (optionalUser.isEmpty()) {
            throw new UserException(UserException.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        User user = optionalUser.get();
        final Integer maximumRecommendations = 10;
        List<Long> recommendations = this.recommendationMatrix.recommend(user.getId(), maximumRecommendations);
        List<Listing> listings = this.listingRepository.findRecommendationsByIds(recommendations);
        return listings.stream()
                .map(ListingBasicDetails::new)
                .collect(Collectors.toList());
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 7200)
    public void factorize() {
        List<Long> sortedUsers = this.userRepository.findSortedIDs();
        final int maxListings = 150;
        PageRequest pageRequest = PageRequest.of(0, maxListings, Sort.by("id").ascending());
        List<Long> sortedListings = this.listingRepository.findListingIds(pageRequest);
        List<SearchHistory> searchHistoryRecords = this.searchHistoryRepository.findAllByListingId(sortedListings);
        this.recommendationMatrix.factorize(sortedUsers, sortedListings, searchHistoryRecords);
    }

}
