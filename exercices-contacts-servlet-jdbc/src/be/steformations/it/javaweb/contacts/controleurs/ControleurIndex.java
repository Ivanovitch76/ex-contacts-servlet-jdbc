package be.steformations.it.javaweb.contacts.controleurs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControleurIndex extends javax.servlet.http.HttpServlet{


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* invoquer une page JSP */
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

}
