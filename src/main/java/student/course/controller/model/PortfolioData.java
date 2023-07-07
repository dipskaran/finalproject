package student.course.controller.model;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import student.course.entity.Portfolio;
/**
 * This is the data transfer object for 
 * portfolio entity
 *
 */
@Data
@NoArgsConstructor
public class PortfolioData {
	private Long portfolioId;
	private String portfolioName;
	private Date portfolioStartdate;
	
	public PortfolioData(Portfolio portfolio) {
		portfolioId = portfolio.getPortfolioId();
		portfolioName = portfolio.getPortfolioName();
		portfolioStartdate = portfolio.getPortfolioStartdate();
	}
	
	public PortfolioData(Long portfolioId,String portfolioName,
			Date portfolioStartdate) {
		this.portfolioId=portfolioId;
		this.portfolioName=portfolioName;
		this.portfolioStartdate=portfolioStartdate;
	}
	
	public Portfolio toPortfolio() {
		Portfolio portfolio = new Portfolio();
		portfolio.setPortfolioId(portfolioId);
		portfolio.setPortfolioName(portfolioName);
		portfolio.setPortfolioStartdate(portfolioStartdate);
		
		return portfolio;
	}

}
