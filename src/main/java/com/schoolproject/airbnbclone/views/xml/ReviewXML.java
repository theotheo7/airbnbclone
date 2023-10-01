package com.schoolproject.airbnbclone.views.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.schoolproject.airbnbclone.models.Review;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewXML implements Serializable {

    @JacksonXmlProperty(localName = "Rating")
    private final Integer rating;

    @JacksonXmlProperty(localName = "Comment")
    private final String comment;

    public ReviewXML(Review review) {
        this.rating = review.getRating();
        this.comment = review.getComment();
    }

}
