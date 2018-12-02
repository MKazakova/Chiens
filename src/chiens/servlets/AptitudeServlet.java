package chiens.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chiens.beans.AptitudeBean;
import chiens.metier.Aptitude;

/**
 * Servlet implementation class AptitudeServlet
 */
@WebServlet("/AptitudeServlet")
public class AptitudeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AptitudeServlet() {
        super();
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Aptitude newApt = new Aptitude();
		try {//création de nouvelle aptitude de chien à partir des données saisies par l'utilisateur
			AptitudeBean aptitude=newApt.creerAptitude(request);
			if(aptitude==null) {//si une aptitude n'a pas pu être crée			
				   request.setAttribute("message", newApt.getMessage());//envoie à l'utilisateur un message d'erreur
				   ServletContext sc = request.getSession().getServletContext();
				   /*renvoie l'utilisateur à la page de forme de saisie*/
		  		   RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/formAptitude.jsp"); 
		  		   rd.forward(request, response);
			}
			else { /*si une aptitude à été crée avec succes*/
				   request.setAttribute("aptitude", aptitude);//renvoie les données d'une aptitude crée à l'utilisateur
		           ServletContext sc = request.getSession().getServletContext();    
	  		       RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/succesAptitude.jsp"); 
	  		       rd.forward(request, response);
			}
		} catch (Exception e) {
			ServletContext sc = request.getSession().getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/erreur.jsp"); 
		    rd.forward(request, response);
		}
		
	}

}
