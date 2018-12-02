package chiens.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chiens.beans.PaysBean;
import chiens.metier.Pays;

/**
 * Servlet implementation class PaysServlet
 */
@WebServlet("/PaysServlet")
public class PaysServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaysServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    /*l'ajout d'un nouveau pays dans la base de données*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Pays newPays = new Pays();
		try {
			/*on ajoute un pays dans la base et crée un PaysBean à partir de l'information
			 * saisie par l'utilisateur*/
			PaysBean pays=newPays.creerPays(request);
			if(pays==null) {
				   /*si l'ajout n'est pas reussie l'utilisateur est renvoyé à la page
				    * du formulaire et le message d'erreur correspondant à son cas est renvoyé*/	
				   request.setAttribute("message", newPays.getMessage());
				   ServletContext sc = request.getSession().getServletContext();    
		  		   RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/formPays.jsp"); 
		  		   rd.forward(request, response);
			}
			else {/*si l'ajout de pays est reussie l'utilisateur est renvoyé à la page qui confirme 
			       * l'ajout du pays et donne son code autogénéré dans le tableau des pays*/
				   request.setAttribute("pays", pays);
		           ServletContext sc = request.getSession().getServletContext();    
	  		       RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/succesPays.jsp"); 
	  		       rd.forward(request, response);
			}
		} catch (Exception e) {
			ServletContext sc = request.getSession().getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/erreur.jsp"); 
		    rd.forward(request, response);
		}
		
	}

}
