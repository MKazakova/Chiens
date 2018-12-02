package chiens.metier;

import javax.servlet.http.HttpServletRequest;

import chiens.bases_donnees.DBCouleurs;
import chiens.beans.CouleurBean;

public class Couleur {
    String nom_couleur;
    String message;
    
    /* ajoute une nouvelle couleur dans le tableau "couleurs" à partir de saisie d'utilisateur*/
    public CouleurBean creerCouleur(HttpServletRequest request) throws Exception {
    	request.setCharacterEncoding("UTF-8");
       	CouleurBean newCouleur = null;
    	nom_couleur=request.getParameter("nomCouleur");
    	/*verifie si le nom de couleur saisie par l'utilisateur n'est pas une chaine vide*/
    	if(nom_couleur==null||nom_couleur.isEmpty()) {
    		message="Le nom de Couleur ne doit pas être vide";	
    	}
    	/*verifie si le nom de couleur est de format correcte*/
    	else if(!verifNomCouleur(nom_couleur)){
    		message="Le format du nom de Couleur n'est pas correct";
    	}
    	else {
    		/*fait de la sorte que la première lettre du nom de couleur soit en majuscule*/
    		nom_couleur=premiereLettreMajuscule(nom_couleur);
    		DBCouleurs dbp = new DBCouleurs();
    		/*on verifie si le nom de couleur n'existe pas encore dans la base*/
    		if(dbp.couleurExiste(nom_couleur)) message=nom_couleur+" existe déjà dans la base";
    		else{//si tous se passe bien on crée CouleurBean et l'ajoute dans la base 
    			newCouleur = new CouleurBean();
    			newCouleur.setNom_couleur(nom_couleur);
    			newCouleur.setCode_couleur(dbp.ajouterCouleur(nom_couleur));
    			if(newCouleur.getCode_couleur()==null) newCouleur = null;
    	   }
    	    
    	}
		return newCouleur;
    }
    
    /* retourne un message d'erreur dans le cas écheant*/
    public String getMessage() {
      return message;	
    }
    
    /*verifie que le nom de couleur ne contient pas de chiffres et sa longueur est correcte*/
    public boolean verifNomCouleur(String nom_couleur) {
    		return nom_couleur.matches("[^0-9]{2,30}");
    }
    
    private String premiereLettreMajuscule(String nom_couleur){
		return nom_couleur.substring(0, 1).toUpperCase() + nom_couleur.substring(1);
	}
    
}