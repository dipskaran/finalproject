package student.course.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import student.course.entity.Course;
import student.course.entity.Rating;
/**
 * This is the data transfer object for 
 * course entity
 *
 */
@Data
@NoArgsConstructor
public class CourseData {
	private Long courseId;
	private String courseName;
	private String courseDescription;
	private Set<RatingData> ratings= new HashSet<>();
	
	public CourseData(Course course) {
		courseId = course.getCourseId();
		courseName = course.getCourseName();
		courseDescription = course.getCourseDescription();
		
		for(Rating rating:course.getRatings()) {
			RatingData ratingData = new RatingData(rating);
			ratings.add(ratingData);
		}
	}
	
	public CourseData(Long courseId,String courseName,
			String courseDescription) {
		this.courseId=courseId;
		this.courseName=courseName;
		this.courseDescription=courseDescription;
	}
	
	public Course toCourse() {
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName(courseName);
		course.setCourseDescription(courseDescription);
		for (RatingData ratingData: ratings) {
			course.getRatings().add(ratingData.toRating());
		}
		return course;
	}
}
