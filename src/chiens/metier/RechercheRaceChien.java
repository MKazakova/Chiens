package chiens.metier;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import chiens.bases_donnees.DBAptitudeChien;
import chiens.bases_donnees.DBCouleurChien;
import chiens.bases_donnees.DBPoilsChien;
import chiens.beans.RaceChienBean;


public class RechercheRaceChien {
	
	/* un methode qui choisie les races de chiens correspondantes à la recherche à partir de liste des toutes
	 * les races existantes dans la base */
	public static ArrayList <RaceChienBean> getRacesCorrespondantes(HttpServletRequest request){
    	try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	/* on commence par recouperer toutes les races existantes et les mettre dans ArrayList resultat */
    	ArrayList <RaceChienBean> resultat = Catalogues.getListRaces();
    	
    	String pays=request.getParameter("paysProvenance");
    	
    	if(!pays.equals("rien")) {
    		/* si un pays particulier était choisie comme critère 
    		 * on supprime de la liste toutes les races qui ont un pays de provenance 
    		 * autre qu'un pays choisie*/
    		resultat.removeIf(r->r.getPays_provenance()!=Integer.parseInt(pays));
    	}
    	
    	String [] aptitudes = request.getParameterValues("aptitude");
    	if(aptitudes!=null) {
    		/* si les aptitudes particulieres étaient choisies comme critères 
    		 * on supprime de la liste toutes les races qui n'ont pas ces attributes */
    	    for(String apt: aptitudes) {
					resultat.removeIf(r->!DBAptitudeChien.lienExiste(Integer.parseInt(apt), r.getCode_race()));
			};
    	}
    	
    	String [] caracteristiques = request.getParameterValues("caracterisiques");
        if(caracteristiques!=null) {
        	/* si les caractèristiques particulieres étaient choisies comme critères de recherche 
    		 * on supprime de la liste toutes les races qui ont dans cette caracteristique moins de 7 points sur 10 */
    	    for(String str: caracteristiques) {
    	    	switch (str) {
    	    		case "enfants": resultat.removeIf(r->r.getEntente_enfants()<7);
    	    		                break;
    	    		case "animaux": resultat.removeIf(r->r.getEntente_autres_animaux()<7);
    	    		                break;
    	    		case "garde":   resultat.removeIf(r->r.getApte_garde()<7);
    	    		                break;
    	    		case "education": resultat.removeIf(r->r.getFacilite_education()<7);
    	    	}
    	    }
        }
        
        String activite = request.getParameter("activite");
        /* si l'utilisateur choisit une race avec beaucoup d'activité, on supprime les races
         * qui ont moins de 8 points sur 10 en activité */
        if(activite.equals("beaucoup")) resultat.removeIf(r->r.getActivite()<8);
        /* si l'utilisateur choisit une race avec peu d'activité, on supprime les races
         * qui ont plus de 4 points sur 10 en activité */
        else if(activite.equals("peu")) resultat.removeIf(r->r.getActivite()>4);
        
        String typePoils = request.getParameter("typePoils");
        /* si le type de poils particulier était choisi comme critères 
		 * on supprime de la liste toutes les races qui n'ont pas ce type de poils */
        if(!typePoils.equals("rien")) resultat.removeIf(r->!DBPoilsChien.lienExiste(Integer.parseInt(typePoils), r.getCode_race()));
        
        String couleurChien = request.getParameter("couleurChien");
        /* si une couleur particuliere était choisi comme critères 
		 * on supprime de la liste toutes les races qui n'ont pas cette couleur */
        if(!couleurChien.equals("rien")) {
        	resultat.removeIf(r->!DBCouleurChien.lienExiste(Integer.parseInt(couleurChien), r.getCode_race()));
        
        }
        
        String taille = request.getParameter("taille");
        /* si une taille particuliere a été choisie on supprime les races qui ne correspondent pas */
        switch(taille) {
              case "grand": resultat.removeIf(r->r.getPoids_max()<30);
                            break;
              case "moyen": resultat.removeIf(r->r.getPoids_max()<=10||r.getPoids_max()>=30);
                            break;
              case "petit": resultat.removeIf(r->r.getPoids_max()>10);
                            break;
        } 
    	return resultat;
	}
	
}
