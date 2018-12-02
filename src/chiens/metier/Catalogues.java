package chiens.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import chiens.bases_donnees.DBAptitude;
import chiens.bases_donnees.DBAptitudeChien;
import chiens.bases_donnees.DBCouleurChien;
import chiens.bases_donnees.DBCouleurs;
import chiens.bases_donnees.DBElevage;
import chiens.bases_donnees.DBElevageRace;
import chiens.bases_donnees.DBPays;
import chiens.bases_donnees.DBPoilsChien;
import chiens.bases_donnees.DBRaceChien;
import chiens.bases_donnees.DBTypePoils;
import chiens.beans.AptitudeBean;
import chiens.beans.CouleurBean;
import chiens.beans.ElevageBean;
import chiens.beans.PaysBean;
import chiens.beans.RaceChienBean;
import chiens.beans.TypePoilsBean;

public class Catalogues {
    
	/* retourne une liste des pays sauf le pays dont le code est passé en paramètre*/
    public static ArrayList<PaysBean> getAutrePays(String codePays) {
    	DBPays dbp = new DBPays();
    	ArrayList<PaysBean> payslistAutre = dbp.getPaysListAutre(codePays);
    	return payslistAutre;
    }
    
    /* retorne un Map avec les codes des pays comme clés et les noms de pays comme valeurs*/
    public static HashMap<Integer, String> getMapDePays() {
    	HashMap <Integer,String> pays=DBPays.getPaysMap();
		return pays;
    }
    
    /* crée un Map avec les codes de races comme clés et une chaine de caractère contenant les noms
     * de toutes les aptitudes de race données concatenés ensembles*/
    public static HashMap<Integer, String> getMapDePoils() {
    	ArrayList <RaceChienBean> listraces = DBRaceChien.getTousLesRacesChiens();
		HashMap<Integer, String> types_poils = new HashMap<>();
		for(RaceChienBean race: listraces) {
			int key = race.getCode_race();
			RaceChien chien = new RaceChien(key);
		    ArrayList<String> listpoils= chien.getType_poils();
		    String value="";
		       for(String str: listpoils) {
		    	   value+=str+", ";//separe les noms des aptitudes par les virgule
		       }
		    if(value.length()>2) value=value.substring(0, value.length()-2);//supprime la dernière virgule
		    types_poils.put(key, value);
		}
		return types_poils;
    }
    
    /* retourne une collection Map des aptitudes de chiens, code d'une aptitude comme clé et un nom
     * de l'aptitude comme valeur*/
    public static HashMap<Integer, String> getMapAptitudes() {
    	HashMap <Integer,String> aptitudes=DBAptitudeChien.getMapAptitudes();
    	return aptitudes;
    }
    
    /* retourne une collection de toutes les races de chiens existantes dans la base*/
    public static ArrayList <RaceChienBean> getListRaces() {
    	ArrayList <RaceChienBean> listraces = DBRaceChien.getTousLesRacesChiens();
		return listraces;
    }
    
    /* retourne une collection des aptitudes(AptitudeBean) liées avec une race de chien dont
     * le code est passé en paramètre */
	public static ArrayList<AptitudeBean> getAptitudesPresents(String codeRace) {
		ArrayList<AptitudeBean> aptitudesPresents=DBAptitudeChien.getListAptitudesPresents(Integer.parseInt(codeRace));
		return aptitudesPresents;
	}

	/* retourne une collection des aptitudes(AptitudeBean) qui ne sont pas liées avec une race 
	 * de chien dont le code est passé en paramètre */
	public static ArrayList<AptitudeBean> getAptitudesAbsents(String codeRace) {
		ArrayList<AptitudeBean> aptitudesAbsents = DBAptitudeChien.getListAptitudesAbsents(Integer.parseInt(codeRace));
		return aptitudesAbsents;
	}
    
	/* retourne une collection des couleurs(CouleurBean) liées avec une race de chien dont
     * le code est passé en paramètre */
	public static ArrayList<CouleurBean> getCouleursPresents(String codeRace) {
		ArrayList<CouleurBean> couleursPresents = DBCouleurChien.getListCouleursPresents(Integer.parseInt(codeRace));
		return couleursPresents;
	}

	/* retourne une collection des couleurs(CouleurBean) qui ne sont pas liées avec une race
	 * de chien dont le code est passé en paramètre */
	public static ArrayList<CouleurBean> getCouleursAbsents(String codeRace) {
		ArrayList<CouleurBean> couleursAbsents = DBCouleurChien.getListCouleursAbsents(Integer.parseInt(codeRace));
		return couleursAbsents;
	}

	/* retourne une collection des types de poils(TypePoilsBean) liées avec une race de chien dont
     * le code est passé en paramètre */
	public static ArrayList<TypePoilsBean> getTypePoilsPresents(String codeRace) {
		ArrayList<TypePoilsBean> typePoilsPresents = DBPoilsChien.getListTypePoilsPresents(Integer.parseInt(codeRace));
		return typePoilsPresents;
	}

	/* retourne une collection des types de poils(TypePoilsBean) qui ne sont pas liées avec une race 
	 * de chien dont le code est passé en paramètre */
	public static ArrayList<TypePoilsBean> getTypePoilsAbsents(String codeRace) {
		ArrayList <TypePoilsBean>typePoilsAbsents = DBPoilsChien.getListTypePoilsAbsents(Integer.parseInt(codeRace));
		return typePoilsAbsents;
	}

	/* retourne une collection des toutes les couleurs existants dans le tableau "couleurs" */
	public static ArrayList<CouleurBean> getListDeCouleurs() {
		return (new DBCouleurs()).getCouleurList();
	}
	
	/* retourne une collection Map ou le code de couleur est une clé et une chaine de caractères avec
	 * les noms de races de chiens de cette couleur concatenés ensemble est une valeur associée*/
	public static HashMap<Integer, String> getListRacesChienCouleurDonne() {
		return DBCouleurChien.getMapChiensDeCouleurX();
	}
	
	/* retourne une collection Map ou le code d'une aptitude est une clé et une chaine de caractères avec
	 * les noms de races de chiens de cette aptitude concatenés ensemble est une valeur associée*/
	public static HashMap<Integer, String> getListRacesChienAptitudeDonne() {
		return DBAptitudeChien.getMapChiensAptitudeX();
	}
	
	/* retourne une liste de toutes les aptitudes existantes dans la base*/
	public static ArrayList<AptitudeBean> getListAptitudes(){
		return DBAptitude.getAptitudeList();
	}
	
	/* retourne une collection Map ou le code de type de poils est une clé et une chaine de caractères avec
	 * les noms de races de chiens de ce type de poils concatenés ensemble est une valeur associée*/
	public static HashMap<Integer, String> getListRacesChienTypePoilsDonne() {
		return DBPoilsChien.getMapChienstypePoilsX();
	}
	
	/* retourne une liste de tous les types de poils existantes dans la base*/
	public static ArrayList<TypePoilsBean> getListTypePoils(){
		return (new DBTypePoils()).getTypePoilsList();
	}
	
	/* retourne une collection Map ou le code de type de poils est une clé et une chaine de caractères avec
	 * les noms de races de chiens de ce type de poils concatenés ensemble est une valeur associée*/
	public static HashMap<Integer, String> getListRacesChienPaysDonne(){
		return DBPays.getMapChiensPaysX();
	}
	
	/* retourne une liste de tous les pays existants dans la base*/
	public static ArrayList<PaysBean> getListPays(){
		return (new DBPays()).getPaysList();
	}
	
	/* retourne une collection Map ou le code de race est une clé et une chaine de caractères avec
	 * les noms de couleurs possibles de cette race concatenés ensemble est une valeur associée*/
	public static HashMap<Integer, String> getMapCouleursParCodeRace(){
		ArrayList <RaceChienBean> listraces = DBRaceChien.getTousLesRacesChiens();
		HashMap<Integer, String> couleurs = new HashMap<>();
		for(RaceChienBean race: listraces) {
			int key = race.getCode_race();
			RaceChien chien = new RaceChien(key);
		    ArrayList<String> listcouleurs= chien.getCouleurs();
		    String value="";
		       for(String str: listcouleurs) {
		    	   value+=str+", ";
		       }
		    if(value.length()>2) value=value.substring(0, value.length()-2); 
		    couleurs.put(key, value);
		}
		return couleurs;
	}
	
	/* retourne une collection Map où le code d'élévage est une clé et une valeur est une 
	 * chaine de caractères contenant les noms de races de chiens élévées dans cet élévage
	 * concatenés ensemble*/
	public static Map<Integer, String> getMapRacesParElevage(){
		return DBElevageRace.getRacesElevage();
	}
	
	/* retourne une collection de tous élévages existant dans la base */
	public static ArrayList<ElevageBean> getListElevages(){
		return DBElevage.getElevageList();
	}
	
	/* supprime un élévage dont le code est passé en paramètre */
	public static void deleteElevage(String code) {
		DBElevageRace.deleteElevageParCodeElevage(code);
		DBElevage.deleteElevage(code);
	}
	
	/* retourne le nom d'élévage dont le code est passé en paramètre */
	public static ElevageBean getElevageParCode(String code) {
		return DBElevage.getElevage(code);
	}
	
	/* retourne une collection Map avec les races(clé - code de race, valeur _ nom de race) 
	 * élévées(si true est passé en paramètre) ou ne sont pas élévés(dans le cas contraire)
	 * dans un élévage dont le code est passé en paramètre */
	public static Map<Integer, String> getRacesElevéesDansElevage(String code, boolean present){
		return DBElevageRace.getRacesElevage(code, present);
	}
	
	/* retourne une collection de toutes les races de chiens de la base regroupés par ordre alphabétique
	 * où le clé est un Character, une lettre de l'alphabet, et une valeur est une liste de races de chiens 
	 * qui commencent par cette lettre */
	public static Map<Character, List <RaceChienBean>> getListRacesAlphabet(){
		Map<Character, List<RaceChienBean>> chiensParAlphabet = 
		DBRaceChien.getTousLesRacesChiens().stream().collect(Collectors.groupingBy((s)->s.getNom_race().charAt(0)));
		return chiensParAlphabet;
	}
}
