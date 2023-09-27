package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.models.Availability;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.repositories.AvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    public void populateAvailability(Listing listing, LocalDate startDate, LocalDate endDate) {
        LocalDate currentDate = startDate;

        System.out.println(startDate);

        while(!currentDate.isAfter(endDate)) {
            var availability = Availability.builder()
                    .listing(listing)
                    .date(currentDate)
                    .available(true)
                    .build();
            availabilityRepository.save(availability);

            currentDate = currentDate.plusDays(1);
        }
    }

}
