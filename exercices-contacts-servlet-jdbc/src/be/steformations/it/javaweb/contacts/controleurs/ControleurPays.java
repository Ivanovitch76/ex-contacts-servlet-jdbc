package be.steformations.it.javaweb.contacts.controleurs;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.steformations.java_data.contacts.interfaces.beans.Country;
import be.steformations.java_data.contacts.interfaces.beans.Tag;
import be.steformations.pc.java_data.contacts.spring_jdbc.managers.CountryManager;
import be.steformations.pc.java_data.contacts.spring_jdbc.managers.TagManager;

public class ControleurPays extends javax.servlet.http.HttpServlet {
	
	private DataSource dataSource;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.dataSource = new org.springframework.jdbc.datasource.DriverManagerDataSource(
		"jdbc:postgresql://localhost/contacts","postgres", "postgres");	
		try {
			//chargement du driver jdbc pour qu'il s'enregistre auprès du driver manager		
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		System.out.println("ControleurPays.doGet");
		CountryManager manager = new CountryManager(dataSource);
		List<Country> pays = manager.getAllCountries();		
		
		req.setAttribute("listePays", pays);
		
		req.getRequestDispatcher("/pays.jsp").forward(req, resp);
	}	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* analyse et validation de la requete */
		String abrev = req.getParameter("abrev");  // nom de l'input dans le formulaire
		String nom = req.getParameter("nom");  // nom de l'input dans le formulaire
		
		
		System.out.println("ControleurPays.doPost: " + nom + "  " + abrev);

		CountryManager manager = new CountryManager(dataSource);
		Country country = null;
		if (nom != null && abrev != null){
			country = manager.createAndSaveCountry(abrev, nom);			
		}	


		List<Country> pays = manager.getAllCountries();		
		
		req.setAttribute("listePays", pays);
		
		resp.setContentType("text/plain");
		
		req.getRequestDispatcher("/pays.jsp").forward(req, resp);
		
	}		
	
}
