package chiens.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chiens.metier.EditionRaceChien;
import chiens.metier.RaceChien;

/**
 * Servlet implementation class EditRaceChienServlet
 */
@WebServlet("/EditRaceChienServlet")
public class EditRaceChienServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String filePath;
	
	   public void init( ){
		      filePath = "C:\\Users\\huiny\\eclipse-workspace\\chiens\\WebContent\\images"; 
		   }	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditRaceChienServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
    /*gère les changements dans la race de chiens */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			EditionRaceChien newRace = new EditionRaceChien(request, filePath);
			if(newRace.isSucces()) {//si les changements ont été bien enregistrés dans la base
				 RaceChien race_chien = new RaceChien(Integer.parseInt(newRace.getCode()));
				 //les données necessaires pour l'affichage sont définies 
				 request.setAttribute("type_poils", race_chien.getType_poils());
			     request.setAttribute("aptitudes", race_chien.getAptitudes());
			     request.setAttribute("couleurs", race_chien.getCouleurs());
			     request.setAttribute("race", race_chien.getChien());
			     request.setAttribute("pays", race_chien.getNom_pays());
			     ServletContext sc = request.getSession().getServletContext();
		  		 RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/ficheRaceChien.jsp"); 
		  		 rd.forward(request, response);
			}
			else {//si les changements n'ont pas été enregistré 
				 ServletContext sc = request.getSession().getServletContext();
		  		 RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/erreur.jsp");
		  		 rd.forward(request, response);
			}
		} catch (Exception e) {
			ServletContext sc = request.getSession().getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/erreur.jsp"); 
		    rd.forward(request, response);
		}
	}
}
