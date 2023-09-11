package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.dtos.listing.ListingRequest;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.repositories.ListingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final ImageService imageService;

    @Transactional
    public void createListing(ListingRequest listingRequest, MultipartFile[] multipartFiles) {
        var listing = Listing.builder()
                .name(listingRequest.getName())
                .location(listingRequest.getLocation())
                .build();
        listingRepository.save(listing);
        this.imageService.uploadListingImages(listing, multipartFiles);
    }

    public void deleteListing(Integer Id) {
        listingRepository.removeListingById(Id);
    }
}
