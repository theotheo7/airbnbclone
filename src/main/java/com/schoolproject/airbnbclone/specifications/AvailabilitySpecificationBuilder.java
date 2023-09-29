package com.schoolproject.airbnbclone.specifications;

import com.schoolproject.airbnbclone.models.Availability;
import com.schoolproject.airbnbclone.models.Listing;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AvailabilitySpecificationBuilder {

    public static Predicate isAvailable(Join<Listing, Availability> availabilityJoin, CriteriaBuilder criteriaBuilder, LocalDate fromDate, LocalDate toDate) {
        List<Predicate> availabilityPredicates = new ArrayList<>();

        availabilityPredicates.add(criteriaBuilder.between(availabilityJoin.get("date"), fromDate, toDate));

        availabilityPredicates.add(criteriaBuilder.isTrue(availabilityJoin.get("available")));

        return criteriaBuilder.and(availabilityPredicates.toArray(new Predicate[0]));
    }

}
