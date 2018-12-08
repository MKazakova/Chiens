package chiens.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chiens.bases_donnees.DBAptitude;
import chiens.bases_donnees.DBAptitudeChien;
import chiens.bases_donnees.DBCouleurChien;
import chiens.bases_donnees.DBCouleurs;
import chiens.bases_donnees.DBElevage;
import chiens.bases_donnees.DBPays;
import chiens.bases_donnees.DBPoilsChien;
import chiens.bases_donnees.DBRaceChien;
import chiens.bases_donnees.DBTypePoils;
import chiens.metier.Catalogues;

/**
 * Servlet implementation class CataloguesServlet
 */
@WebServlet("/CataloguesServlet")
public class CataloguesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CataloguesServlet() {
        super();
    }
    
	/**
	 * @throws Exception 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
			
   int doIt() throws Exception {  throw new Exception("FORGET IT");  
   }
	
	/*Utilisé pour affichage des differents tableau existants dans la base des données chiens*/
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String whatsend = request.getParameter("whatsend");
		if(whatsend.equals("Voir la liste des couleurs")) {//pour affichage de tableau des couleurs
			setAttrCouleur(request);//établissement des attributes de session correspondants
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listCouleurs.jsp"); 
			rd.forward(request, response);
		}
		else if(whatsend.equals("Voir la liste aptitudes")) {//pour affichage de tableau des aptitudes
			setAttrAptitude(request);//établissement des attributes de session correspondants
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listAptitudes.jsp");
			rd.forward(request, response);
		}
		else if(whatsend.equals("Voir la liste de types de poils")) {//pour affichage de tableau de types de poils
			setAttrTypePoils(request);//établissement des attributes de session correspondants
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listTypePoils.jsp");
			rd.forward(request, response);
		}
		else if(whatsend.equals("Voir la liste des pays")) {//pour affichage de tableau des pays
			setAttrPays(request);//établissement des attributes de session correspondants
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listPays.jsp");
			rd.forward(request, response);
		}
		else if(whatsend.equals("Voir la liste elevages")) {//affichage de tableau des élévages
			setAttrElevage(request);//établissement des attributes de session correspondants
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listElevages.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/*gère la suppression des éléments des tableaux differents en fonction de la valeur de whatsend*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String whatsend = request.getParameter("whatsend");
		
		if(whatsend.equals("delete")) {//première demande de suppression de couleur
			String code = request.getParameter("element");
			String nom = DBCouleurs.getNomParCode(Integer.parseInt(code));
			setAttrCouleur(request);//établissement des attributes de session correspondants
			request.setAttribute("code", code);
			request.setAttribute("nom", nom);
			request.setAttribute("confirmation", true);//affiche une fenêtre de confirmation de suppression de couleur
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listCouleurs.jsp"); 
			rd.forward(request, response);
		}
		else if(whatsend.equals("SuppressionConfirme")) {//la suppression est confirmé, on supprime la couleur
			String code=request.getParameter("element");
			DBCouleurChien.deleteCouleurParCodeCouleur(code);
			DBCouleurs.deleteCouleur(code);
			setAttrCouleur(request);//établissement des attributes de session correspondants
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listCouleurs.jsp"); 
			rd.forward(request, response);
		}
		else if(whatsend.equals("SuppressionConfirmeApt")) {//la suppression d'une aptitude est confirmé, on la supprime
			String code=request.getParameter("element");
			DBAptitudeChien.deleteAptitudeParCodeAptitude(code);
			DBAptitude.deleteAptitude(code);
			setAttrAptitude(request);//établissement des attributes de session correspondants
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listAptitudes.jsp"); 
			rd.forward(request, response);
		}
		else if(whatsend.equals("deleteApt")) {//première demande de suppression d'une aptitude
			String code = request.getParameter("element");
			String nom = DBAptitude.getNomParCode(Integer.parseInt(code));
			setAttrAptitude(request);//établissement des attributes de session correspondants
			request.setAttribute("code", code);
			request.setAttribute("nom", nom);
			request.setAttribute("confirmation", true);//affiche une fenêtre de confirmation de suppression de couleur
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listAptitudes.jsp"); 
			rd.forward(request, response);
		}
		else if(whatsend.equals("SuppressionConfirmeTP")) {//la suppression d'un type de poils est confirmé, on le supprime
			String code=request.getParameter("element");
			DBPoilsChien.deleteTypePoilsParCodeTypePoils(code);
			DBTypePoils.deleteTypePoils(code);
			setAttrTypePoils(request);//établissement des attributes de session correspondants
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listTypePoils.jsp"); 
			rd.forward(request, response);
		}
		else if(whatsend.equals("deleteTP")) {//la première demande de suppression de type de poils
			String code = request.getParameter("element");
			String nom = DBTypePoils.getNomParCode(Integer.parseInt(code));
			setAttrTypePoils(request);//établissement des attributes de session correspondants
			request.setAttribute("code", code);
			request.setAttribute("nom", nom);
			request.setAttribute("confirmation", true);//affiche une fenêtre de confirmation de suppression de type de poils
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listTypePoils.jsp"); 
			rd.forward(request, response);
		}
		else if(whatsend.equals("deletePays")) {//la première demande de suppression de pays
			String code = request.getParameter("element");
			if(DBRaceChien.RacesOriginairesExistent(code)) request.setAttribute("liee", true);
			String nom = DBPays.getNomDePays(Integer.parseInt(code));
			request.setAttribute("code", code);
			request.setAttribute("nom", nom);
			request.setAttribute("confirmation", true);//affiche une fenêtre de confirmation de suppression de pays
			setAttrPays(request);//établissement des attributes de session correspondants
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listPays.jsp");
			rd.forward(request, response);
		}
		else if(whatsend.equals("SuppressionConfirmePays")) {//la suppression d'un pays est confirmé, on le supprime
			String code=request.getParameter("element");
			DBPays.deletePays(code);
			setAttrPays(request);//établissement des attributes de session correspondants
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listPays.jsp"); 
			rd.forward(request, response);
		}
		else if(whatsend.equals("deleteElevage")) {
			String code = request.getParameter("element");
			String nom = DBElevage.getNomParCode(Integer.parseInt(code));
			setAttrElevage(request);//établissement des attributes de session correspondants
			request.setAttribute("code", code);
			request.setAttribute("nom", nom);
			request.setAttribute("confirmation", true);//affiche une fenêtre de confirmation de suppression de pays
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listElevages.jsp"); 
			rd.forward(request, response);
		}
		else if(whatsend.equals("SuppressionConfirmeElevage")) {//la suppression d'un élévage est confirmé, on le supprime
			String code=request.getParameter("element");
			Catalogues.deleteElevage(code);
			setAttrElevage(request);//établissement des attributes de session correspondants
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listElevages.jsp"); 
			rd.forward(request, response);
		}
	}

	/*Les methodes suivantes ajoute les attributes necessaires pour l'affichage de l'information 
	 * sur la page dans le request passé en paramètre*/
    private static void setAttrAptitude(HttpServletRequest request) {
    	request.setAttribute("races_aptitudes", Catalogues.getListRacesChienAptitudeDonne());
		request.setAttribute("listatributes", Catalogues.getListAptitudes());
    }
    
    private static void setAttrPays(HttpServletRequest request) {
    	request.setAttribute("races_pays", Catalogues.getListRacesChienPaysDonne());
		request.setAttribute("listpays", Catalogues.getListPays());
    }
    
    private static void setAttrCouleur(HttpServletRequest request) {
		request.setAttribute("listcouleurs", Catalogues.getListDeCouleurs());
		request.setAttribute("races_couleurs", Catalogues.getListRacesChienCouleurDonne());
    }
    
    private static void setAttrElevage(HttpServletRequest request) {
		request.setAttribute("list_elevages", Catalogues.getListElevages());
		request.setAttribute("elevages_races", Catalogues.getMapRacesParElevage());
    }
    
    private static void setAttrTypePoils(HttpServletRequest request) {
    	request.setAttribute("races_types_poils", Catalogues.getListRacesChienTypePoilsDonne());
		request.setAttribute("listtypepoils", Catalogues.getListTypePoils());
    }
}
