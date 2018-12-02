package chiens.metier;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import chiens.bases_donnees.DBAptitudeChien;
import chiens.bases_donnees.DBCouleurChien;
import chiens.bases_donnees.DBPoilsChien;
import chiens.bases_donnees.DBRaceChien;
import chiens.beans.RaceChienBean;

public class EditionRaceChien {
	/*RaceChienBean va contenir l'information saisie par l'utilisateur avant qu'elle soit inscrite dans la base*/
	 private RaceChienBean raceChien = new RaceChienBean();
	 private Map<String, String> erreurs = new HashMap<String, String>();	
	 private String filePath;
	 private boolean isMultipart;
	 private int maxFileSize = 250* 1024;
	 private int maxMemSize = 4 * 1024;
	 private File file ;
	 private String fieldName;
	 private String fieldValeur;
	 private ArrayList<Integer> aptitudes = new ArrayList<Integer>();
     private ArrayList<Integer> couleurs = new ArrayList<Integer>();
     private ArrayList<Integer> poils = new ArrayList<Integer>();
     private DBRaceChien dbr= new DBRaceChien();
     private String code=null;
	 boolean succes=false;
	 
	    /* recoupère les changements dans une race de chien à partir de formulaire de type "multipart/form-data" 
	     * car le formulaire reçoit une photo d'un chien de race correspondante entre autres */ 
	public EditionRaceChien(HttpServletRequest request, String path) throws UnsupportedEncodingException{

		      request.setCharacterEncoding("UTF-8");
		      filePath=path;
		      isMultipart = ServletFileUpload.isMultipartContent(request);
		      /*verifie si les données de request sont bien de type MultipartContent*/
		      if( !isMultipart ) {
		         return;
		      }
		      String imageName="";
		      DiskFileItemFactory factory = new DiskFileItemFactory();
		      
		      factory.setSizeThreshold(maxMemSize);
		   
		      factory.setRepository(new File("c:\\temp"));

		      ServletFileUpload upload = new ServletFileUpload(factory);
		   
		      upload.setSizeMax( maxFileSize );
		      try { 
		         List fileItems = upload.parseRequest(request);
		         /* traite les éléments du formulaire*/
		         Iterator i = fileItems.iterator();
		         while ( i.hasNext () ) {
		            FileItem fi = (FileItem)i.next();
		            /* d'abord on verifie si un élément donné est une photo, ou un champs de formulaire de type String*/
		            if ( fi.isFormField () ) {//si ce n'est pas une photo
			               fieldName = fi.getFieldName();//on prend le nom du champ pour le traiter en consequence
			               fieldValeur =new String(fi.getString().getBytes(), "UTF-8");//on reçoit une valeur du champ
			               if(fieldName.equals("nomRace")) {//un nom de race 
			            	   if(fieldValeur.isEmpty()||fieldValeur==null||!verifNomRace(fieldValeur)) {// on verifie que le nom n'est pas vide
			            		   erreurs.put("nom_race", "Le nom de la race ne doit pas être vide");
			            	   }//si tout va bien, on initialise le champs "nom_race" de RaceChienBean 
			            	   else raceChien.setNom_race(fieldValeur);
			               }
			               else if(fieldName.equals("paysProvenance")) {//le champs nom de pays de provenance
			            	   if(!fieldValeur.equals("rien")) {//si le pays était choisie dans "select"
			            		   raceChien.setPays_provenance(Integer.parseInt(fieldValeur));     
			            	   }//sinon un message d'erreur 
			            	   else erreurs.put("pays", "Veillez choisir un pays");
			               }
			               else if(fieldName.equals("element")) { //le code de la race 
			            	   raceChien.setCode_race(Integer.parseInt(fieldValeur)); 
			                   code = fieldValeur;
			               }
			               else if(fieldName.equals("Aptitude")) {//il y une valeur par default dans "select", donc pas de verification
			            	   aptitudes.add(Integer.parseInt(fieldValeur));
			               }
			               else if(fieldName.equals("facilite_education")) { //il y une valeur par default dans "select", donc pas de verification 
			            	   raceChien.setFacilite_education(Integer.parseInt(fieldValeur));
			               }
			               else if(fieldName.equals("apte_garde")) {//il y une valeur par default dans "select", donc pas de verification  
			            	   raceChien.setApte_garde(Integer.parseInt(fieldValeur));
			               }
			               else if(fieldName.equals("entente_enfants")) {//il y une valeur par default dans "select", donc pas de verification  
			            	   raceChien.setEntente_enfants(Integer.parseInt(fieldValeur));
			               }
			               else if(fieldName.equals("entente_animaux")) {//il y une valeur par default dans "select", donc pas de verification  
			            	   raceChien.setEntente_autres_animaux(Integer.parseInt(fieldValeur));
			               }
			               else if(fieldName.equals("activite")) {//il y une valeur par default dans "select", donc pas de verification 
			            	   raceChien.setActivite(Integer.parseInt(fieldValeur));
			               }
			               else if(fieldName.equals("typePoils")) {//il y une valeur par default dans "select", donc pas de verification 
			            	   poils.add(Integer.parseInt(fieldValeur));
			               }
			               else if(fieldName.equals("couleurChien")) {//il y une valeur par default dans "select", donc pas de verification 
			            	   couleurs.add(Integer.parseInt(fieldValeur));
			               }
			               else if(fieldName.equals("imageRace")) {//que une nouvelle image soit chargé ou pas, le nom de l'image reste le même
			            	   raceChien.setImage(fieldValeur);
			               }
			               else if(fieldName.equals("description")) {//description n'est pas obligatoire et n'est pas contrôlé, donc pas de verification 
			            	   raceChien.setDescription(fieldValeur);
			               }
			               else if(fieldName.equals("poidsMin")) { 
			            	           String kilos;
			            	           if(fieldValeur.isEmpty()||fieldValeur==null) {//verifie si le champs n'est pas vide
			            	        	   erreurs.put("poids", "Le champ de poids ne doit pas être vide");
			            	           }
			            	           else {//cela permet à l'utilisateur d'inserer soit "," soit "." pour separer les décimales
			            	               kilos=fieldValeur.replace(',', '.');
			            	           try {
			            	        	  raceChien.setPoids_min(Double.parseDouble(kilos)); 
			            	           }
			            	           catch(NumberFormatException e) {/*si le nombre n'est pas de forme correcte*/
			            	        	   erreurs.put("poids", "Le format de poids minimal n'est pas bon");
			            	           }
			            	           }
			               }/*les mêmes verifications pour le poids maximale*/
			               else if(fieldName.equals("poidsMax")){
	            	           String kilos;
	            	           if(fieldValeur.isEmpty()||fieldValeur==null) {
	            	        	   erreurs.put("poids", "Le champ de poids ne doit pas être vide");
	            	           }
	            	           else {
	            	        	   kilos=fieldValeur.replace(',', '.');
	            	        		   
	            	        	   try {
	            	        			   	raceChien.setPoids_max(Double.parseDouble(kilos)); 
	            	        	   }
	            	        	   catch(NumberFormatException e) {
	            	        			   erreurs.put("poids", "Le format de poids maximal n'est pas bon");
	            	        	   }
	            	           }
	                       }
			               else if(fieldName.equals("tailleMin")){//s'il s'agit du champs de taille
			            	   //on verifie que le champ n'est pas vide
	            	           if(fieldValeur.isEmpty()||fieldValeur==null) {
	            	        	   erreurs.put("taille", "Le champ de taille ne doit pas être vide");
	            	           }
	            	           else {
	            	        	   try {
	            	        		 //analyse une chaîne de caractère fournie et initialyse un champ taille_min
	            	        		   raceChien.setTaille_min(Integer.parseInt(fieldValeur)); 
	            	        	   }
	            	        	   catch(NumberFormatException e) {
	            	        		   erreurs.put("taille", "Le format de taille minimal n'est pas bon");
	            	        	   }
	            	           }
	                       }/*les verifications similazires concernant la taille maximale*/
			               else if(fieldName.equals("tailleMax")){
			            	   if(fieldValeur.isEmpty()||fieldValeur==null) {
			            		   erreurs.put("taille", "Le champ de taille ne doit pas être vide");
			            	   }
	            	           else {
	            	        	   try {
	            	        		   raceChien.setTaille_max(Integer.parseInt(fieldValeur)); 
	            	        	   }
	            	        	   catch(NumberFormatException e) {
	            	        		   erreurs.put("taille", "Le format de taille maximal n'est pas bon");
	            	        	   }
	            	           }
			               }	
			        }
		            else{
		               String contentType = fi.getContentType();
		               if(contentType.startsWith("image")) {
			            	   if(raceChien.getNom_race().length()<=25) {
			            		   imageName=raceChien.getNom_race();
			            	   }
		            		   else {
		            			   imageName=raceChien.getNom_race().substring(0, 25);
		            		   }
			            	   
			            	   if(contentType.endsWith("png")) {
			            		   imageName=imageName+".png";
			                       file = new File( filePath + "\\"+imageName);	
			            	   }
			            	   else if(contentType.endsWith("jpeg")) {
			            		   imageName=imageName+".jpg";
			                       file = new File( filePath + "\\"+imageName);		   
			            	   }
		               raceChien.setImage(imageName);
		               fi.write(file);
		               }
		            }
		         
		         }
		      }
		      catch(Exception ex) {}
		      		if(erreurs.isEmpty()) {
		      			/*si aucune erreur n'était généré lors de création de bean, 
			      		 * on enregistre cette race dans une base
			      		 */
		      			try {
		      				/*variable qui signale que la race avait été ajouter avec succes*/
		      				succes=dbr.editRace(raceChien);
		      				if(succes) {
		      					/*on supprime les liens qui existe actuellement entre une race de chiens
		      					 * et couleurs, aptitudes et types de poils*/
		      					DBCouleurChien.deleteCouleurParCodeChien(code);
		      					DBAptitudeChien.deleteAptitudeChien(code);
		      					DBPoilsChien.deleteTypePoilsChien(code);
		      					/*on ajoute les liens liant la nouvelle race avec les couleurs, aptitudes
			      				 * et types de poils correspondants*/
		      					CouleurChien.ajouterPlusieursCouleurs(couleurs, raceChien.getCode_race());
		      					AptitudeChien.ajouterPlusieursAptitudes(aptitudes, raceChien.getCode_race());
		      					TypePoilsChien.ajouterPlusieursTypePoils(poils, raceChien.getCode_race());
		      				}
		      			}
		      			catch(Exception e) {}
		      			}
	}
	
	/* verifie que le nom de race ne contient pas de chiffres et de longueur coherente*/
	public boolean verifNomRace(String nom) {
		return nom.matches("[^0-9]{2,30}");
    }

	/*retourne true si les changements dans la race des chien ont reussi*/
	public boolean isSucces() {
		return succes;
	}

	/*retourne  le code de race*/
	public String getCode() {
		return code;
	}

}
