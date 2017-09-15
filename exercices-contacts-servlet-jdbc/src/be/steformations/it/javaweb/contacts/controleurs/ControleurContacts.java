package be.steformations.it.javaweb.contacts.controleurs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.steformations.it.javaweb.contacts.modele.TagsParContact;
import be.steformations.java_data.contacts.interfaces.beans.Contact;
import be.steformations.java_data.contacts.interfaces.beans.Country;
import be.steformations.java_data.contacts.interfaces.beans.Tag;
import be.steformations.pc.java_data.contacts.spring_jdbc.managers.ContactsManager;
import be.steformations.pc.java_data.contacts.spring_jdbc.managers.CountryManager;
import be.steformations.pc.java_data.contacts.spring_jdbc.managers.TagManager;

public class ControleurContacts extends javax.servlet.http.HttpServlet {
	
	private DataSource dataSource;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.dataSource = new org.springframework.jdbc.datasource.SingleConnectionDataSource(
	//SingleConnectionDataSource car dans le ContactsManager, l'opération createAndSaveContact comporte un try-catch
	// avec commit et rollback qui fait qu'il vaut mieux privilégier une connexion unique (SingleConnectionDataSource) par rapport à plusieurs connexions 
	// (DriverManagerDataSource) car lors d'un create contacts, on perd l'affichage de la liste des contacts sinon.			
		"jdbc:postgresql://localhost/contacts","postgres", "postgres", false);	
		try {
			//chargement du driver jdbc pour qu'il s'enregistre auprès du driver manager		
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/* faire travailler le modèle */
		CountryManager ctman = new CountryManager(dataSource);
		TagManager tagman = new TagManager(dataSource);
		ContactsManager manager = new ContactsManager(dataSource, ctman, tagman);		
						
		String val = req.getParameter("delete");  // nom de l'input dans le formulaire
		int delete = -1;  // nom de l'input dans le formulaire
		
		if (val != null) {
			try {
				delete = Integer.parseInt(val);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (delete > 0) {
				manager.removeContact(delete);
			}
		}
		
		List<Contact> contact = manager.getAllContacts();	
		List<TagsParContact> listContacts = new ArrayList<>();
		List<Country> pays = ctman.getAllCountries();	
		List<Tag> tag = tagman.getAllTags();

		for(Contact cont : contact){
			TagsParContact tpc = new TagsParContact();
			tpc.setContact(cont);
			tpc.setTag(manager.getTagsByContact(cont.getId()));
			listContacts.add(tpc);
		}
	
		/* insérer le résultat(Calcul) dans le contexte */		
		req.setAttribute("listeContacts", contact);
		req.setAttribute("listePays", pays);
		req.setAttribute("listeTags", tag);
		req.setAttribute("tagParContact", listContacts);
		
		/* invoquer une page JSP */		
		req.getRequestDispatcher("/contacts.jsp").forward(req, resp);
	}	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String prenom = req.getParameter("prenom");
		String nom = req.getParameter("nom");
		String email = req.getParameter("email");
		String paysid = req.getParameter("listepays");		
		String[] tags = req.getParameterValues("listetags");
		
		if(paysid.equals("aucun")){
			paysid = null;
		}
		
		TagManager tagman = new TagManager(dataSource);

		CountryManager ctman = new CountryManager(dataSource);		
		ContactsManager manager = new ContactsManager(dataSource, ctman, tagman);
		Contact contact = null;
		
		if (tags !=null){
			contact = manager.createAndSaveContact(prenom, nom, email, paysid, Arrays.asList(tags));
		} else {
			contact = manager.createAndSaveContact(prenom, nom, email, paysid, null);
		}
		
		System.out.println("ICI????");
		
		List<Contact> listContact = manager.getAllContacts();	
		List<TagsParContact> tagsParContacts = new ArrayList<>();
		List<Country> pays = ctman.getAllCountries();	
		List<Tag> tag = tagman.getAllTags();

		for(Contact cont : listContact){
			TagsParContact tpc = new TagsParContact();
			tpc.setContact(cont);
			tpc.setTag(manager.getTagsByContact(cont.getId()));
			tagsParContacts.add(tpc);
		}
		
		req.setAttribute("listeContacts", listContact);
		req.setAttribute("listePays", pays);
		req.setAttribute("listeTags", tag);
		req.setAttribute("tagParContact", tagsParContacts);
	
		resp.setContentType("text/plain");
		
		req.getRequestDispatcher("/contacts.jsp").forward(req, resp);
		
	}		
	
}
