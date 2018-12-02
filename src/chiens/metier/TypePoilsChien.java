package chiens.metier;

import java.util.ArrayList;

import chiens.bases_donnees.DBPoilsChien;


public class TypePoilsChien {

	/* gère l'ajout de plusieurs liens code-type-poils : code-race à partir de ArrayList avec les codes 
	 * de types poils et un code de races de chiens */
	public static boolean ajouterPlusieursTypePoils(ArrayList<Integer> typePoils, Integer codeRace) 
			throws Exception {
		
		boolean succes=false;
		
		/* s'il n'y a pas encore de lien entre les deux codes, ajoute un tel lien */
		for(Integer codeTP: typePoils) {
			if(!DBPoilsChien.lienExiste(codeTP, codeRace)) {
				    DBPoilsChien.addlien(codeTP, codeRace);
			}
		}
		return succes;
	}
	
	/* retourne une liste de noms de types de poils de race dont le code est passé en paramètre */
	public static ArrayList<String> typePoilsDeRace(Integer codeRace) throws Exception{
		ArrayList<String> listeTypesPoils = DBPoilsChien.getTypePoilsRace(codeRace);
		return listeTypesPoils;
	}
}