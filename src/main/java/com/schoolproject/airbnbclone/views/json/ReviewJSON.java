package com.schoolproject.airbnbclone.views.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.schoolproject.airbnbclone.models.Review;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewJSON implements Serializable {

    @JsonProperty("Rating")
    private final Integer rating;

    @JsonProperty("Comment")
    private final String comment;

    public ReviewJSON(Review review) {
        this.rating = review.getRating();
        this.comment = review.getComment();
    }

}
