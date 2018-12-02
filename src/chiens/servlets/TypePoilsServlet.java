package chiens.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chiens.beans.TypePoilsBean;
import chiens.metier.Poils;

/**
 * Servlet implementation class TypePoilsServlet
 */
@WebServlet("/TypePoilsServlet")
public class TypePoilsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TypePoilsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    /*le methode g�re l'ajout de nouveau type de poils dans le tableau type_poils*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Poils newTypePoils = new Poils();
		try {
			/*On essaie de cr�er un type de poils � partir de l'information saisie*/
			TypePoilsBean TypePoils=newTypePoils.creerTypePoils(request);
			if(TypePoils==null) {
				   /*si le nouveau type de poils de chien n'a pas pu �tre cr�e on renvoie l'utilisateur
				    * sur la page du formulaire et affiche un message d'erreur expliquant o� �tait le probl�me*/
				   request.setAttribute("message", newTypePoils.getMessage());
				   ServletContext sc = request.getSession().getServletContext();    
		  		   RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/formPoils.jsp"); 
		  		   rd.forward(request, response);
			}
			else { 
				   /*si le nouveau type de poils avait �t� ajout� dans la base,
				    * l'utilisateur est redirig� sur la page de succ�ss ou le code de 
				    * nouveau type de poils g�n�r� automatiquement est affich� avec le nom de type de poils*/
				   request.setAttribute("poils", TypePoils);
		           ServletContext sc = request.getSession().getServletContext();    
	  		       RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/succesTypePoils.jsp"); 
	  		       rd.forward(request, response);
			}
		} catch (Exception e) {
			/*le cas o� il y avait un probl�me d'ordre technique*/
			ServletContext sc = request.getSession().getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/erreur.jsp"); 
		    rd.forward(request, response);
		}
		
	}

}
