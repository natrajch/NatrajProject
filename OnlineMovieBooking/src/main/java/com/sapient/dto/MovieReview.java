package com.sapient.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieReview {
	
	private String movieName;
	private List<Review> userReviews;
    private String averageRating;  //average of all user ratings
}
