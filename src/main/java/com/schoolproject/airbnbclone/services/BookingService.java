package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.exceptions.ListingException;
import com.schoolproject.airbnbclone.exceptions.UserException;
import com.schoolproject.airbnbclone.models.Booking;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.models.User;
import com.schoolproject.airbnbclone.repositories.BookingRepository;
import com.schoolproject.airbnbclone.repositories.ListingRepository;
import com.schoolproject.airbnbclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookingService {

    private final ListingRepository listingRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    private final AvailabilityService availabilityService;

    public void book(Authentication authentication, Long id, LocalDate fromDate, LocalDate toDate) {
        Optional<Listing> optionalListing = this.listingRepository.findById(id);

        if (optionalListing.isEmpty()) {
            throw new ListingException(ListingException.LISTING_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        Optional<User> optionalUser = this.userRepository.findByUsername(authentication.getName());

        if (optionalUser.isEmpty()) {
            throw new UserException(UserException.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        Listing listing = optionalListing.get();
        User user = optionalUser.get();

        Booking booking = Booking.builder()
                .listing(listing)
                .guest(user)
                .fromDate(fromDate)
                .toDate(toDate)
                .bookingDate(LocalDate.now())
                .build();

        this.bookingRepository.save(booking);
        this.availabilityService.updateAvailability(listing, fromDate, toDate);
    }

}
