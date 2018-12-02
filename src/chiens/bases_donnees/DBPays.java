package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import chiens.beans.PaysBean;


public class DBPays {
	private static final String CODE_PAYS = "code_pays";
	private static final String NOM_PAYS = "nom_pays"; 
	private static final String TABLE = "pays";
    private static final DBCatalogue DBC = new DBCatalogue(TABLE);
    
    /*retourne true si le nom de pays, passé en paramètre, existe déjà dans le tableau "pays"*/
	public boolean nomExiste(String nom) throws Exception  {  
	      return DBC.xExiste(nom, NOM_PAYS);
	}

	/*ajoute un nom de pays(String passé en paramètre) dans le tableau "pays" des pays et
	 * retourne un code de pays généré automatiquement*/
	 public Integer ajouterPays(String nom_pays) {
	      return DBC.ajouterX(nom_pays, NOM_PAYS);
	 }
	 
	 /*supprime le pays, dont le code est passé en paramètre, du tableau "pays"*/
	 public static void deletePays(String code) {
		  DBC.deleteX(code, CODE_PAYS);
	 }
	 
	 /*retourne le nom de pays dont le code est passé en paramètre*/
	 public static String getNomDePays(Integer code) {
		   return DBC.getNomParCode(code, CODE_PAYS, NOM_PAYS);
	 }
	 
	 /*retourne une collection ArayList de type PaysBean representant tous
	  * les pays du tableau "pays"*/
	 public ArrayList<PaysBean> getPaysList(){
	    ArrayList<PaysBean> listPays = new ArrayList<PaysBean>();
		try(Connection conn=DBConnection.getConnection();
		 PreparedStatement prstm = conn.prepareStatement("select * from pays;");
		 ResultSet rs= prstm.executeQuery();){
		 while(rs.next()) { 
			 PaysBean pays = new PaysBean();
       	     pays.setNom_pays(rs.getString(2));
       	     pays.setCode_pays(Integer.parseInt(rs.getString(1)));
       	     listPays.add(pays);
		  }
		}
		catch (SQLException | NumberFormatException e){e.printStackTrace();} 
		catch (Exception e) {e.printStackTrace();}

	   return listPays;
	  }
	
	 /*retourne une collection ArayList de type PaysBean representant tous
	  * les pays du tableau "pays" sauf le pays dont le code est passé en paramètre*/
	 public ArrayList<PaysBean> getPaysListAutre(String code){
		    ArrayList<PaysBean> listPays = new ArrayList<PaysBean>();
		    Connection conn = null;  
			PreparedStatement prstm = null;
			ResultSet rs = null; 
			try {
			conn=DBConnection.getConnection();
			prstm = conn.prepareStatement("select * from pays where code_pays !=?;");
			prstm.setInt(1, Integer.parseInt(code));
			rs= prstm.executeQuery();
			 while(rs.next()) { 
				 PaysBean pays = new PaysBean();
	       	     pays.setNom_pays(rs.getString(2));
	       	     pays.setCode_pays(Integer.parseInt(rs.getString(1)));
	       	     listPays.add(pays);
			 }
			}
			catch(SQLException |NumberFormatException e){e.printStackTrace();} 
	        finally {
	            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
	            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
	            try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
	        }
		   return listPays;
		 }
	 
	 /*retourne une collection HashMap avec les codes de tous les pays existantes dans le 
	  *tableau "pays" comme clés et leurs noms comme valeurs*/
	 public static HashMap <Integer, String> getPaysMap(){
		    HashMap<Integer, String> listPays = new HashMap<Integer, String>();	
			try(Connection conn=DBConnection.getConnection();
			 PreparedStatement prstm = conn.prepareStatement("select * from pays;");
			 ResultSet rs= prstm.executeQuery();){
			 while(rs.next()) { 
				 Integer key = Integer.parseInt(rs.getString(1));
	       	     String value = rs.getString(2);
	       	     listPays.put(key, value);
			  }
			}
			catch (SQLException | NumberFormatException e){e.printStackTrace();} 
			catch (Exception e) {e.printStackTrace();}
		   return listPays;
		 }
	 
	 /* retourne une collection HashMap avec les codes de tous les pays du tableau "pays" comme
	  * clés et une chaine de caractère avec le noms de toutes les races provenantes de ce pays 
	  * separés par virgule et concaténés ensemble comme valeurs */
	 public static HashMap<Integer, String> getMapChiensPaysX(){
		 HashMap<Integer, String> races_pays = new HashMap<Integer, String>();
		
			try(Connection conn=DBConnection.getConnection();
			PreparedStatement prstm = conn.prepareStatement("select * from races_chiens order by pays_provenance;");
			ResultSet rs= prstm.executeQuery();){
			String key=null, value="";
	        while(rs.next()) { 
	        	if(key==null) { key=rs.getString(3); value=rs.getString(2);}
	        	else if(!key.equals(rs.getString(3))) {
	        		races_pays.put(Integer.parseInt(key), value);
	        		key=rs.getString(3);
	        		value=rs.getString(2);
	        	}
	        	else value+=", "+rs.getString(2);
	          }
	        races_pays.put(Integer.parseInt(key), value);
			}
			catch (SQLException | NumberFormatException e){e.printStackTrace();} 
			catch (Exception e) {e.printStackTrace();}
		  return races_pays;
		}
	 
}
