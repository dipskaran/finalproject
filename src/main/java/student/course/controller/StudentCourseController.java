package student.course.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import student.course.controller.model.CourseData;
import student.course.controller.model.PortfolioData;
import student.course.controller.model.RatingData;
import student.course.controller.model.StudentData;
import student.course.service.StudentCourseService;


@RestController
@RequestMapping("/student")
@Slf4j
public class StudentCourseController {
	@Autowired
	private StudentCourseService studentCourseService;
	/**
	 * This method is used to create StudentData
	 * @param studentData
	 * @return StudentData
	 */
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public StudentData insertStudent(
			@RequestBody StudentData studentData) {
	  log.info("Creating Controller {}"+studentData);
	  return studentCourseService.saveStudent(studentData);
	}
	/**
	 * This method returns all the student rows in the table
	 * @return List<StudentData> 
	 */
	@GetMapping
	public List<StudentData> retrieveAllStudent() {
	  log.info("Retrive all students ");
	  return studentCourseService.retrieveAllStudents();
	}
	/**
	 * This method is used to retrieve a student based on id
	 * from the database along with Portfolio, Courses and ratings 
	 * @param studentId
	 * @return StudentData
	 */
	@GetMapping("/{studentId}")
	public StudentData retrieveStudentById(@PathVariable Long studentId) {
		log.info("Retrieving student with ID={}",studentId);
		return studentCourseService.retrieveStudentById(studentId);
	}
	/**
	 * This method is used to update a student row in database
	 * @param studentId
	 * @param studentData
	 * @return StudentData
	 */
	@PutMapping("/{studentId}")
	public StudentData insertStudent(@PathVariable Long studentId,
			@RequestBody StudentData studentData) {
		studentData.setStudentId(studentId);
	  log.info("Updating Controller {}"+studentData);
	  return studentCourseService.saveStudent(studentData);
	}
	/**
	 * This method is used to delete a single student from database
	 * @param studentId
	 * @return Map<String, String>
	 */
	@DeleteMapping("/{studentId}")
	public Map<String, String> deleteStudentById(
			@PathVariable Long studentId){
		log.info("Deleting Student with ID={}",studentId);
		
		studentCourseService.deleteStudentById(studentId);
		
		return Map.of("message","Deletion of Student with ID=" + 
				studentId + " was sucessful.");
		
	}
	/**
	 * This method is used to insert a Portfolio in database
	 * @param studentId
	 * @param portfolioData
	 * @return PortfolioData
	 */
	@PostMapping("/{studentId}/portfolio")
	@ResponseStatus(code=HttpStatus.CREATED)
	public PortfolioData insertPortfolio(@PathVariable Long studentId,
			@RequestBody PortfolioData portfolioData) {
		log.info("Creating Portfolio {} with Student with ID={}",portfolioData,
				studentId);
		return studentCourseService.savePortfolio(studentId,portfolioData);
	}
	/**
	 * This method is used to create course for existing student
	 * @param studentId
	 * @param courseData
	 * @return CourseData
	 */
	@PostMapping("/{studentId}/course")
	@ResponseStatus(code=HttpStatus.CREATED)
	public CourseData insertCourseWithStudentId(@PathVariable Long studentId,
			@RequestBody CourseData courseData) {
		log.info("Creating course {} with student with ID={}",courseData,
				studentId);
		return studentCourseService.saveCourse(studentId, courseData);
	}
	/**
	 * This method is used to update a existing course 
	 * @param courseId
	 * @param courseData
	 * @return CourseData
	 */
	
	@PutMapping("/course/{courseId}")
	public CourseData updateCourse(@PathVariable Long courseId,
			@RequestBody CourseData courseData) {
		courseData.setCourseId(courseId);
	  log.info("Updating Course {}"+courseData);
	  return studentCourseService.saveCourse(courseData);
	}
	
	/**
	 * This method is used to retrieve all courses in the table
	 * @return List<CourseData>
	 */
	@GetMapping("/course")
	public List<CourseData> retrieveAllCourses() {
	  log.info("Retrive all Courses ");
	  return studentCourseService.retrieveAllCourses();
	}
	
	/**
	 * This method is used to retrieve a course for specific id
	 * @param courseId
	 * @return CourseData
	 */
	@GetMapping("/course/{courseId}")
	public CourseData retrieveCourseById(@PathVariable Long courseId) {
		log.info("Retrieving Course with ID={}",courseId);
		return studentCourseService.retrieveCourseById(courseId);
	}
	
	/**
	 * This method is used to delete a course from database
	 * @param courseId
	 * @return Map<String, String>
	 */
	@DeleteMapping("/course/{courseId}")
	public Map<String, String> deleteCourseById(
			@PathVariable Long courseId){
		log.info("Deleting petStore with ID={}",courseId);
		
		studentCourseService.deleteCourseById(courseId);
		
		return Map.of("message","Deletion of Course with ID=" + 
				courseId + " was sucessful.");
		
	}
	
	/**
	 * This method is used to create a Rating in 
	 * database for a existing course
	 * @param courseId
	 * @param ratingData
	 * @return RatingData
	 */
	@PostMapping("/course/{courseId}/rating")
	@ResponseStatus(code=HttpStatus.CREATED)
	public RatingData insertRating(@PathVariable Long courseId,
			@RequestBody RatingData ratingData) {
		log.info("Creating course {} with student with ID={}",ratingData,
				courseId);
		return studentCourseService.saveRating(courseId, ratingData);
	}
}
