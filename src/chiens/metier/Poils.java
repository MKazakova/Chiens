package chiens.metier;

import javax.servlet.http.HttpServletRequest;

import chiens.bases_donnees.DBTypePoils;
import chiens.beans.TypePoilsBean;

public class Poils {
    String description_type_poils;
    String message;

    /* traite la création d'un nouveau type de poils */
    public TypePoilsBean creerTypePoils(HttpServletRequest request) throws Exception {
    	request.setCharacterEncoding("UTF-8");
       	TypePoilsBean newTypePoils = null;
    	description_type_poils=request.getParameter("typePoils");
    	
    	/* on verifie que la description de type de poils n'est pas vide */
    	if(description_type_poils==null||description_type_poils.isEmpty()) {
    		message="La description de type de poils de chien ne doit pas être vide";	
    	}
    	/* on verifie que le nom de poils ne contient pas de chiffres et de longueur correcte */
    	else if(!verifNomTypePoils(description_type_poils)){
    		message="Le format du nom de pays n'est pas correct";
    	}
    	else {
    		DBTypePoils dbtp = new DBTypePoils();
    		/* on verifie que ce type de poils n'existe pas encore dans la base */
    		if(dbtp.nomExiste(description_type_poils)) {
    			message=description_type_poils+" existe déjà dans la base";
    		}
    		else{
    			newTypePoils = new TypePoilsBean();
    			
    			/* si tout se passe bien on initialise le champ description_type_poils 
    			 * dans TypePoilsBean, ajoute type poils dans la base et recoupère le code généré */
    			newTypePoils.setDescription_type_poils(description_type_poils);
    			newTypePoils.setCode_type_poils(dbtp.ajouterTypePoils(description_type_poils));
    			if(newTypePoils.getCode_type_poils()==null) newTypePoils = null;
    		}
    	    
    	}
		return newTypePoils;
    }
    
    /* retourne un message d'erreur */
    public String getMessage() {
      return message;	
    }
    
    /* on verifie que le nom de poils ne contient pas de chiffres et de longueur entre 2 et 30 caractères */
    public boolean verifNomTypePoils(String description_type_poils) {
    		return description_type_poils.matches("[^0-9]{2,30}");
    }
    
}
