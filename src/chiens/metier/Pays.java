package chiens.metier;

import javax.servlet.http.HttpServletRequest;

import chiens.bases_donnees.DBPays;
import chiens.beans.PaysBean;

public class Pays {
    String nom_pays;
    String message;
    
    /*traite la création d'un nouveau pays à partir des données saisies par l'utilisateur*/
    public PaysBean creerPays(HttpServletRequest request) throws Exception {
    	request.setCharacterEncoding("UTF-8");
       	PaysBean newPays = null;
       	
       	/*on recoupère le nom de pays saisie par l'utilisateur*/
    	nom_pays=request.getParameter("nomPays");
    	
    	/*verifie que le nom de pays n'est pas vide*/
    	if(nom_pays==null||nom_pays.isEmpty()) {
    		message="Le nom de pays ne doit pas être vide";	
    	}
    	/*verifie que le nom de pays ne contient pas de chiffres et de longueur correcte*/
    	else if(!verifNomPays(nom_pays)){
    		message="Le format du nom de pays n'est pas correct";
    	}
    	else {
    		 /*si tout va bien met une première lettre en majuscule*/
    		nom_pays=premiereLettreMajuscule(nom_pays);
    		DBPays dbp = new DBPays();
    		/*on verifie si le nom de pays n'existe pas encore dans la base*/
    		if(dbp.nomExiste(nom_pays)) message=nom_pays+" existe déjà dans la base";
    		else{
    			/* si tout va bien on crée un PaysBean */
    			newPays = new PaysBean();
    			newPays.setNom_pays(nom_pays);//on initialise le nom du pays
    			newPays.setCode_pays(dbp.ajouterPays(nom_pays));//on recoupère le code du pays généré dans la base
    			if(newPays.getCode_pays()==null) newPays = null;
    		}
    	    
    	}
		return newPays;
    }
    
    /*recoupère le message d'erreur*/
    public String getMessage() {
      return message;	
    }
    
    /*verifie que le nom de pays ne contient pas de chiffres et de longueur correcte*/
    public boolean verifNomPays(String nom_pays) {
    		return nom_pays.matches("[^0-9]{2,30}");
    }
    
    /*met une première lettre en majuscule*/
    private String premiereLettreMajuscule(String nom_pays){
		return nom_pays.substring(0, 1).toUpperCase() + nom_pays.substring(1);
	}
}
