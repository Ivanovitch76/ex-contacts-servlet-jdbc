package be.steformations.it.javaweb.contacts.tests;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.steformations.java_data.contacts.interfaces.beans.Country;
import be.steformations.pc.java_data.contacts.spring_jdbc.managers.CountryManager;

public class TestDaoSpringJdbc extends HttpServlet{

	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			//chargement du driver jdbc pour qu'il s'enregistre auprès du driver manager		
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.sql.DataSource dataSource = new org.springframework.jdbc.datasource.DriverManagerDataSource(
				"jdbc:postgresql://localhost/contacts","postgres", "postgres");
		CountryManager manager = new CountryManager(dataSource);
		Country usa = manager.getCountryByAbbreviation("US");
		
		resp.setContentType("text/plain");
		resp.getWriter().write(usa.getName());
	}

	
	
}
