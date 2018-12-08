package chiens.metier;

import java.util.ArrayList;

import chiens.bases_donnees.DBAptitudeChien;
import chiens.bases_donnees.DBCouleurChien;
import chiens.bases_donnees.DBPays;
import chiens.bases_donnees.DBPoilsChien;
import chiens.bases_donnees.DBRaceChien;
import chiens.beans.RaceChienBean;

public class RaceChien {
	
	/* une classe qui contient tous les caaractèristiques essentiels d'une race de chien */
    private RaceChienBean chien;//les caractèristiques propres
    private String nom_pays;//le pays de provenance d'une race
    private ArrayList <String> aptitudes;//les aptitudes que cette race a
    private ArrayList <String> couleurs;//les couleurs possibles de la race
    private ArrayList <String> type_poils;//les types de poils de la race
    
	public RaceChien(Integer code) {
		
		/*on recoupère les caracteristiques de la race par code*/
		chien = DBRaceChien.getRaceChien(code);
		
		/*on recoupere le nom de pays du tableau pays par son code*/
		nom_pays=DBPays.getNomDePays(chien.getPays_provenance());
		try {
			/* on recoupere les aptitudes, les couleurs et les types de poils 
			 * dans la base de données par le code de race */
			aptitudes=DBAptitudeChien.getAptitudesDeRace(code);
			couleurs=DBCouleurChien.getCouleursDeRace(code);
		    type_poils=DBPoilsChien.getTypePoilsRace(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    
	public RaceChienBean getChien() {
		return chien;
	}

	public void setChien(RaceChienBean chien) {
		this.chien = chien;
	}

	public String getNom_pays() {
		return nom_pays;
	}

	public void setNom_pays(String nom_pays) {
		this.nom_pays = nom_pays;
	}

	public ArrayList<String> getAptitudes() {
		return aptitudes;
	}

	public void setAptitudes(ArrayList<String> aptitudes) {
		this.aptitudes = aptitudes;
	}

	public ArrayList<String> getCouleurs() {
		return couleurs;
	}

	public void setCouleurs(ArrayList<String> couleurs) {
		this.couleurs = couleurs;
	}

	public ArrayList<String> getType_poils() {
		return type_poils;
	}

	public void setType_poils(ArrayList<String> type_poils) {
		this.type_poils = type_poils;
	}



	
	
	
}
