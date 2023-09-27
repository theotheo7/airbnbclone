package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.dtos.listing.request.ListingRequest;
import com.schoolproject.airbnbclone.dtos.listing.response.ListingBasicDetails;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.models.User;
import com.schoolproject.airbnbclone.repositories.ListingRepository;
import com.schoolproject.airbnbclone.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final AvailabilityService availabilityService;

    @Transactional
    public void createListing(Authentication authentication, ListingRequest listingRequest, MultipartFile[] multipartFiles) {
        Optional<User> host = userRepository.findByUsername(authentication.getName());
        var listing = Listing.builder()
                .name(listingRequest.getName())
                .location(listingRequest.getLocation())
                .host(host.orElseGet(User::new))
                .build();
        listingRepository.save(listing);
        this.imageService.uploadListingImages(listing, multipartFiles);
        this.availabilityService.populateAvailability(listing, listingRequest.getStartDate(), listingRequest.getEndDate());
    }

    public List<ListingBasicDetails> getListings(Authentication authentication, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Listing> listings = listingRepository.findAllByHostName(pageable, authentication.getName());
        List<Listing> listingList = listings.getContent();
        return listingList.stream()
                .map(ListingBasicDetails::new)
                .collect(Collectors.toList());
    }

    public void deleteListing(Integer Id) {
        listingRepository.removeListingById(Id);
    }
}
