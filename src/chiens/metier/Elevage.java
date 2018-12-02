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
	
	/* crée un nouvel élévage à partir du request renvoyé  par servlet */
	public ElevageBean creerElevage(HttpServletRequest request) throws Exception {
		/* recoupere l'information du formulaire rempli par l'utilisateur */
		ElevageBean elevage = recoupererInfoFormulaire(request);
		
		/* si aucune erreur n'était généré, cela veut dire que les champs ont été remplies
		 * correctement */
		if(erreurs.isEmpty()){
			
			/* on recoupère le code de l'elevage généré automatiquement */
			int code = DBElevage.addElevage(elevage);
			elevage.setCodeElevage(code);
			ajoute=true;
			/* ici on recoupere le tableau de races de chiens(codes de races)
			 * qui sont élévés dans un élévage donné */
			String [] races = request.getParameterValues("races");
	        if(races!=null) {
	    	    for(String str: races) {
	    	    	/* on ajoute des liens entre les races élévées et cet élévage */
	    	    	DBElevageRace.addlien(code, Integer.parseInt(str));
	    	    }
		    }
		}
		return elevage;
	}
	
	/* met à jour un élévage existant à partir du request renvoyé  par servlet */
	public ElevageBean editElevage(HttpServletRequest request) throws Exception {
		/* recoupere l'information du formulaire rempli par l'utilisateur */
		ElevageBean elevage = recoupererInfoFormulaire(request);
		/*on recoupère le code de la race qu'on veut mettre à jour*/
		String code = request.getParameter("code");
		elevage.setCodeElevage(Integer.parseInt(code));
		if(erreurs.isEmpty()){
			
			/*on enregistre les changements*/
			DBElevage.updatElevage(elevage);
			ajoute=true;
			
			/*on supprime les liens existants entre cet élévage et races de chiens élévédx*/
			DBElevageRace.deleteElevageParCodeElevage(code);
			
			/*on recoupère une nouvelle liste de races de chiens */
			String [] races = request.getParameterValues("races");
	        if(races!=null) {
	    	    for(String str: races) {
	    	    	/* on ajoute des liens entre les races élévées et cet élévage */
	    	    	DBElevageRace.addlien(elevage.getCodeElevage(), Integer.parseInt(str));
	    	    }
		    }
		}
		
		return elevage;
	}
	
	/* retourne true si une race a été ajouté ou changé avec succès */
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
	
	/* retourne une liste d'erreures générées lors de recouperation des données saisies par l'utilisateur */
	public Map<String, String> getErreurs(){
		return erreurs;
	}
	
	private ElevageBean recoupererInfoFormulaire(HttpServletRequest request) throws Exception {
		/* ElevageBean va contenir l'information passé par utilisateur */
		ElevageBean elevage = new ElevageBean();
		request.setCharacterEncoding("UTF-8");
		/* le champ du nom d'élévage */
		String nom = request.getParameter("nomElevage");
		/* c'est un champ obligatoire qui ne doit pas être vide */
		if(nom==null||nom.isEmpty()) {
			erreurs.put("nom", "Le nom d'elevage ne doit pas être vide");
		}
		else {// initialise le champ nom de ElevageBean
			elevage.setNomElevage(nom);
		}
		
		String telephone = request.getParameter("telephone");
		/* le champ telephone est obligatoire, donc il ne doit pas être vide et doit être de
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
		/* le champ email est obligatoire et doit être de format email valide */
		if(email!=null&&!email.isEmpty()&&!verifEmail(email)) {
			erreurs.put("email", "Email n'est pas valide");
		}
		else {// si tout va bien on initialise le champ email
			elevage.setEmail(email);
		}
		
		String siteInternet = request.getParameter("siteInternet");
		/* le champ site internet doit être de format correcte et ne doit pas être vide */
		if(siteInternet!=null&&!siteInternet.isEmpty()) { 
			 erreurs.put("siteInternet", "Le champ internet ne doit pas être vide");
		}
		else {
			// si tout va bien on initialise le champ siteInternet
			elevage.setSiteInternet(siteInternet);
		}
		return elevage;
	}
	
}
