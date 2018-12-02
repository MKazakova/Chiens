package chiens.metier;

import java.util.ArrayList;
import chiens.bases_donnees.DBAptitudeChien;

public class AptitudeChien {	
	/*ajoute plusieurs liens code-aptitude-code-race � partir du code de race et une collection(ArrayList)
	 *  des codes des aptitudes pass�es en param�tres */
	public static boolean ajouterPlusieursAptitudes(ArrayList<Integer> aptitudes, Integer codeRace){
		boolean succes=false;
		for(Integer codeAp: aptitudes){
			if(!DBAptitudeChien.lienExiste(codeAp, codeRace)){
				DBAptitudeChien.addlien(codeAp, codeRace);
			}
		}
		return succes;
	}
	
}
