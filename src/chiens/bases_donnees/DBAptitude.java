package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import chiens.beans.AptitudeBean;

public class DBAptitude {
	
  private static final String CODE = "code_aptitude";
  private static final String NOM = "nom_aptitude"; 
  private static final String TABLE = "aptitude";
  private static final DBCatalogue DBC = new DBCatalogue(TABLE);/*une class utilitaire avec les methodes 
  correspondants � des op�rations les plus courants de manipulation de bases de donnn�es dans le projet*/
  
     /*return true si un nom donn� existe d�j� dans le tableau 'aptitude'*/
	 public static boolean aptitudeExiste(String aptitude) throws Exception  {  
	      return DBC.xExiste(aptitude, NOM);
	 }

	 /*ajoute une nouvelle aptitude dans le tableau 'aptitude'*
	  *  et return le cl� primaire g�n�r� automatiquement*/
	 public static Integer ajouterAptitude(String nom_aptitude) {
		 return DBC.ajouterX(nom_aptitude, NOM);
	 }
	 
	 /*prend un code d'aptitude en tant qqu'argument et supprime une*
	  * aptitude correspondante */
	 public static void deleteAptitude(String code) {
		 DBC.deleteX(code, CODE);
     }
	 
	 /*prend un code d'aptitude et returne un nom d'aptitude en tant que String*/
	 public static String getNomParCode(Integer code) {
		  return DBC.getNomParCode(code, CODE, NOM);
	 }
	 
	 /*retourne une collection ArrayList de type <AptitudeBean> contenant *
	  * toutes les aptitudes existants dans la base*/
	 public static ArrayList<AptitudeBean> getAptitudeList(){
	     ArrayList<AptitudeBean> listAptitude = new ArrayList<AptitudeBean>();
		 try (Connection conn=DBConnection.getConnection();
		 PreparedStatement prstm = conn.prepareStatement("select * from aptitude;");
		 ResultSet rs= prstm.executeQuery();){
		 
		 while(rs.next()) { 
			 AptitudeBean aptitude = new AptitudeBean();
       	     aptitude.setNom_aptitude(rs.getString(2));
       	     aptitude.setCode_aptitude(Integer.parseInt(rs.getString(1)));
       	     listAptitude.add(aptitude);
		  }
		 }
		 catch (SQLException sqlEx) {sqlEx.printStackTrace();} 
		 catch(NumberFormatException e){e.printStackTrace();} 
		 catch (Exception e) {e.printStackTrace();}

	     return listAptitude;
	 }
	 
}

