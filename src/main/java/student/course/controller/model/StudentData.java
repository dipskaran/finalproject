package student.course.controller.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import student.course.entity.Course;
import student.course.entity.Student;
/**
 * This is the data transfer object for 
 * Student entity
 *
 */
@Data
@NoArgsConstructor
public class StudentData {
	private Long studentId;
	private String studentFirstName;
	private String studentLastName;
	private String studentGrade;
	private String studentEmail;
	private PortfolioData portfolioData;
	private Set<CourseData> courses=new HashSet<>();
	
	public StudentData(Student student) {
		studentId = student.getStudentId();
		studentFirstName = student.getStudentFirstName();
		studentLastName = student.getStudentLastName();
		studentGrade = student.getStudentGrade();
		studentEmail = student.getStudentEmail();
		if(Objects.nonNull(student.getPortfolio()))
			portfolioData = new PortfolioData(student.getPortfolio());
		for(Course course:student.getCourses()) {
			CourseData courseData = new CourseData(course);
			courses.add(courseData);
		}
		
	}
	
	public StudentData(Long studentId, String studentFirstName,
			String studentLastName, String studentGrade, String studentEmail) {
		this.studentId=studentId;
		this.studentFirstName=studentFirstName;
		this.studentLastName=studentLastName;
		this.studentGrade=studentGrade;
		this.studentEmail=studentEmail;
		
	}
	
	public Student toStudent() {
		Student student = new Student();
		student.setStudentId(studentId);
		student.setStudentFirstName(studentFirstName);
		student.setStudentLastName(studentLastName);
		student.setStudentGrade(studentGrade);
		student.setStudentEmail(studentEmail);
		if(Objects.nonNull(portfolioData))
		student.setPortfolio(portfolioData.toPortfolio());
		for(CourseData courseData:courses) {
			student.getCourses().add(courseData.toCourse());
		}
		return student;
	}
}
