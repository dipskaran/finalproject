package student.course.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long studentId;
	private String studentFirstName;
	private String studentLastName;
	private String studentGrade;
	private String studentEmail;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToOne(mappedBy="student", cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Portfolio portfolio;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "student_course",
		joinColumns =@JoinColumn(name ="student_id"),
		inverseJoinColumns = @JoinColumn(name = "course_id"))
	private Set<Course> courses=new HashSet<>();
	
}
