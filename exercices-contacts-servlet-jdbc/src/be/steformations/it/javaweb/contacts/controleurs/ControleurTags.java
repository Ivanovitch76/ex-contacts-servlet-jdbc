package be.steformations.it.javaweb.contacts.controleurs;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.steformations.java_data.contacts.interfaces.beans.Tag;
import be.steformations.pc.java_data.contacts.spring_jdbc.managers.TagManager;

public class ControleurTags extends javax.servlet.http.HttpServlet{

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

		TagManager manager = new TagManager(dataSource);
		List<Tag> tags = manager.getAllTags();		
		
		req.setAttribute("listeTags", tags);
		
		req.getRequestDispatcher("/tags.jsp").forward(req, resp);
	}	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* analyse et validation de la requete */
		String valeur = req.getParameter("valeur");  // nom de l'input dans le formulaire
		String val = req.getParameter("delvalue");  // nom de l'input dans le formulaire
		int delete = -1;  // nom de l'input dans le formulaire
		
		try{
			delete = Integer.parseInt(val);
		} catch(Exception e) {
			e.printStackTrace();
		}

		TagManager manager = new TagManager(dataSource);
		Tag tag = null;
		if (valeur != null){
			tag = manager.createAndSaveTag(valeur);			
		}
		if (delete > 0){
			manager.removeTag(delete);			
		}		


		List<Tag> tags = manager.getAllTags();		
		
		req.setAttribute("listeTags", tags);
		
		resp.setContentType("text/plain");
		
		req.getRequestDispatcher("/tags.jsp").forward(req, resp);
		
	}	
	
}
