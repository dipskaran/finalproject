package student.course.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import student.course.entity.Rating;

public interface RatingDao extends JpaRepository<Rating, Long> {

}
