package com.schoolproject.airbnbclone.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SearchHistory {

    @EmbeddedId
    private SearchHistoryID id;

    @MapsId("listingID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "listing_id", referencedColumnName = "id", nullable = false)
    private Listing listing;

    @MapsId("userID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long interactions;

    public SearchHistory(SearchHistoryID id, Listing listing, User user) {
        this.id = id;
        this.listing = listing;
        this.user = user;
        this.interactions = 1L;
    }
}
