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

import chiens.bases_donnees.DBRaceChien;
import chiens.beans.RaceChienBean;

public class CreationRaceChien {
	/*RaceChienBean à partir duquel la nouvelle race est crée pour être ajouter dans la base*/
	 private RaceChienBean raceChien = new RaceChienBean();
	 private Map<String, String> erreurs = new HashMap<String, String>();
	 boolean succes=false;
	 private boolean isMultipart;
	 private String filePath;
	 private int maxFileSize = 150 * 1024;
	 private int maxMemSize = 4 * 1024;
	 private File file ;
	 private String fieldName;
	 private String fieldValeur;
     private ArrayList<Integer> aptitudes = new ArrayList<Integer>();
     private ArrayList<Integer> couleurs = new ArrayList<Integer>();
     private ArrayList<Integer> poils = new ArrayList<Integer>();
     private DBRaceChien dbr= new DBRaceChien();
     private Integer code=null;
     
    /* crée une race de chien à partir de formulaire de type "multipart/form-data" car le formulaire reçoit
     * une photo d'un chien de race correspondante entre autres */
	public CreationRaceChien(HttpServletRequest request, String path) throws UnsupportedEncodingException{
		
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
		         Iterator i = fileItems.iterator();
		         while ( i.hasNext () ) {
		            FileItem fi = (FileItem)i.next();
		            /* d'abord on verifie si un élément donné est une photo, ou un champs de formulaire de type String*/
		            if ( fi.isFormField () ) {//si ce n'est pas une photo
			               fieldName = fi.getFieldName();//on prend le nom du champ pour le traiter en consequence
			               fieldValeur = fi.getString();//on reçoit une valeur du champ
			               if(fieldName.equals("nomRace")) {//un nom de race 
			            	   if(fieldValeur.isEmpty()||fieldValeur==null) {// on verifie que le nom n'est pas vide
			            		   erreurs.put("nom_race", "Le nom de la race ne doit pas être vide");
			            	   }
			            	   else if(!verifNomRace(fieldValeur)) {//on verifie le format
			            		   erreurs.put("nom_race", "Le nom de la race n'est pas correct");
			            	   }
			            	   else if(!dbr.raceExiste(fieldValeur)) {//si ce nom n'existe pas encore dans la base
			            		   raceChien.setNom_race(fieldValeur);//si tout va bien on initialise le champ nom_race de RaceChienBEan
			            	   }
			            	   else {
			            		   erreurs.put("nom_race", fieldValeur+" existe déjà dans la base");
			            	   }
			               }
			               else if(fieldName.equals("paysProvenance")) {//le champs nom de pays de provenance
			            	   if(!fieldValeur.equals("rien")) {//si le pays était choisie dans "select"
			            		   raceChien.setPays_provenance(Integer.parseInt(fieldValeur));     
			            	   }//sinon un message d'erreur
			            	   else erreurs.put("pays", "Veillez coisir un pays");
			               }
			               else if(fieldName.equals("Aptitude")) {//il y une valeur par default dans "select", donc pas de verification
			            	   aptitudes.add(Integer.parseInt(fieldValeur));//on l'ajoute dans la collection d'aptitudes
			               }
			               else if(fieldName.equals("facilite_education")) {//il y une valeur par default dans "select", donc pas de verification
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
			               else if(fieldName.equals("typePoils")) {//le choix de type poils n'est pas obligatoire et se fait à partir du "select", donc pas de verification
			            	   poils.add(Integer.parseInt(fieldValeur));//on l'ajoute dans la collection de types de poils
			               }
			               else if(fieldName.equals("couleurChien")) {//le choix de couleur n'est pas obligatoire et se fait à partir du "select", donc pas de verification
			            	   couleurs.add(Integer.parseInt(fieldValeur));//on l'ajoute dans la collection de couleurs
			               }
			               else if(fieldName.equals("description")) {//description n'est pas obligatoire et n'est pas contrôlé, donc pas de verification
			            	   raceChien.setDescription(fieldValeur);
			               }
			               else if(fieldName.equals("poidsMin")) {
			            	           String kilos;
			            	           if(fieldValeur.isEmpty()||fieldValeur==null) {//si le champs n'est pas vide
			            	        	   erreurs.put("poids", "Le champ de poids ne doit pas être vide");
			            	           }
			            	           else {//cela permet à l'utilisateur d'inserer soit "," soit "." pour separer les décimales
			            	        	   kilos=fieldValeur.replace(',', '.');
			            	        	   try {
			            	        		   raceChien.setPoids_min(Double.parseDouble(kilos)); 
			            	        	   }
			            	        	   catch(NumberFormatException e) {
			            	        	   /*si le nombre n'est pas de forme correcte*/
			            	        		   erreurs.put("poids", "Le format de poids minimal n'est pas bon");
			            	        	   }
			            	           }
			               }//les vérifications similaires du poids maximale
			               else if(fieldName.equals("poidsMax")){
	            	           String kilos;
	            	           if(fieldValeur.isEmpty()||fieldValeur==null) erreurs.put("poids", "Le champ de poids ne doit pas être vide");
	            	           else {
	            	        		   kilos=fieldValeur.replace(',', '.');
	            	        		   try {
	            	        			   double kilomax = Double.parseDouble(kilos);
	            	        			   /*la verification si le poids maximale n'est pas plus petit que le poids minimale*/
	            	        			   if(raceChien.getPoids_min()>kilomax) {
	            	        				   erreurs.put("poids", "Le poids maximum ne peut pas être moins que poids minimum");
	            	        			   }
	            	        			   else raceChien.setPoids_max(kilomax); 
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
	                       }
			               else if(fieldName.equals("tailleMax")){//les manipulations similaire au champs de taille minimale
			            	   if(fieldValeur.isEmpty()||fieldValeur==null) erreurs.put("taille", "Le champ de taille ne doit pas être vide");
	            	           else {
	            	        	   try {
	            	        		   int taillemax = Integer.parseInt(fieldValeur);
	            	        		   	/*on verifie si la taille maximale n'est pas plus petite que la taille minimale*/
	            	        	       if(raceChien.getTaille_min()>taillemax) {
	            	        	    	 erreurs.put("taille", "La taille maximal ne doit pas être moins que la taille minimal");
	            	        	       }
	            	        	       else raceChien.setTaille_max(taillemax); 
	            	        	     
	            	               }
	            	               catch(NumberFormatException e) {
	            	            	   erreurs.put("taille", "Le format de taille maximal n'est pas bon");
	            	               }
	                           }
		                   }
			        }
		            else{//si un élément traité est une photo d'une race
		               String contentType = fi.getContentType();
		               //on verifie que le type de fichier envoyé est bien une photo
		               if(contentType.startsWith("image")) {
		            	   /*ici on crée un nom pour le fichier image et si le nom de race est trop longue
		            	    * on ne prends que 25 premières caractères*/
		            	   if(raceChien.getNom_race().length()<=25) {
		            		   imageName=raceChien.getNom_race();
		            	   }
	            		   else {
	            			   imageName=raceChien.getNom_race().substring(0, 25);
	            		   }
		            	   
		            	   //on verifie le type de l'image pour l'enregistrer son nom correctement dans la base
		            	   if(contentType.endsWith("png")) {
		            		   imageName=imageName+".png";
		                       file = new File( filePath + "\\"+imageName);	
		            	   }
		            	   else if(contentType.endsWith("jpeg")) {
		            		   imageName=imageName+".jpg";
		                       file = new File( filePath + "\\"+imageName);		   
		            	   }
		               /* on initialise le champ image avec le nom de l'image qu'on vient de créer*/
		               raceChien.setImage(imageName);
		               /* et finalement on enregistre un image dans un fichier 
		                * correspondant ou on garde tous les images des chiens de races differents
		                */
		               fi.write(file);

		               }
		            }
		         
		         }
		      }
		      catch(Exception ex) {
		    	  ex.printStackTrace();
		      }
		      	if(erreurs.isEmpty()) {
		      		/*si aucune erreur n'était généré lors de création de bean, 
		      		 * on enregistre cette race dans une base
		      		 */
		      		try {
		      			/*si l'enregistrement est reussie on recoupere un code de race 
		      			 * généré automatiquement dans le tableau "race_chiens"*/
		      			code = dbr.addRace(raceChien);
		      			if(code!=null) {
		      				raceChien.setCode_race(code);
		      				/*variable qui signale que la race avait été ajouter avec succes*/
		      				succes = true;
		      				/*on ajoute les liens liant la nouvelle race avec les couleurs, aptitudes
		      				 * et types de poils correspondants*/
		      				CouleurChien.ajouterPlusieursCouleurs(couleurs, code);
		      				AptitudeChien.ajouterPlusieursAptitudes(aptitudes, code);
		      				TypePoilsChien.ajouterPlusieursTypePoils(poils, code);
		      			}
		      		}
		      		catch(Exception e) {
		      			e.printStackTrace();
		      		}
	           }
	}
	
	/* retourne true si une race de chiens avait été crée avec succes*/
	public boolean isSucces() {
		return succes;
	}
	
	/* verifie que le nom de race ne contient pas de chiffres et de longueur coherente*/
    public boolean verifNomRace(String nom) {
		return nom.matches("[^0-9]{2,30}");
    }

    /*retourne RaceChienBean contenant les données de nouvelle race qui vient d'être crée*/
	public RaceChienBean getRaceChien() {
		return raceChien;
	}

	/*retourne une collection des messages d'erreurs(si pas d'erreur retourne un Map vide*/
	public Map<String, String> getErreurs() {
		return erreurs;
	}

	/*retourne une collection d'aptitudes liées à une nouvelle race*/
	public ArrayList<Integer> getAptitudes() {
		return aptitudes;
	}

	/*retourne une collection de couleurs liées à une nouvelle race*/
	public ArrayList<Integer> getCouleurs() {
		return couleurs;
	}
	
	/*retourne une collection de types de poils liées à une nouvelle race*/
	public ArrayList<Integer> getPoils() {
		return poils;
	} 
	
	/*retourne le code généré d'une nouvelle race*/
	public Integer getCode() {
		return code;
	}
}
