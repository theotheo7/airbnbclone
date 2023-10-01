package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.Listing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class CustomListingRepositoryImpl implements CustomListingRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<Listing> searchListings(Specification<Listing> specification, PageRequest pageRequest) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Listing> criteriaQuery = criteriaBuilder.createQuery(Listing.class);
        Root<Listing> listingRoot = criteriaQuery.from(Listing.class);
        listingRoot.fetch("location", JoinType.INNER);
        listingRoot.fetch("images", JoinType.INNER);
        listingRoot.fetch("host", JoinType.INNER);
        criteriaQuery.select(listingRoot);
        criteriaQuery.distinct(true);
        criteriaQuery.orderBy(QueryUtils.toOrders(pageRequest.getSort(), listingRoot, criteriaBuilder));
        criteriaQuery.where(specification.toPredicate(listingRoot, criteriaQuery, criteriaBuilder));
        List<Listing> listings = entityManager.createQuery(criteriaQuery).getResultList();
        return new PageImpl<>(listings, pageRequest, listings.size());

    }

    @Override
    public List<Listing> export(Integer maxResults) {
        TypedQuery<Listing> typedQuery = entityManager.createQuery("SELECT DISTINCT l " +
                "FROM Listing l " +
                "LEFT JOIN FETCH l.location AS location " +
                "LEFT JOIN FETCH l.host AS host " +
                "LEFT JOIN FETCH host.hostReviews AS hostReviews " +
                "ORDER BY l.id ASC", Listing.class).setMaxResults(maxResults);
        List<Listing> listings = typedQuery.getResultList();

        typedQuery = entityManager.createQuery("SELECT DISTINCT l " +
                "FROM Listing l " +
                "LEFT JOIN FETCH l.bookings AS bookings " +
                "LEFT JOIN FETCH bookings.guest AS guest " +
                "WHERE l in :listings", Listing.class)
                .setParameter("listings", listings);

        listings = typedQuery.getResultList();

        typedQuery = entityManager.createQuery("SELECT DISTINCT l " +
                        "FROM Listing l " +
                        "LEFT JOIN FETCH l.reviews AS reviews " +
                        "WHERE l in :listings", Listing.class)
                .setParameter("listings", listings);

        listings = typedQuery.getResultList();
        return listings;
    }

}
