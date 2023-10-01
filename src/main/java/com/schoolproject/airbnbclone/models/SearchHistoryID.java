package com.schoolproject.airbnbclone.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SearchHistoryID implements Serializable {

    @Serial
    private static final long serialVersionUID = -3891283133955115014L;

    @Column(name = "listing_id", nullable = false)
    private Long listingID;

    @Column(name = "user_id", nullable = false)
    private Long userID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SearchHistoryID entity = (SearchHistoryID) o;
        return Objects.equals(this.listingID, entity.listingID) && Objects.equals(this.userID, entity.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listingID, userID);
    }

}
