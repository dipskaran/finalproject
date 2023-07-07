package student.course.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Portfolio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long portfolioId;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id")
	private Student student;
	
	private String portfolioName;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date portfolioStartdate;
	
}
