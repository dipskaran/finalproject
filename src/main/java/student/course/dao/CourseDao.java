package student.course.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import student.course.entity.Course;

public interface CourseDao extends JpaRepository<Course, Long> {

}
