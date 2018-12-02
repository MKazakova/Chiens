package chiens.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chiens.bases_donnees.DBElevageRace;
import chiens.bases_donnees.DBRaceChien;
import chiens.beans.ElevageBean;
import chiens.metier.Catalogues;
import chiens.metier.Elevage;

/**
 * Servlet implementation class ElevageServlet
 */
@WebServlet("/ElevageServlet")
public class ElevageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ElevageServlet() {
        super();
    }
    
    /* gère l'ajout et l'affichage des élévages */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String whatsend = request.getParameter("whatsend");
		/* redirige vers la page de forme de l'élévage pour ajouter un élévage*/
		if(whatsend.equals("ajouter")) {
			request.setAttribute("races", Catalogues.getListRaces());
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/formElevages.jsp"); 
			rd.forward(request, response);
		}
		else if(whatsend.equals("elevagesDeRace")) {//affichage du tableau des élévages
			String code = request.getParameter("code");
			
			if(code.equals("0")) {//si le code est égale à 0, on affiche tous les élévages existants
				request.setAttribute("elevages", Catalogues.getListElevages());
				request.setAttribute("touteselevages", true);
			}
			else{//sinon on affiche des élévages où la race du code donnée est élévée
				request.setAttribute("nom", DBRaceChien.getNomParCode(Integer.parseInt(code)));
				request.setAttribute("elevages", DBElevageRace.getElevagesDeRace(code));
				request.setAttribute("elevrace", true);
			}
			/*redirige à la page d'accueil l'information sur les élévages*/
			request.setAttribute("elevages_races", Catalogues.getMapRacesParElevage());	
			ServletContext sc = request.getSession().getServletContext(); 
			RequestDispatcher rd = sc.getRequestDispatcher("/user/page_d_accueil.jsp"); 
			rd.forward(request, response);
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String whatsend = request.getParameter("whatsend");
		
		/* envoie l'information nécessaire à la page de formulaire pour éditer un élévage*/
		if(whatsend.equals("editerElevage")) {
			
			String code = request.getParameter("element");
			ElevageBean elb = Catalogues.getElevageParCode(code);
			/*crée deux Maps différents, un avec les races élévées dans l'élévage donnée et l'autre
			 *avec les races de la base qui ne sont pas élévées dans l'élévage donné */ 
			Map<Integer, String> races_presents = Catalogues.getRacesElevéesDansElevage(code, true);
			Map<Integer, String> races_absents = Catalogues.getRacesElevéesDansElevage(code, false);
			request.setAttribute("races_presents", races_presents);
			request.setAttribute("races_absents", races_absents);
			request.setAttribute("elevage", elb);
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/editElevage.jsp"); 
			rd.forward(request, response);
	    }
		/* gère l'enregistrement des changements dans un élévage */
		else if(whatsend.equals("updatElevage")){
			
		    Elevage elevage = new Elevage();
		    
			try {
				/*crée un ElevageBean à partir de l'information saisie par l'utilisateur 
				 * et l'ajoute dans le tableau*/
				ElevageBean elb =elevage.editElevage(request);
				if(elevage.isAjoute()) {
					//si l'ajout des changements est reussie
					request.setAttribute("elevage", elb);
					ServletContext sc = request.getSession().getServletContext();    
					RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/ficheElevage.jsp"); 
					rd.forward(request, response);
				}
				else {
					//si l'ajout des changements n'est pas reussie genere les messages d'erreur correspondants
					request.setAttribute("erreurs", elevage.getErreurs());
					request.setAttribute("races", Catalogues.getListRaces());
					request.setAttribute("elevage", elb);
					ServletContext sc = request.getSession().getServletContext();    
					RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/editElevages.jsp"); 
					rd.forward(request, response);
			    }
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		/* gère la création de nouvel élévage  */
	    else if(whatsend.equals("creerElevage")){
	    	
	    Elevage elevage = new Elevage();
	    
		try {
			/*crée un ElevageBean à partir de l'information saisie par l'utilisateur*/
			ElevageBean elb =elevage.creerElevage(request);
			
			if(elevage.isAjoute()) {
				/* si l'élévage a été ajouter dans la base avec succes */
				request.setAttribute("elevage", elb);
				ServletContext sc = request.getSession().getServletContext();    
				RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/ficheElevage.jsp"); 
				rd.forward(request, response);
			}
			else { 
				/* sinon reçoit un message d'erreur et le renvoie à la page de formulaire*/
				request.setAttribute("erreurs", elevage.getErreurs());
				request.setAttribute("races", Catalogues.getListRaces());
				request.setAttribute("elevage", elb);
				ServletContext sc = request.getSession().getServletContext();    
				RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/formElevages.jsp"); 
				rd.forward(request, response);
		    }
			
		}
		catch (Exception e) {
			ServletContext sc = request.getSession().getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/erreur.jsp"); 
		    rd.forward(request, response);
		}
	   }
	}

}
