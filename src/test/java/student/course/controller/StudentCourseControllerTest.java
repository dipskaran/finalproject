package student.course.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.SqlConfig;

import student.course.StudentCourseApplication;
import student.course.controller.model.StudentData;

@SpringBootTest(webEnvironment = WebEnvironment.NONE,
classes = StudentCourseApplication.class)
@ActiveProfiles("test")
@SqlConfig(encoding ="utf-8")
class StudentCourseControllerTest extends StudentCourseServiceTestSupport{
    /**
     * This method will test saving of Student
     * in database
     */
	@Test
	void testInsertStudent() {
		//Given: A student request
		StudentData request = buildInsertStudent(1);
		StudentData expected = buildInsertStudent(1);
		
		//When: the student is added to the location table
		StudentData actual = insertStudent(request);
		
		//Then: the student returned is what expected
		assertThat(actual).isEqualTo(expected);
		
		//And: there is one row in the student table.
		assertThat(rowsInStudentTable()).isOne();
	}
	 /**
     * This method will test retrieving of Student
     * from database
     */
	@Test
	void testRetrieveStudent() {
		//Given a student
		StudentData location  = insertStudent(buildInsertStudent(2));
		StudentData expected =  buildInsertStudent(2);
		
		//When: the student is retrieved by student ID
		StudentData actual = retrieveStudent(location.getStudentId());
		//Then: the actual student is equal to the expected student.
		assertThat(actual).isEqualTo(expected);
	}
	
	
}
