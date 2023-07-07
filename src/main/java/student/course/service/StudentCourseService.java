package student.course.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import student.course.controller.model.CourseData;
import student.course.controller.model.PortfolioData;
import student.course.controller.model.RatingData;
import student.course.controller.model.StudentData;
import student.course.dao.CourseDao;
import student.course.dao.PortfolioDao;
import student.course.dao.RatingDao;
import student.course.dao.StudentDao;
import student.course.entity.Course;
import student.course.entity.Portfolio;
import student.course.entity.Rating;
import student.course.entity.Student;

@Service
public class StudentCourseService {
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private PortfolioDao portfolioDao;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private RatingDao ratingDao;
	
	/**
	 * This method makes DAO call to save student in database
	 * @param studentData
	 * @return StudentData
	 */
	@Transactional(readOnly = false)
	public StudentData saveStudent(StudentData studentData) {
		Long studentId = studentData.getStudentId();
		Student student = findOrCreateStudent(studentId,studentData.getStudentEmail());

		setFieldsInStudent(student, studentData);
		return new StudentData(studentDao.save(student));
	}
	/**
	 * This method copies the data from StudentData to Student entity
	 * @param student
	 * @param studentData
	 */
	private void setFieldsInStudent(Student student, StudentData studentData) {
		student.setStudentFirstName(studentData.getStudentFirstName());
		student.setStudentLastName(studentData.getStudentLastName());
		student.setStudentEmail(studentData.getStudentEmail());
		student.setStudentGrade(studentData.getStudentGrade());
		
	}
	/**
	 * This method will create a new student object or return an existing
	 * student from the database
	 * @param studentId
	 * @param studentEmail
	 * @return Student
	 */
	private Student findOrCreateStudent(Long studentId,String studentEmail) {
			Student student;

			if (Objects.isNull(studentId)) {

				Optional<Student> opStudent = studentDao.findByStudentEmail(studentEmail);
				if (opStudent.isPresent()) {
					throw new DuplicateKeyException("Student with email " + studentEmail + " already exists.");
				}
				student = new Student();
			} else {
				student = findStudentById(studentId);
			}

			return student;
		
	}
	/**
	 * This method finds Student based on the id from database
	 * @param studentId
	 * @return Student
	 */
	private Student findStudentById(Long studentId) {
		return studentDao.findById(studentId).orElseThrow(
				() -> new NoSuchElementException("Student with ID=" + studentId + " was not found."));
	}
	/**
	 * This method is used to save Portfolio in database
	 * @param studentId
	 * @param portfolioData
	 * @return PortfolioData
	 */
	@Transactional(readOnly = false)
	public PortfolioData savePortfolio(Long studentId, PortfolioData portfolioData) {
		Student student = findStudentById(studentId);
		Long portfolioId = portfolioData.getPortfolioId();
		Portfolio portfolio = findOrCreatePortfolio(portfolioId, studentId);
		copyPortfolioFields(portfolio, portfolioData);
		portfolio.setStudent(student);
		student.setPortfolio(portfolio);
		Portfolio dbPortfolio = portfolioDao.save(portfolio);
		return new PortfolioData(dbPortfolio);
	}
	/**
	 * This method copies data from PortfolioData to Portfolio
	 * @param portfolio
	 * @param portfolioData
	 */
	private void copyPortfolioFields(Portfolio portfolio, PortfolioData portfolioData) {
		portfolio.setPortfolioName(portfolioData.getPortfolioName());
		portfolio.setPortfolioStartdate(portfolioData.getPortfolioStartdate());	
	}
	/**
	 * This method will create a new Portfolio object of will find an existing
	 * one from database
	 * @param portfolioId
	 * @param studentId
	 * @return Portfolio
	 */
	private Portfolio findOrCreatePortfolio(Long portfolioId, Long studentId) {
		Portfolio portfolio;
		if (Objects.isNull(portfolioId)) {
			portfolio = new Portfolio();
		} else {
			portfolio = findPortfolioById(portfolioId,studentId);
		}
		return portfolio;
	}
	/**
	 * This method will find Portfolio by the ID from database
	 * @param portfolioId
	 * @param studentId
	 * @return Portfolio
	 */
	private Portfolio findPortfolioById(Long portfolioId, Long studentId) {
		Portfolio portfolio=portfolioDao.findById(portfolioId).orElseThrow(
				() -> new NoSuchElementException("Portfolio with ID=" + portfolioId + " was not found."));
		
		if(portfolio.getStudent().getStudentId()==studentId) {
			return portfolio;
		}else {
			throw new IllegalArgumentException("The student "+studentId+ 
					" has another portfolio "+portfolioId);
		}
	}
	/**
	 * This method will save a Course to database
	 * @param studentId
	 * @param courseData
	 * @return CourseData
	 */
	@Transactional(readOnly = false)
	public CourseData saveCourse(Long studentId, CourseData courseData) {
		Student student = findStudentById(studentId);
		Long courseId = courseData.getCourseId();
		Course course = findOrCreateCourse(studentId, courseId);
		copyCourseFields(course, courseData);
		course.getStudents().add(student);
		student.getCourses().add(course);
		Course dbCourse = courseDao.save(course);
		return new CourseData(dbCourse);
	}
	/**
	 * This method is used to copy data from CourseData to Course
	 * @param course
	 * @param courseData
	 */
	private void copyCourseFields(Course course, CourseData courseData) {
		course.setCourseName(courseData.getCourseName());
		course.setCourseDescription(courseData.getCourseDescription());		
	}
	/**
	 * This method will create a new Course object or find an existing
	 * one from database
	 * @param studentId
	 * @param courseId
	 * @return Course
	 */
	private Course findOrCreateCourse(Long studentId, Long courseId) {
		Course course;
		if (Objects.isNull(courseId)) {
			course = new Course();
		} else {
			course = findCourseById(studentId,courseId);
		}
		return course;
	}
	/**
	 * This method will find an Course based on an id from database
	 * @param studentId
	 * @param courseId
	 * @return Course
	 */
	private Course findCourseById(Long studentId, Long courseId) {
		Course course=courseDao.findById(courseId).orElseThrow(
				() -> new NoSuchElementException("Course with ID=" + courseId + " was not found."));
		Set<Student> students = course.getStudents();
		boolean courseFlg=false;
		for(Student student : students) {
			if(student.getStudentId() == studentId){
				courseFlg=true;
			}
		}
		
		if(courseFlg) {
			return course;
		}else {
			throw new IllegalArgumentException("There course "+courseId+ 
					" does not available for this "+studentId);
		}
	}
	/**
	 * This method is used save Rating to database 
	 * @param courseId
	 * @param ratingData
	 * @return RatingData
	 */
	@Transactional(readOnly = false)
	public RatingData saveRating(Long courseId, RatingData ratingData) {
		Course course=courseDao.findById(courseId).orElseThrow(
				() -> new NoSuchElementException("Course with ID=" + courseId + " was not found."));
		Long ratingId = ratingData.getRatingId();
		Rating rating = findOrCreateRating(courseId,ratingId);
		copyRatingFields(rating, ratingData);
		rating.setCourse(course);
		course.getRatings().add(rating);
		Rating dbRating = ratingDao.save(rating);
		return new RatingData(dbRating);
		
	}
	/**
	 * This method will copy data from RatingData to Rating
	 * @param rating
	 * @param ratingData
	 */
	private void copyRatingFields(Rating rating, RatingData ratingData) {
		rating.setRatingDescription(ratingData.getRatingDescription());
		rating.setRatingDescription(ratingData.getRatingDescription());
		rating.setRatingScale(ratingData.getRatingScale());
		
	}
	/**
	 * This method will create a new Rating object or find an existing 
	 * Rating from database
	 * @param courseId
	 * @param ratingId
	 * @return Rating
	 */
	private Rating findOrCreateRating(Long courseId, Long ratingId) {
		Rating rating;
		if (Objects.isNull(ratingId)) {
			rating = new Rating();
		} else {
			rating = findRatingById(courseId,ratingId);
		}
		return rating;
	}
	/**
	 * This method will find a specific Rating from database
	 * @param courseId
	 * @param ratingId
	 * @return Rating
	 */
	private Rating findRatingById(Long courseId, Long ratingId) {
		Rating rating=ratingDao.findById(ratingId).orElseThrow(
				() -> new NoSuchElementException("Rating with ID=" + ratingId + " was not found."));
		
		if(rating.getCourse().getCourseId() == courseId) {
			return rating;
		}else {
			throw new IllegalArgumentException("The Rating "+ratingId+ 
					" has another course "+ratingId);
		}
	}
	/**
	 * This method will find all Students from database
	 * @return List<StudentData>
	 */
	@Transactional(readOnly = true)
	public List<StudentData> retrieveAllStudents() {
		List<Student> students = studentDao.findAll();
		List<StudentData> response = new LinkedList<>();
		
		for(Student student : students) {
			StudentData sdt = new StudentData(student);
			sdt.getCourses().clear();
			response.add(sdt);
		}
		
		return response;
	}
	/**
	 * This method will find specific Student from database
	 * @param studentId
	 * @return StudentData
	 */
	@Transactional(readOnly = true)
	public StudentData retrieveStudentById(Long studentId) {
		Student student = findStudentById(studentId);
		return new StudentData(student);
	}
	/**
	 *This method will retrieve all courses from database
	 * @return List<CourseData>
	 */
	@Transactional(readOnly = true)
	public List<CourseData> retrieveAllCourses() {
		List<Course> courses = courseDao.findAll();
		List<CourseData> response = new LinkedList<>();
		for(Course course : courses) {
			CourseData cdt = new CourseData(course);
			cdt.getRatings().clear();
			response.add(cdt);
		}
		return response;
	}
	/**
	 * This method will retrieve specific Course from database
	 * @param courseId
	 * @return CourseData
	 */
	@Transactional(readOnly = true)
	public CourseData retrieveCourseById(Long courseId) {
		Course course = findCourseById(courseId);
		return new CourseData(course);
	}
	/**
	 * This method will find a specific Course from database
	 * @param courseId
	 * @return Course
	 */
	private Course findCourseById(Long courseId) {
		Course course=courseDao.findById(courseId).orElseThrow(
				() -> new NoSuchElementException("Course with ID=" + courseId + " was not found."));
		return course;
	}
	/**
	 * This method will save  a Course to database
	 * @param courseData
	 * @return CourseData
	 */
	@Transactional(readOnly = false)
	public CourseData saveCourse(CourseData courseData) {
		Long courseId = courseData.getCourseId();
		Course course = findOrCreateCourse(courseId);

		copyCourseFields(course, courseData);
		return new CourseData(courseDao.save(course));
	}
	/**
	 * This method will create a new Course or find an existing Course
	 * from the database
	 * @param courseId
	 * @return Course
	 */
	private Course findOrCreateCourse(Long courseId) {
		Course course;
		if (Objects.isNull(courseId)) {
			course = new Course();
		} else {
			course = findCourseById(courseId);
		}
		return course;
	}
	/**
	 * This method will be used to delete a specific Course from
	 * database
	 * @param courseId
	 */
	@Transactional(readOnly = false)
	public void deleteCourseById(Long courseId) {
		Course course = findCourseById(courseId);
		courseDao.delete(course);
		
	}
	/**
	 * This method is used to delete a specific Student from 
	 * database
	 * @param studentId
	 */
	@Transactional(readOnly = false)
	public void deleteStudentById(Long studentId) {
		Student student=findStudentById(studentId);
		studentDao.delete(student);
	}

}
