package chiens.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chiens.beans.RaceChienBean;
import chiens.metier.Catalogues;
import chiens.metier.RechercheRaceChien;
import chiens.metier.TestRace;

/**
 * Servlet implementation class RechercheChienServlet
 */
@WebServlet("/RechercheChienServlet")
public class RechercheChienServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RechercheChienServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String whatsend = request.getParameter("whatsend");
		/*redirige l'utilisateur sur la page de recherche d'une race par paramètres à partir de 
		 * l'espace d'administrateur */
		if(whatsend.equals("rechercheAdmin")) { 
		    setAttrRaceAsLists(request);
		    ServletContext sc = request.getSession().getServletContext();    
		    RequestDispatcher rd = sc.getRequestDispatcher("/user/rechercheRace.jsp"); 
		    rd.forward(request, response);
		}
		/*redirige l'utilisateur sur la page de recherche d'une race par paramètres à partir de 
		 * la page d'accueil */
		else if(whatsend.equals("rechercheUser")) { 
			setAttrRaceAsLists(request);
			request.setAttribute("rechercheRace", true);
			ServletContext sc = request.getSession().getServletContext(); 
			RequestDispatcher rd = sc.getRequestDispatcher("/user/page_d_accueil.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String whatsend = request.getParameter("whatsend");
		/*le cas d'utilisateur qui a passé un test détérminant la race de chien qui lui correspond*/
		if(whatsend.equals("test")) { 
			/*on crée une variable de type TestRace*/
		    TestRace test = new TestRace(request);
		    /*et essaye d'obtenir une liste de races de chiens correspondantes à la situation
		     *de l'utilisateur*/
		    List<RaceChienBean> races = test.test();
		    try {
			  if(!test.isComplet()) throw new Exception();/*si un teste n'a pas été remplie correctement
			  on jette une exception qui sera traité dans le block catch*/
			  if(!races.isEmpty()) {/*si le test avait été traité correctement(donc on n'est pas sortie du try
			  on verifie si la liste de races correspondantes n'est pas vide*/
				request.setAttribute("trouve", true);//s'il n'est pas vide, on initialise trouve à true
				request.setAttribute("races", races);//et crée une attribute de request avec ces races
				setAttrRaceAsMaps(request);
			  }
			  else request.setAttribute("notrouve", true);//si aucune race correspondante n'a été trouvé

			    ServletContext sc = request.getSession().getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher("/user/page_d_accueil.jsp"); 
			    rd.forward(request, response);
		      }
		    catch (Exception e) {
			  if(!test.getErreur().isEmpty()){//si le test n'a pas été remplie correctement
				//on envoie une liste des messages d'erreurs à l'utilisateur
				request.setAttribute("erreur", test.getErreur());
			    request.setAttribute("echec", true);
				ServletContext sc = request.getSession().getServletContext();
				RequestDispatcher rd = sc.getRequestDispatcher("/user/test.jsp"); 
				rd.forward(request, response);
			  }
		   }
		}
		else {//si la recherche de race avait été effectué à travers le formulaire de recherche directe(sans test)
			/*on cherche des races correspondante à la recherche*/
			ArrayList<RaceChienBean> races = RechercheRaceChien.getRacesCorrespondantes(request);
			if(!races.isEmpty()) {//si les races correspondante avaient été crées
				request.setAttribute("trouve", true);
				request.setAttribute("races", races);
				setAttrRaceAsMaps(request);
			}
			else request.setAttribute("notrouve", true);//s'il n'y avait pas de races correspondantes
			
			ServletContext sc = request.getSession().getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/user/page_d_accueil.jsp"); 
			rd.forward(request, response);
		  }
	}
	
	/*une methodes qui instaurent les attributes necessaires à l'affichage*/
     private static void setAttrRaceAsLists(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("payslist", Catalogues.getListPays());
		session.setAttribute("tplist", Catalogues.getListTypePoils());
		session.setAttribute("aptitudelist", Catalogues.getListAptitudes());
		session.setAttribute("cclist", Catalogues.getListDeCouleurs());
	}

      private static void setAttrRaceAsMaps(HttpServletRequest request) {
		request.setAttribute("pays", Catalogues.getMapDePays());
		request.setAttribute("aptitudes", Catalogues.getMapAptitudes());
		request.setAttribute("couleurs", Catalogues.getMapCouleursParCodeRace());
		request.setAttribute("type_poils", Catalogues.getMapDePoils());
	}	
}
