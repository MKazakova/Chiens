package chiens.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chiens.metier.Catalogues;
import chiens.metier.CreationRaceChien;
import chiens.metier.RaceChien;



/**
 * Servlet implementation class RaceChienServlet
 */
@WebServlet("/RaceChienServlet")
public class RaceChienServlet extends HttpServlet {
	   
	/**
	 **/
	
	private static final long serialVersionUID = 1L;
	private String filePath;
	   
	public void init( ){
	      filePath = "C:\\Users\\huiny\\eclipse-workspace\\chiens\\WebContent\\images"; 
	   }
	   
	   /*L'affichage d'information sur une race de chiens particulière*/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	      throws ServletException, java.io.IOException { 
		   request.setCharacterEncoding("UTF-8");
		   String whatsend = request.getParameter("whatsend");
		   if(whatsend==null) {
		     try {/*en arrivant ici a	près l'envoie du formulaire de création d'une nouvelle race 
		         *on essaie de créer une nouvelle race dans le tableau des races de chiens*/
			   CreationRaceChien newRace = new CreationRaceChien(request, filePath);
			   if(newRace.isSucces()) {
				      /*si la création est reussie l'utilisateur est envoyé sur la page
			           *de l'information sur la race qui vient d'être crée, avec la posibilité de modification*/
				       getDetails(newRace.getCode(),request);
					   request.setAttribute("newRace", true);
				       ServletContext sc = request.getSession().getServletContext();    
			  		   RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/ficheRaceChien.jsp"); 
			  		   rd.forward(request, response);
			   }
			   else {
				/*sinon l'utilisateur est renvoyé sur la page de formulaire de saisie avec le/les messages
				 * d'erreur expliquants le problème*/
				   	   request.setAttribute("erreurs", newRace.getErreurs());
				   	   request.setAttribute("newrace", newRace.getRaceChien());
				   	   ServletContext sc = request.getSession().getServletContext();    
				   	   RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/formRaceChien.jsp"); 
				   	   rd.forward(request, response);
			 }
		    } 
		    catch (Exception e) {
			  ServletContext sc = request.getSession().getServletContext();
			  RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/erreur.jsp"); 
			  rd.forward(request, response);
		  }
		 }
		 else if(whatsend.equals("details")) {
			 /*donne l'information sur une race de chien dont le code est envoyé dans la request*/
			 String codeRaceDetails = request.getParameter("element");
			 getDetails(Integer.parseInt(codeRaceDetails),request);
			 ServletContext sc = request.getSession().getServletContext();
		  	 RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/ficheRaceChien.jsp"); 
		  	 rd.forward(request, response); 
		 }
		 else if(whatsend.equals("editer")) {
			 /*retourne l'information necessaire pour l'affichage du formulaire de l'édition de race */
			 String code=request.getParameter("element");
			   try {
				 RaceChien race_chien = new RaceChien(Integer.parseInt(code));
			     request.setAttribute("race", race_chien.getChien());
			     /*un nom de pays d'origine de race actuel pour le cas où l'utilisateur veux laisser le pays
			      *actuel sans le changer*/
			     request.setAttribute("pays", race_chien.getNom_pays());
			     /*un liste des autres pays(tous les pays de la base sauf le pays choisie actuellement) pour le cas où
			      * l'utilisateur veux changer le pays de provenance de race*/
			     request.setAttribute("payslist", Catalogues.getAutrePays(""+race_chien.getChien().getPays_provenance()));
			     /*la liste des aptitudes courantes de la race*/
				 request.setAttribute("aptitudespresents", Catalogues.getAptitudesPresents(code));
				 /*la liste des autres aptitudes possibles, sauf les aptitudes choisies couramment*/
				 request.setAttribute("aptitudesabsents", Catalogues.getAptitudesAbsents(code));
				 /*la liste des couleurs courantes de la race*/
			     request.setAttribute("couleurspresents", Catalogues.getCouleursPresents(code));
			     /*la liste des autres couleurs possibles, sauf les couleurs choisies couramment*/
			     request.setAttribute("couleursabsents", Catalogues.getCouleursAbsents(code));
			     /*la liste des types de poils courants de la race*/
			     request.setAttribute("type_poilsabsent", Catalogues.getTypePoilsAbsents(code));
			     /*la liste des autres types de poils possibles, sauf les types de poils choisies couramment*/
			     request.setAttribute("type_poilspresent", Catalogues.getTypePoilsPresents(code));
			     ServletContext sc = request.getSession().getServletContext();
		  		 RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/editRaceChien.jsp"); 
		  		 rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}  
		 }
	}
	      

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String whatsend = request.getParameter("whatsend");
		/*renvoie sur la page du formulaire de l'ajout d'une nouvelle race*/
		if(whatsend.equals("form")||whatsend.equals("Ajouter une Race")) {
		   HttpSession session = request.getSession();
		   session.setAttribute("payslist", Catalogues.getListPays());
		   session.setAttribute("tplist", Catalogues.getListTypePoils());
		   session.setAttribute("aptitudelist", Catalogues.getListAptitudes());
		   session.setAttribute("cclist", Catalogues.getListDeCouleurs());
		   ServletContext sc = request.getSession().getServletContext();    
		   RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/formRaceChien.jsp"); 
		   rd.forward(request, response);
	    }
		/*renvoie l'information sur une race de chiens données choisie à partir
		 *de liste de race de chiens par alphabet */
	    else if(whatsend.equals("rechercheAlpha")) {
		   String code = request.getParameter("code");
		   getDetails(Integer.parseInt(code),request);
		   request.setAttribute("ficheRaceAlpha", true);
		   ServletContext sc = request.getSession().getServletContext();
	  	   RequestDispatcher rd = sc.getRequestDispatcher("/user/page_d_accueil.jsp"); 
	  	   rd.forward(request, response);
	    }
     }

	/*initialise les attributes les plus courantes pour l'affichage de l'information sur la race de chiens*/
     private static void getDetails(Integer codeRaceDetails, HttpServletRequest request ){
    	RaceChien race_chien = null;
		race_chien = new RaceChien(codeRaceDetails);
		request.setAttribute("type_poils", race_chien.getType_poils());
	    request.setAttribute("aptitudes", race_chien.getAptitudes());
	    request.setAttribute("couleurs", race_chien.getCouleurs());
	    request.setAttribute("race", race_chien.getChien());
	    request.setAttribute("pays", race_chien.getNom_pays());
    }

}
