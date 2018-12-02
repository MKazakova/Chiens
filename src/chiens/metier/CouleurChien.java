package chiens.metier;
import java.util.ArrayList;

import chiens.bases_donnees.DBCouleurChien;

public class CouleurChien {
  
	/* ajoute plusieurs liens code-couleur-code-race à partir du code de race et une collection(ArrayList)
	 *  des codes des couleurs passées en paramètres */
	public static boolean ajouterPlusieursCouleurs(ArrayList<Integer> couleurs, Integer codeRace) throws Exception {
		boolean succes=false;
		for(Integer codeCol: couleurs) {
			if(!DBCouleurChien.lienExiste(codeCol, codeRace)) {
				DBCouleurChien.addlien(codeCol, codeRace);
			}
		}
		return succes;
	}
	
	/*retourne une collection des noms de couleurs d'une race dont le code est passé en paramètre*/
	public static ArrayList<String> couleursDeRace(Integer codeRace) throws Exception{
		ArrayList<String> listeCouleurs = DBCouleurChien.getCouleursDeRace(codeRace);
		return listeCouleurs;
	}
}
