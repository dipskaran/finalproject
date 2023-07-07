package student.course.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import student.course.entity.Rating;
/**
 * This is the data transfer object for 
 * Rating entity
 *
 */
@Data
@NoArgsConstructor
public class RatingData {
	
	private Long ratingId;
	private String ratingScale;
	private String ratingDescription;
	
	public RatingData(Rating rating) {
		ratingId = rating.getRatingId();
		ratingScale = rating.getRatingScale();
		ratingDescription = rating.getRatingDescription();
	}
	
	public RatingData(Long ratingId, String ratingScale,
			String ratingDescription) {
		this.ratingId=ratingId;
		this.ratingScale=ratingScale;
		this.ratingDescription=ratingDescription;
	}
	
	public Rating toRating() {
		Rating rating = new Rating();
		rating.setRatingId(ratingId);
		rating.setRatingScale(ratingScale);
		rating.setRatingDescription(ratingDescription);
		
		return rating;
	}
}
