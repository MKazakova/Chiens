package chiens.metier;

import javax.servlet.http.HttpServletRequest;

import chiens.bases_donnees.DBAptitude;
import chiens.beans.AptitudeBean;

public class Aptitude {
	String nom_aptitude;
	String MESSAGE;
    
    /*crée une aptitude et l'ajout dans le tableau */
    public AptitudeBean creerAptitude(HttpServletRequest request) throws Exception {
    	
    	request.setCharacterEncoding("UTF-8");
       	AptitudeBean newAptitude = null;
    	nom_aptitude=request.getParameter("nomAptitude");
    	
    	/*si le nom saisie est vide le message d'erreur est initié*/
    	if(nom_aptitude==null||nom_aptitude.isEmpty()) {
    		MESSAGE="L'aptitude ne doit pas être vide";	
    	}
    	/*si le nom du pays saisie n'est pas au bon format*/
    	else if(!verifNomPays(nom_aptitude)){
    		MESSAGE="Le format du nom de pays n'est pas correct";
    	}
    	/*si le format saisie est correcte*/
    	else {
    		if(DBAptitude.aptitudeExiste(nom_aptitude)) {//si le nom existe déjà dans la base
    			MESSAGE=nom_aptitude+" existe déjà dans la base";
    		}
    		else{
    			newAptitude = new AptitudeBean();
    			newAptitude.setNom_aptitude(nom_aptitude);
    			newAptitude.setCode_aptitude(DBAptitude.ajouterAptitude(nom_aptitude));
    			if(newAptitude.getCode_aptitude()==null) newAptitude = null;
    		}    
    	}
		return newAptitude;
    }
    
    public String getMessage() {
      return MESSAGE;	
    }
    
    public boolean verifNomPays(String nom_aptitude) {
    	    //verifie que le nom du pays ne contient pas de chiffres 
    		return nom_aptitude.matches("[^0-9]{2,30}");
    }
    
}
