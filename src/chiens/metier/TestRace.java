package chiens.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import chiens.bases_donnees.DBAptitudeChien;
import chiens.beans.RaceChienBean;

public class TestRace {
	
	 private String ACTIVITE;
	 private String SPORT;
	 private String ENFANTS;
	 private String GARDE;
	 private boolean COMPLET;
	 private HttpServletRequest request;
	 
	 public TestRace(HttpServletRequest request) {
		 this.request = request;
	 }
	 
	 /* un methode qui choisie les races de chiens correspondantes à la recherche à partir 
	  * de liste des toutes les races existantes dans la base */
	 public List<RaceChienBean> test(){
	   /* d'abord on recoupere toutes les races de chiens existantes dans la base */
	   List <RaceChienBean> races = Catalogues.getListRaces();
	   
	   /* on declare les predicates qui vont filtrer la liste */
	   Predicate <RaceChienBean> p1, p2, p3, p4, p5, p6, p7;
	   
	   if(getErreur().isEmpty()) {//s'il n'y avait pas d'erreurs de saisie
		   
		 /* si une personne habite dans l'appartement on ne garde que les chiens
		  * qui ont le poids maximale moins de 30 kg ou qui ont une activité moins 
		  * de moyen */
		 if(request.getParameter("habitation").equals("appartement")) {
			 p1 = r->r.getPoids_max()<30||r.getActivite()<5;
		 }
		 else {//sinon on ne filtre pas les races d echiens selon ce critère
			 p1 = r->true;
		 }
		 
		 /* si une personne n'aime pas les longues promenades on ne garde 
		  * que les chiens qui ont une activité moyen ou moins de moyen */
		 if(request.getParameter("activite").equals("non")) {
			 p2 = r->r.getActivite()<=5; 
		 }
		 else {//sinon on ne filtre pas les races d echiens selon ce critère
			 p2 = r->true;
		 }
		
		 /* si une personne n'est pas très sportif, on estime qu'elle aura du mal
		  * avec un chien très fort(pour le tenir, pour le soulever le cas écheant), 
		  * donc on ne garde que les race de chiens de moins de 20 kg maximum*/
		 if(request.getParameter("sport").equals("non")) {
			 p3 = r->r.getPoids_max()<20; 
		 }
		 else {//sinon on ne filtre pas les races d echiens selon ce critère
			 p3 = r->true;
		 }
		 
		 /* si une personne a des enfants on ne garde que les races de chiens 
		  * qui s'entendent bien avec les enfants */
		 if(request.getParameter("enfants").equals("oui")) {
			 p4 = r->r.getEntente_enfants()>5; 
		 }
		 else {//sinon on ne filtre pas les races d echiens selon ce critère
			 p4 = r->true;
		 }
		 
		 String animaux = request.getParameter("animaux");
		 
		 /* si une personne a des autres chiens ou des chats on ne garde que 
		  * les races de chiens qui s'entendent bien avec autres animaux */
		 if(animaux.equals("chien")) {
			 p5 = r->r.getEntente_autres_animaux()>6;
		 }
		 /* si une personne a des rongeurs on ne garde que 
		  * les races de chiens qui n'ont pas d'aptitude pour la chasse */
		 else if(animaux.equals("rongeurs")) {
			 p5 = r->!DBAptitudeChien.lienExiste(1, r.getCode_race());
		 }
		 else {//sinon on ne filtre pas les races d echiens selon ce critère
			 p5 = r->true;
		 }
		 
		 /* Si une personne a besoin d'une chien de garde, on ne garde que les
		  * chiens qui ont aptitudes pour la chasse */
		 if(request.getParameter("garde").equals("oui")) {
			 p6 = r->DBAptitudeChien.lienExiste(2, r.getCode_race());
		 }
		 else {//sinon on ne filtre pas les races d echiens selon ce critère
			 p6 = r->true;
		 }
		 
		 /* Si une personne veut voyager avec son chien en avion, on ne garde que
		  * les races dont le poids maximale est moins au égale à 8 kilos */
		 String voyages = request.getParameter("voyages");
		 if(voyages.equals("avion")) {
			 p7 = r->r.getPoids_max()<=8; 
		 }
		 else {//sinon on ne filtre pas les races d echiens selon ce critère
			 p7 = r->true;
		 }

		/* on  crée un stream parallelle à partir de liste de races de chiens et
		 * applique tous les filtres crées precedemment */
	    return races.parallelStream().filter(p1).filter(p2)
			  .filter(p3).filter(p4).filter(p5)
			  .filter(p6).filter(p7).collect(Collectors.toList());
	   }
	   else return null;
	 }
	 
	 public Map<String, Boolean> getErreur(){
		 /* on verifie que les champs du test ont été remplie correctement par l'utilisateur */
		 Map<String, Boolean> erreurs = new HashMap<>();
		 /* comme tous ces paramètres sont choisie à partir de liste select ou
		  * radio buttons, donc on ne verifie pas la valeur même, seulement le
		  * fait que le choix avait été effectué */
		 if(request.getParameter("activite")!=null) {
			 ACTIVITE = request.getParameter("activite");
		 }
		 else {
			 erreurs.put("activite_erreur", true);
		 }
		 if(request.getParameter("sport")!=null) {
			 SPORT = request.getParameter("sport");
		 }
		 else {
			 erreurs.put("sport_erreur", true);
		 }
		 if(request.getParameter("enfants")!=null) {
			 ENFANTS = request.getParameter("enfants");
		 }
		 else {
			 erreurs.put("enfants_erreur", true);
		 }
		 if(request.getParameter("garde")!=null) {
			 GARDE = request.getParameter("garde");
		 }
		 else {
			 erreurs.put("garde_erreur", true);
		 }
		 COMPLET = erreurs.isEmpty();
		 return erreurs;
	 }
	 
	 /* retourne true si le formulaire du test avait été remplie correctement */
	 public boolean isComplet() {
		 return COMPLET;
	 }
}