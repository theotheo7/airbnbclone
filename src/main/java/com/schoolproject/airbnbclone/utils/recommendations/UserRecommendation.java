package com.schoolproject.airbnbclone.utils.recommendations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserRecommendation implements Comparable<UserRecommendation> {

    private Long listingID;
    private Double listingValue;


    @Override
    public int compareTo(UserRecommendation userRecommendation) {
        return Double.compare(this.listingValue, userRecommendation.listingValue);
    }
}
