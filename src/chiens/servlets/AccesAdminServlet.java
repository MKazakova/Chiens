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

import chiens.bases_donnees.DBAccesAdmin;

/**
 * Servlet implementation class AccesAdminServlet
 */
@WebServlet("/AccesAdminServlet")
public class AccesAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccesAdminServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    /*g�re la fen�tre d'autorisation d'administrateur de la page d'accueil "user/page_d_accueil.jsp"*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String whatsend = request.getParameter("whatsend");
		if(whatsend.equals("connection")) {//ouvre la fen�tre d'autorisation d'administrateur
			  request.setAttribute("admin", true);
	          ServletContext sc = request.getSession().getServletContext();    
	  		  RequestDispatcher rd = sc.getRequestDispatcher("/user/page_d_accueil.jsp"); 
	  		  rd.forward(request, response);
		}
		else if(whatsend.equals("deconnection")) {//ferme la fen�tre d'autorisation d'administrateur
		      HttpSession session = request.getSession();
			  session.invalidate();
		      session = request.getSession(false);
			  response.sendRedirect(request.getContextPath()+"/user/page_d_accueil.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String whatsend = request.getParameter("whatsend");
		  if(whatsend.equals("autorisation")) {//autorisation d'un administrateur pour acceder � l'espace d'admin
		         String mot_passe=request.getParameter("motpasse");
		         String message="";
		         if(mot_passe!=null&&!mot_passe.isEmpty()) {//verifie si le champ mot de passe n'est pas vide
		        	      DBAccesAdmin databaseAcces = new DBAccesAdmin();
		        	      try {
							if(databaseAcces.verifAcces(mot_passe)) {//si le mot de pass� correspond � celui de la base
								  HttpSession session = request.getSession();
								  /*cree une variable de session administrateur dont la valeur est �gale � true, 
								   *pour que le Filtre permet d'acceder au contenu avec acces restreint*/
								  session.setAttribute("administrateur", true);
								  response.sendRedirect(request.getContextPath()+"/user/page_d_accueil.jsp");
							      }
							else {//si le mot de passe n'est pas correct
							  	  message = databaseAcces.getMessage();//recoupere un message d'erreur pour le transmettre � l'utilisateur
								  request.setAttribute("admin", true);//pour garder la fen�tre d'autorisation ouverte
						          request.setAttribute("message", message);//transmet le message d'erreur
						          ServletContext sc = request.getSession().getServletContext();    
						  		  RequestDispatcher rd = sc.getRequestDispatcher("/user/page_d_accueil.jsp"); 
						  		  rd.forward(request, response);
							     }
						  } 
		        	      catch (Exception e) {
							e.printStackTrace();
						   }
		          }
		         else { //si le champ de mot de passe est vide 
		          message="Le mot de passe ne doit pas �tre vide";
		          request.setAttribute("admin", true);//pour garder la fen�tre ouverte
		          request.setAttribute("message", message);//un message pour l'utilisateur
		          ServletContext sc = request.getSession().getServletContext();    
		  		  RequestDispatcher rd = sc.getRequestDispatcher("/user/page_d_accueil.jsp");//retourne � la page d'accueil
		  		  rd.forward(request, response);
		          }
          }
		  else if(whatsend.equals("changerMotPasse")) {//le changement du mot de passe d'administrateur
			     String ancienMotPasse=request.getParameter("ancienMP");//le mot de passe en cours
			     String nouveauMotPasse=request.getParameter("nouveauMP");//le nouveau mot de passe
		         String erreurAncienMotPasse="";
		         String erreurNouveauMotPasse="";
		         /*verifie si les champs des mots de passe sont rempli correctement 
		          * avant de comparer avec le mot de passe de la base*/
		         if(ancienMotPasse!=null&&!ancienMotPasse.isEmpty()&&nouveauMotPasse!=null
		        		 &&nouveauMotPasse.length()>=4) {//si les saisies sont correct
		        	      DBAccesAdmin databaseAcces = new DBAccesAdmin();
		        	      try {
							if(databaseAcces.verifAcces(ancienMotPasse)) {//compare l'ancien mot de passe avec celui de la base de donn�es
								//si ancien mot de passe est correcte, proc�de au changement
								if(databaseAcces.changerMotPasse(nouveauMotPasse)>0) {//si le changement est reussi
									  ServletContext sc = request.getSession().getServletContext();//envoie � la page de confirmation du changement reussi    
							  		  RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/succesNouveauMP.jsp"); 
							  		  rd.forward(request, response);
								}
								else{//si le changement n'est pas reussi
									  ServletContext sc = request.getSession().getServletContext(); //envoie � la page d'erreur       
							  		  RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/erreur.jsp"); 
							  		  rd.forward(request, response);
								} 
							 }
							 else {//si l'ancien mot de passe saisie ne correspond pas � celui de la base
								  erreurAncienMotPasse = databaseAcces.getMessage();
								  request.setAttribute("admin", true);
						          request.setAttribute("message", erreurAncienMotPasse);
						          ServletContext sc = request.getSession().getServletContext();    
						  		  RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/changementMotPasseAdmin.jsp"); 
						  		  rd.forward(request, response);
							     }
						  } 
		        	      catch (Exception e) {
							e.printStackTrace();
						   }
		          }
		          else{//si les saisies sont correct, cr�e les messages correspondants au types d'erreurs
		    	    if(ancienMotPasse==null||ancienMotPasse.isEmpty()) {
		    	    	erreurAncienMotPasse="Le mot de passe ne doit pas �tre vide";
		    	    }
		    	    if(nouveauMotPasse==null||nouveauMotPasse.length()<4) {
		    	    	erreurNouveauMotPasse="Le nouveau mot de passe doit �tre form� d'au moins 4 caract�res";
		    	    }
		    	    //transmet les messages d'erreur � l'utilisateur
	                request.setAttribute("message", erreurAncienMotPasse);
	                request.setAttribute("message2", erreurNouveauMotPasse);
	                ServletContext sc = request.getSession().getServletContext();    
	                RequestDispatcher rd = sc.getRequestDispatcher("/administrateur/changementMotPasseAdmin.jsp"); 
	                rd.forward(request, response);
	  		      }
       }
	   else if(whatsend.equals("deconnection")) {//deconnection de l'administrateur, l'espace d'administrateur n'est plus accessible
		      HttpSession session = request.getSession();
			  session.setAttribute("administrateur", false);//remet la variable de session "administrateur" � false
			  response.sendRedirect(request.getContextPath()+"/user/page_d_accueil.jsp");
	   }
	}  
}
 
