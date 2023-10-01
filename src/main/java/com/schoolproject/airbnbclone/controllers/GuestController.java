package com.schoolproject.airbnbclone.controllers;

import com.schoolproject.airbnbclone.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
public class GuestController {

    private final BookingService bookingService;

    @RequestMapping(value = "/book/{id}", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> book(
            Authentication authentication,
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "from") String from,
            @RequestParam(name = "to") String to
    ) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(from, dtf);
        LocalDate toDate = LocalDate.parse(to, dtf);
        this.bookingService.book(authentication, id, fromDate, toDate);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
