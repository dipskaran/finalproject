package student.course.controller;

import java.util.List;
import java.util.function.IntPredicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import student.course.controller.model.StudentData;
import student.course.entity.Student;

public class StudentCourseServiceTestSupport {
	private static final String STUDENT_TABLE = "student";

	@Autowired
	private StudentCourseController studentCourseController;
	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	// @formatter:off
	private StudentData insertStudent1 = new StudentData(
			1L,
			"Mike",
			"Cohen",
			"10",
			"mike.c@gma.com");
	private StudentData insertStudent2 = new StudentData(
			2L,
			"Eric",
			"Bane",
			"10",
			"e.bane@goat.com");
	// @formatter:on
	protected int rowsInStudentTable() {
		// TODO Auto-generated method stub
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, STUDENT_TABLE);
	}

	protected StudentData insertStudent(StudentData studentData) {
		Student student = studentData.toStudent();
		StudentData clone = new StudentData(student);
		clone.setStudentId(null);
		return studentCourseController.insertStudent(clone);
	}

	protected StudentData buildInsertStudent(int which) {
		return which == 1 ? insertStudent1: insertStudent2;
	}
	
	protected StudentData retrieveStudent(Long studentId) {
		
		return studentCourseController.retrieveStudentById(studentId);
	}
	
}
