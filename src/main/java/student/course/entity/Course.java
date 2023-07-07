package student.course.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseId;
	private String courseName;
	private String courseDescription;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy="course", cascade = CascadeType.ALL,
		orphanRemoval = true)
	private Set<Rating> ratings= new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy= "courses",cascade = CascadeType.PERSIST)
	private Set<Student> students = new HashSet<>();
	
}
