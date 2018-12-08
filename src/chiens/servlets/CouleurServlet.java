package chiens.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chiens.beans.CouleurBean;
import chiens.metier.Couleur;


/**
 * Servlet implementation class CouleurServlet
 */
@WebServlet("/CouleurServlet")
public class CouleurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    	public CouleurServlet() {
        super();
   	 }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    /*gère la création de nouvelle couleur à partir de "formCouleur.jsp" */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Couleur newCouleur = new Couleur();
		try {
			/*crée une nouvelle couleur à partir de l'information saisie par l'utilisateur*/
			CouleurBean couleur=newCouleur.creerCouleur(request);
			if(couleur==null) {
			//si la CouleurBean n'a pas été crée le message d'erreur est retourné
				   request.setAttribute("message", newCouleur.getMessage());
				   ServletContext sc = request.getSession().getServletContext();  
				   //le request est rederigé sur la page de formulaire
		  		   RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/formCouleur.jsp"); 
		  		   rd.forward(request, response);
			}
			else {//si la CouleurBean a été crée avec succès
				   request.setAttribute("couleur", couleur);
		           ServletContext sc = request.getSession().getServletContext();
		           //le request est redirigé sur la page de succes
	  		       RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/succesCouleur.jsp"); 
	  		       rd.forward(request, response);
			}
		} catch (Exception e) {
			ServletContext sc = request.getSession().getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/erreur.jsp"); 
		    rd.forward(request, response);
		}
		
	}

}
