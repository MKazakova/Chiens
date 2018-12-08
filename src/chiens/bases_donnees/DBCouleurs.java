package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import chiens.beans.CouleurBean;

public class DBCouleurs {
  	private static final String CODE_COULEUR = "code_couleur";
  	private static final String NOM_COULEUR = "nom_couleur"; 
  	private static final String TABLE = "couleurs";
  	private static final DBCatalogue DBC = new DBCatalogue(TABLE);	
  
     /*retourne true si le nom de couleur passé en paramètre existe déjà dans la base
      * du tableau "couleurs"*/
	 public boolean couleurExiste(String couleur) throws Exception  {  
		return DBC.xExiste(couleur, NOM_COULEUR);
	 }

	 /*ajoute un nom de couleur passé en paramètre dans un tableau "couleurs"
	  * et retourne le clé généré automatiquement si l'enregistrement est reussi*/
	 public Integer ajouterCouleur(String nom_couleur) {
	      return DBC.ajouterX(nom_couleur, NOM_COULEUR);
	 }
	 
	 /*retourne une collection de CouleurBean representante tous les couleurs du tableau "couleurs"*/
	 public ArrayList<CouleurBean> getCouleurList(){
	    ArrayList<CouleurBean> listCouleurs = new ArrayList<CouleurBean>();	
		try(Connection conn=DBConnection.getConnection();
		PreparedStatement prstm = conn.prepareStatement("select * from couleurs;");
		ResultSet rs= prstm.executeQuery();){
		 while(rs.next()) { 
			 CouleurBean couleur = new CouleurBean();
			 couleur.setNom_couleur(rs.getString(2));
			 couleur.setCode_couleur(Integer.parseInt(rs.getString(1)));
       	     listCouleurs.add(couleur);
		 }
		}
		catch (SQLException |NumberFormatException e){ e.printStackTrace();} 
		catch (Exception e) {}

	   return listCouleurs;
	 }
	 
	 /*retourne le nom de couleur du tableau "couleurs" dont le code est passé en paramètre*/
	 public static String getNomParCode(Integer code) {
		  return DBC.getNomParCode(code, CODE_COULEUR, NOM_COULEUR);
	 }
	 
	 /*supprime une couleur du tableau "couleurs" dont le code est passé en paramètre*/
	 public static void deleteCouleur(String code) {
		  DBC.deleteX(code, CODE_COULEUR);
	 }
}
