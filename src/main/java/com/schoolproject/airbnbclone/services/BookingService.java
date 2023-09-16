package com.schoolproject.airbnbclone.services;

import com.schoolproject.airbnbclone.repositories.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;

}
