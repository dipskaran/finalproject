package student.course.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ratingId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "course_id")
	private Course course;
	
	private String ratingScale;
	private String ratingDescription;
}
