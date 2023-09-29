package com.schoolproject.airbnbclone.specifications;

import com.schoolproject.airbnbclone.models.Availability;
import com.schoolproject.airbnbclone.models.Listing;
import com.schoolproject.airbnbclone.models.Location;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListingSpecificationBuilder {

    public static Predicate isType(Root<Listing> root, CriteriaBuilder criteriaBuilder, String type) {
        return criteriaBuilder.like(criteriaBuilder.lower(root.get("type")), "%" + type.toLowerCase() + "%");
    }

    public static Predicate hasMaxPrice(Root<Listing> root, CriteriaBuilder criteriaBuilder, BigDecimal maxPrice) {
        return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Predicate hasWifi(Root<Listing> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isTrue(root.get("wifi"));
    }

    public static Predicate hasAC(Root<Listing> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isTrue(root.get("ac"));
    }

    public static Predicate hasHeat(Root<Listing> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isTrue(root.get("heat"));
    }

    public static Predicate hasKitchen(Root<Listing> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isTrue(root.get("kitchen"));
    }

    public static Predicate hasTV(Root<Listing> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isTrue(root.get("tv"));
    }

    public static Predicate hasParking(Root<Listing> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isTrue(root.get("parking"));
    }

    public static Predicate hasElevator(Root<Listing> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isTrue(root.get("elevator"));
    }

    public static Specification<Listing> filterListings(
            String city,
            LocalDate fromDate,
            LocalDate toDate,
            Integer people,
            String type,
            BigDecimal maxPrice,
            Boolean wifi,
            Boolean ac,
            Boolean heat,
            Boolean kitchen,
            Boolean tv,
            Boolean parking,
            Boolean elevator
    ) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> listingPredicates = new ArrayList<>();

            Join<Listing, Location> locationJoin = root.join("location", JoinType.INNER);
            locationJoin.on(criteriaBuilder.like(criteriaBuilder.lower(locationJoin.get("city")), "%" + city.toLowerCase() + "%"));

            Join<Listing, Availability> availabilityJoin = root.join("availabilities", JoinType.INNER);
            availabilityJoin.on(AvailabilitySpecificationBuilder.isAvailable(availabilityJoin, criteriaBuilder, fromDate, toDate));

            listingPredicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("maxPeople"), people));

            if (type != null) {
                listingPredicates.add(ListingSpecificationBuilder.isType(root, criteriaBuilder, type));
            }

            if (maxPrice != null) {
                listingPredicates.add(ListingSpecificationBuilder.hasMaxPrice(root, criteriaBuilder, maxPrice));
            }

            if (wifi != null) {
                listingPredicates.add(ListingSpecificationBuilder.hasWifi(root, criteriaBuilder));
            }

            if (ac != null) {
                listingPredicates.add(ListingSpecificationBuilder.hasAC(root, criteriaBuilder));
            }

            if (heat != null) {
                listingPredicates.add(ListingSpecificationBuilder.hasHeat(root, criteriaBuilder));
            }

            if (kitchen != null) {
                listingPredicates.add(ListingSpecificationBuilder.hasKitchen(root, criteriaBuilder));
            }

            if (tv != null) {
                listingPredicates.add(ListingSpecificationBuilder.hasTV(root, criteriaBuilder));
            }

            if (parking != null) {
                listingPredicates.add(ListingSpecificationBuilder.hasParking(root, criteriaBuilder));
            }

            if (elevator != null) {
                listingPredicates.add(ListingSpecificationBuilder.hasElevator(root, criteriaBuilder));
            }

            return criteriaBuilder.and(listingPredicates.toArray(new Predicate[0]));
        };
    }

}
