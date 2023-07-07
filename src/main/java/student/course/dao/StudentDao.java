package student.course.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import student.course.entity.Student;

public interface StudentDao extends JpaRepository<Student, Long> {
	Optional<Student> findByStudentEmail(String studentEmail);
}
