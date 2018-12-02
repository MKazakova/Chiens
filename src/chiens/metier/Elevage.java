package chiens.metier;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import chiens.bases_donnees.DBElevage;
import chiens.bases_donnees.DBElevageRace;
import chiens.beans.ElevageBean;

public class Elevage {
	private Map<String, String> erreurs = new HashMap<String, String>();
	private boolean ajoute;
	
	/* cr�e un nouvel �l�vage � partir du request renvoy�  par servlet */
	public ElevageBean creerElevage(HttpServletRequest request) throws Exception {
		/* recoupere l'information du formulaire rempli par l'utilisateur */
		ElevageBean elevage = recoupererInfoFormulaire(request);
		
		/* si aucune erreur n'�tait g�n�r�, cela veut dire que les champs ont �t� remplies
		 * correctement */
		if(erreurs.isEmpty()){
			
			/* on recoup�re le code de l'elevage g�n�r� automatiquement */
			int code = DBElevage.addElevage(elevage);
			elevage.setCodeElevage(code);
			ajoute=true;
			/* ici on recoupere le tableau de races de chiens(codes de races)
			 * qui sont �l�v�s dans un �l�vage donn� */
			String [] races = request.getParameterValues("races");
	        if(races!=null) {
	    	    for(String str: races) {
	    	    	/* on ajoute des liens entre les races �l�v�es et cet �l�vage */
	    	    	DBElevageRace.addlien(code, Integer.parseInt(str));
	    	    }
		    }
		}
		return elevage;
	}
	
	/* met � jour un �l�vage existant � partir du request renvoy�  par servlet */
	public ElevageBean editElevage(HttpServletRequest request) throws Exception {
		/* recoupere l'information du formulaire rempli par l'utilisateur */
		ElevageBean elevage = recoupererInfoFormulaire(request);
		/*on recoup�re le code de la race qu'on veut mettre � jour*/
		String code = request.getParameter("code");
		elevage.setCodeElevage(Integer.parseInt(code));
		if(erreurs.isEmpty()){
			
			/*on enregistre les changements*/
			DBElevage.updatElevage(elevage);
			ajoute=true;
			
			/*on supprime les liens existants entre cet �l�vage et races de chiens �l�v�dx*/
			DBElevageRace.deleteElevageParCodeElevage(code);
			
			/*on recoup�re une nouvelle liste de races de chiens */
			String [] races = request.getParameterValues("races");
	        if(races!=null) {
	    	    for(String str: races) {
	    	    	/* on ajoute des liens entre les races �l�v�es et cet �l�vage */
	    	    	DBElevageRace.addlien(elevage.getCodeElevage(), Integer.parseInt(str));
	    	    }
		    }
		}
		
		return elevage;
	}
	
	/* retourne true si une race a �t� ajout� ou chang� avec succ�s */
	public boolean isAjoute() {
		return ajoute;
	}

	/* verifie le format de email : une seule @ au milieu*/
	private boolean verifEmail(String email) throws Exception {
		if(email!=null) return email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)");
		else return false;
	}
	
	/* verifie le format de telephone*/
	private boolean verifTelephone(String telephone) {
		return telephone.matches("^(\\+)?[0-9]{8,30}");
	}
	
	/* retourne une liste d'erreures g�n�r�es lors de recouperation des donn�es saisies par l'utilisateur */
	public Map<String, String> getErreurs(){
		return erreurs;
	}
	
	private ElevageBean recoupererInfoFormulaire(HttpServletRequest request) throws Exception {
		/* ElevageBean va contenir l'information pass� par utilisateur */
		ElevageBean elevage = new ElevageBean();
		request.setCharacterEncoding("UTF-8");
		/* le champ du nom d'�l�vage */
		String nom = request.getParameter("nomElevage");
		/* c'est un champ obligatoire qui ne doit pas �tre vide */
		if(nom==null||nom.isEmpty()) {
			erreurs.put("nom", "Le nom d'elevage ne doit pas �tre vide");
		}
		else {// initialise le champ nom de ElevageBean
			elevage.setNomElevage(nom);
		}
		
		String telephone = request.getParameter("telephone");
		/* le champ telephone est obligatoire, donc il ne doit pas �tre vide et doit �tre de
		 * format correcte, cad ne contenir que de chiffres et eventuellement "+" au debut */
		if(telephone!=null&&!telephone.isEmpty()&&!verifTelephone(telephone)) { 
			erreurs.put("telephone", "Le format de telephone n'est pas valide");
		}
		else {// si tout se passe bien, on initialise le champ telephone de ElevageBean
			elevage.setTelephone(telephone);
		}
		
		/* le champ adresse */
		String adresse = request.getParameter("adresse");
		/* l'adresse n'est pas obligatoire, donc on ne genere pas de message d'erreur, mais on 
		 * n'initialise le champ du ElevageBean que s'il est remlpie */
		if(adresse!=null&&!adresse.isEmpty()) {
			elevage.setAdresse(adresse);
		}
		
		String email = request.getParameter("email");
		/* le champ email est obligatoire et doit �tre de format email valide */
		if(email!=null&&!email.isEmpty()&&!verifEmail(email)) {
			erreurs.put("email", "Email n'est pas valide");
		}
		else {// si tout va bien on initialise le champ email
			elevage.setEmail(email);
		}
		
		String siteInternet = request.getParameter("siteInternet");
		/* le champ site internet doit �tre de format correcte et ne doit pas �tre vide */
		if(siteInternet!=null&&!siteInternet.isEmpty()) { 
			 erreurs.put("siteInternet", "Le champ internet ne doit pas �tre vide");
		}
		else {
			// si tout va bien on initialise le champ siteInternet
			elevage.setSiteInternet(siteInternet);
		}
		return elevage;
	}
	
}
