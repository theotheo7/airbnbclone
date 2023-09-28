package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.dtos.listing.request.ListingRequest;
import com.schoolproject.airbnbclone.dtos.listing.response.ListingBasicDetails;
import com.schoolproject.airbnbclone.dtos.listing.response.ListingCompleteDetails;
import com.schoolproject.airbnbclone.exceptions.ListingException;
import com.schoolproject.airbnbclone.exceptions.UserException;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.models.User;
import com.schoolproject.airbnbclone.repositories.ListingRepository;
import com.schoolproject.airbnbclone.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
                .maxPeople(listingRequest.getMaxPeople())
                .price(listingRequest.getPrice())
                .extraPeople(listingRequest.getExtraPeople())
                .type(listingRequest.getType())
                .beds(listingRequest.getBeds())
                .baths(listingRequest.getBaths())
                .meters(listingRequest.getMeters())
                .living(listingRequest.getLiving())
                .party(listingRequest.getParty())
                .pets(listingRequest.getPets())
                .summary(listingRequest.getSummary())
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

    public ListingCompleteDetails getListing(String name) {
        Optional<Listing> optionalListing = this.listingRepository.findByName(name);

        if (optionalListing.isPresent()) {
            return new ListingCompleteDetails(optionalListing.get());
        } else {
            throw new UserException(name, ListingException.LISTING_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public void updateListing(Authentication authentication, ListingRequest listingRequest, MultipartFile[] multipartFiles) {
        Optional<Listing> optionalListing = listingRepository.findByName(listingRequest.getName());

        if (optionalListing.isEmpty()) {
            throw new ListingException(ListingException.LISTING_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        Listing listing = optionalListing.get();

        listing.setLocation(listingRequest.getLocation());
        listing.setName(listingRequest.getName());
        listing.setMaxPeople(listingRequest.getMaxPeople());
        listing.setPrice(listingRequest.getPrice());
        listing.setExtraPeople(listingRequest.getExtraPeople());
        listing.setType(listingRequest.getType());
        listing.setBeds(listingRequest.getBeds());
        listing.setBaths(listingRequest.getBaths());
        listing.setMeters(listingRequest.getMeters());
        listing.setLiving(listingRequest.getLiving());
        listing.setParty(listingRequest.getParty());
        listing.setPets(listingRequest.getPets());
        listing.setSummary(listingRequest.getSummary());

        imageService.updateListingImages(listing, multipartFiles);
    }

    public void deleteListing(Integer Id) {
        listingRepository.removeListingById(Id);
    }
}
