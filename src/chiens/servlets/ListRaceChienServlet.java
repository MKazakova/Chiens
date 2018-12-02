package chiens.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chiens.bases_donnees.DBAptitudeChien;
import chiens.bases_donnees.DBCouleurChien;
import chiens.bases_donnees.DBElevageRace;
import chiens.bases_donnees.DBPoilsChien;
import chiens.bases_donnees.DBRaceChien;
import chiens.metier.Catalogues;

/**
 * Servlet implementation class ListRaceChienServlet
 */
@WebServlet("/ListRaceChienServlet")
public class ListRaceChienServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListRaceChienServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    /*gère l'affichage des races de chiens*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String whatsend = request.getParameter("whatsend");
		/*retourne la collection des races de chiens regroupée par alphabet*/
		if(whatsend.equals("racesParAlphabet")) {
			request.setAttribute("aptitudes", Catalogues.getMapAptitudes());
			request.setAttribute("listraces", Catalogues.getListRacesAlphabet());
			request.setAttribute("pays", Catalogues.getMapDePays());
			request.setAttribute("alphabet", true);
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/user/page_d_accueil.jsp"); 
			rd.forward(request, response);
		}
		/*retourne toutes les races des chiens*/
		else if (whatsend.equals("toutesRaces")) {
			setAttrRace(request);
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listeRacesChien.jsp"); 
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/*gère la suppression d'une race de chiens*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String whatsend = request.getParameter("whatsend");
		/*c'est la prèmiere demande de suppression d'une race, on affiche une fenêtre de confirmation*/
		if(whatsend.equals("delete")) {
			String code = request.getParameter("element");
			String nom = DBRaceChien.getNomParCode(Integer.parseInt(code));
			setAttrRace(request);
			request.setAttribute("code", code);
			request.setAttribute("nom", nom);/*pour une demande de confirmation personnalisée*/
			request.setAttribute("confirmation", true);
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listeRacesChien.jsp"); 
			rd.forward(request, response);
		}
		/*c'est après la confirmation de suppression de la race de chiens*/
		else if(whatsend.equals("SuppressionConfirme")) {
			String code=request.getParameter("element");
			/*avant de supprimer une race il faut supprimer tous les liens avec son code dans 
			 * les autres tableaux qui en font référence par foreign key*/
			DBCouleurChien.deleteCouleurParCodeChien(code);/*on supprime les liens code race-code couleur*/
			DBAptitudeChien.deleteAptitudeChien(code);/*ensuite les liens code arace-code aptitude*/
			DBPoilsChien.deleteTypePoilsChien(code);/* code race-code type poils*/
			DBElevageRace.deleteElevageParCodeRace(code);/* code race-code élévage*/
			DBRaceChien.deleteRace(code);/*et maintenant on peut proceder à la suppression de la race même*/
			setAttrRace(request);/*on inicialise les variables de request necessaires pour l'affichege 
			du tableau des race resultant*/
			ServletContext sc = request.getSession().getServletContext();    
			RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/listeRacesChien.jsp"); 
			rd.forward(request, response);
		}
	}
	
	private static void setAttrRace(HttpServletRequest request) {
		request.setAttribute("aptitudes", Catalogues.getMapAptitudes());
	    request.setAttribute("listraces", Catalogues.getListRaces());
		request.setAttribute("pays", Catalogues.getMapDePays());
	}

}
