package student.course.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import student.course.entity.Portfolio;

public interface PortfolioDao extends JpaRepository<Portfolio, Long> {

}
