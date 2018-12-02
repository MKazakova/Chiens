package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import chiens.beans.CouleurBean;

public class DBCouleurChien {
	
	private static ResultSet rs;
	private static final String CODE_RACE = "code_race";
	private static final String CODE_COULEUR = "code_couleur"; 
	private static final String TABLE = "couleur_de_chien";
	private static final DBCatalogue DBC = new DBCatalogue(TABLE, CODE_RACE, CODE_COULEUR);
	
	/*retourne true s'il y a un enregistrement dans le tableau "couleur_de_chien"
	 *liant un code de couleur passé en paramètre avec un code de race passé en paramètre*/
	public static boolean lienExiste(Integer codeCouleur, Integer codeRace){  
	  try {
		return DBC.lienExiste(codeRace, codeCouleur);
	  } 
	  catch (Exception e) {}
	return false;
	}
	
	/*ajoute un enregistrement dans le tableau "couleur_de_chien"
	 *liant un code de couleur passé en paramètre avec un code de race passé en paramètre*/
	public static boolean addlien(Integer codeCouleur, Integer codeRace) {
	  return DBC.addlien(codeRace, codeCouleur);
	}
	
	/*retourne ArrayList de noms de couleurs(en String) liés à une race, donc le code est passé en paramètre*/
	public static ArrayList<String> getCouleursDeRace(Integer codeRace) throws Exception  {  
	  Connection conn = null;  
	  PreparedStatement prstm = null;	
	  ArrayList<String> listChienCouleurs = new ArrayList<String>();
	  
	  try {
		conn=DBConnection.getConnection();
		prstm = conn.prepareStatement("select couleurs.nom_couleur from couleurs join "
				+ "couleur_de_chien on couleur_de_chien.code_couleur=couleurs.code_couleur where couleur_de_chien.code_race=?;");
		prstm.setInt(1, codeRace);
		rs= prstm.executeQuery();
		
        while(rs.next()) { 
           String couleur;
           couleur=rs.getString(1);
           listChienCouleurs.add(couleur);
          }
        
	   }
	   catch(SQLException e) {e.printStackTrace();} 
       catch(NumberFormatException e){e.printStackTrace();}
       finally {
            try { conn.close(); } catch(SQLException se)  {se.printStackTrace();}
            try { prstm.close();} catch(SQLException se)  {se.printStackTrace();}
            try { rs.close();   } catch(SQLException se)  {se.printStackTrace();}
       }
	 return listChienCouleurs;
	}
	
	/*supprime les enregistrements liants une code de race passé en paramètre avec les codes 
	 * de couleurs dans le tableau "couleur_de_chien"*/
	public static void deleteCouleurParCodeChien(String code) {
		DBC.deleteX(code, CODE_RACE);
    }
	
	/*supprime les enregistrements liants une code de couleur passé en paramètre avec les codes 
	 * de races dans le tableau "couleur_de_chien"*/
	public static void deleteCouleurParCodeCouleur(String code) {
		DBC.deleteX(code, CODE_COULEUR);
    }
	
	/*retourne une collection(ArrayList) de CouleurBean correspondants aux couleurs qui
	 * ne sont pas liées à un code de race de chien passé en paramètre dans le tableau "couleur_de_chien"*/
	public static ArrayList<CouleurBean> getListCouleursAbsents(Integer codeRace){
		Connection conn = null;  
		PreparedStatement prstm = null;	
		ArrayList<CouleurBean> listCouleur = new ArrayList<CouleurBean>();
		try {
			conn=DBConnection.getConnection();
			prstm = conn.prepareStatement("select * from couleurs where code_couleur not in(select couleurs.code_couleur "
					+ "from couleurs join couleur_de_chien on couleur_de_chien.code_couleur=couleurs.code_couleur "
					+ "where couleur_de_chien.code_race=?);");
			prstm.setInt(1, codeRace);
			rs= prstm.executeQuery();
	        while(rs.next()) { 
	           CouleurBean couleur = new CouleurBean();
	           couleur.setCode_couleur(Integer.parseInt(rs.getString(1)));
	           couleur.setNom_couleur(rs.getString(2));
	           listCouleur.add(couleur);
	          }
			}
		 catch(SQLException e) {e.printStackTrace();} 
	     catch(NumberFormatException e){e.printStackTrace();} 
		 catch (Exception e) {e.printStackTrace();}
	     finally {
	            try { conn.close(); } catch(SQLException se)  {se.printStackTrace();}
	            try { prstm.close();} catch(SQLException se)  {se.printStackTrace();}
	            try { rs.close();   } catch(SQLException se)  {se.printStackTrace();}
	        }
		return listCouleur;
	 }	
	
	/*retourne une collection(ArrayList) de CouleurBean correspondants aux couleurs qui
	 * sont liées à un code de race de chien passé en paramètre dans le tableau "couleur_de_chien"*/
	public static ArrayList<CouleurBean> getListCouleursPresents(Integer codeRace){
		Connection conn = null;  
		PreparedStatement prstm = null;	
		ArrayList<CouleurBean> listCouleur = new ArrayList<CouleurBean>();
		try {
			conn=DBConnection.getConnection();
			prstm = conn.prepareStatement("select * from couleurs join "
					+ "couleur_de_chien on couleur_de_chien.code_couleur=couleurs.code_couleur where couleur_de_chien.code_race=?;");
			prstm.setInt(1, codeRace);
			rs= prstm.executeQuery();
	        while(rs.next()) { 
	           CouleurBean couleur = new CouleurBean();
	           couleur.setCode_couleur(Integer.parseInt(rs.getString(1)));
	           couleur.setNom_couleur(rs.getString(2));
	           listCouleur.add(couleur);
	          }
		 }
		 catch(SQLException e) {e.printStackTrace();} 
	     catch(NumberFormatException e){e.printStackTrace();} 
		 catch (Exception e) {e.printStackTrace();}
	     finally {
	            try { conn.close(); } catch(SQLException se)  {se.printStackTrace();}
	            try { prstm.close();} catch(SQLException se)  {se.printStackTrace();}
	            try { rs.close();   } catch(SQLException se)  {se.printStackTrace();}
	     }
		return listCouleur;
	 }	
	
	/*retourne une map ayant les codes de couleurs comme les clés et les String de noms de races de chiens
	 * dont les codes sont liés à cette couleur dans le tableau "couleur_de_chien" comme valeurs*/
	public static HashMap<Integer, String> getMapChiensDeCouleurX(){
		HashMap<Integer, String> couleurs = new HashMap<Integer, String>();	
		try(Connection conn=DBConnection.getConnection();
		 PreparedStatement prstm = conn.prepareStatement("select * from couleur_de_chien join races_chiens on "
				+ "couleur_de_chien.code_race=races_chiens.code_race order by code_couleur;");
		 ResultSet rs= prstm.executeQuery();){
		 String key=null, value="";
         while(rs.next()) { 
        	if(key==null) { key=rs.getString(2); value=rs.getString(4);}
        	else if(!key.equals(rs.getString(2))) {
        		couleurs.put(Integer.parseInt(key), value);
        		key=rs.getString(2);
        		value=rs.getString(4);
        	}
        	else value+=", "+rs.getString(4);
          }
          couleurs.put(Integer.parseInt(key), value);
		  }
		 catch(SQLException |NumberFormatException e){e.printStackTrace();} 
		 catch (Exception e) {e.printStackTrace();}
	    return couleurs;
	}
}
