package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

public interface CustomListingRepository {

    Page<Listing> searchListings(Specification<Listing> specification, PageRequest pageRequest);

}
