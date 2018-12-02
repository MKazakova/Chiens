package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import chiens.beans.TypePoilsBean;

public class DBPoilsChien {
		
	private static final String CODE_RACE = "code_race";
	private static final String CODE_TYPE_POILS = "code_type_poils"; 
	private static final String TABLE = "type_poils_chien";
	private static final DBCatalogue DBC = new DBCatalogue(TABLE, CODE_RACE, CODE_TYPE_POILS);
	
	/*retourne true s'il existe un lien entre un code de type poils et un code de
	 * race passés en paramètres dans le tableau "type_poils_chien"*/
	public static boolean lienExiste(Integer codeTypePoils, Integer codeRace){  
	   try {
		return DBC.lienExiste(codeRace, codeTypePoils);
	   } 
	   catch (Exception e) {}
	return false;
	}
	
	/*ajoute un enregistrement qui lie un code de race et un code de type de poils
	 * dans le tableau "type_poils_chien" */
	public static boolean addlien(Integer codeTypePoils, Integer codeRace) {
	   return DBC.addlien(codeRace, codeTypePoils);
	}
	
	/*supprime tous les enregistrements du tableau "type_poils_chien" où
	 * le champs code_type_poils est égale à un code passé en paramètre*/
	public static void deleteTypePoilsParCodeTypePoils(String code) {
		DBC.deleteX(code, CODE_TYPE_POILS);
    }
	
	/*retourne une collection ArrayList avec les noms de types de poils qui sont liés avec le code
	 * de race passé en paramètre dans le tableau "type_poils_chien"*/
	public static ArrayList<String> getTypePoilsRace(Integer codeRace) throws Exception  {  
		Connection conn = null;  
		PreparedStatement prstm = null;	
		ResultSet rs = null;
		ArrayList<String> listChienTypePoils = new ArrayList<String>();
		try {
		 conn=DBConnection.getConnection();
		 prstm = conn.prepareStatement("select type_poils.description_type_poils from type_poils join "
				+ "type_poils_chien on type_poils_chien.code_type_poils=type_poils.code_type_poils where type_poils_chien.code_race=?;");
		 prstm.setInt(1, codeRace);
		 rs= prstm.executeQuery();
         while(rs.next()) { 
           String couleur;
           couleur=rs.getString(1);
           listChienTypePoils.add(couleur);
          }
		}
		catch(SQLException |NumberFormatException e){e.printStackTrace();} 
        finally {
            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
            try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
        }
	 return listChienTypePoils;
	}
	
	/*supprime tous les enregistrements du tableau "type_poils_chien" où
	 * le champs "code_race" est égale à un code passé en paramètre*/
	public static void deleteTypePoilsChien(String code) {
		DBC.deleteX(code, CODE_RACE);
    }
	
	/*retourne une collection de types de poils(de type TypePoilsBean) existants dans le tableau
	 * "type_poils" qui ne sont pas liés à une race, dont le code est passé en paramètre, dans le
	 * tableau "type_poils_chien" */
	public static ArrayList<TypePoilsBean> getListTypePoilsAbsents(Integer codeRace){
		Connection conn = null;  
		PreparedStatement prstm = null;	
		ResultSet rs = null;
		ArrayList<TypePoilsBean> listTypePoils = new ArrayList<TypePoilsBean>();
		try {
			conn=DBConnection.getConnection();
			prstm = conn.prepareStatement("select * from type_poils where code_type_poils not in(select type_poils.code_type_poils "
					+ "from type_poils join type_poils_chien on type_poils_chien.code_type_poils=type_poils.code_type_poils "
					+ "where type_poils_chien.code_race=?);");
			prstm.setInt(1, codeRace);
			rs= prstm.executeQuery();
	        while(rs.next()) {
	           TypePoilsBean type_poils = new TypePoilsBean();
	           type_poils.setCode_type_poils(Integer.parseInt(rs.getString(1)));
	           type_poils.setDescription_type_poils(rs.getString(2));
	           listTypePoils.add(type_poils);
	          }
			}
		catch(SQLException |NumberFormatException e){e.printStackTrace();} 
        finally {
            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
            try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
        }
		return listTypePoils;
	 }	
	
	/*retourne une collection de types de poils(de type TypePoilsBean) existants dans le tableau
	 * "type_poils" qui sont liés à une race, dont le code est passé en paramètre, dans le
	 * tableau "type_poils_chien" */
	 public static ArrayList<TypePoilsBean> getListTypePoilsPresents(Integer codeRace){
		Connection conn = null;  
		PreparedStatement prstm = null;	
		ResultSet rs = null;
		ArrayList<TypePoilsBean> listTypePoils = new ArrayList<TypePoilsBean>();
		try {
			conn=DBConnection.getConnection();
			prstm = conn.prepareStatement("select * from type_poils join "
					+ "type_poils_chien on type_poils_chien.code_type_poils=type_poils.code_type_poils where type_poils_chien.code_race=?;");
			prstm.setInt(1, codeRace);
			rs= prstm.executeQuery();
	        while(rs.next()) { 
	           TypePoilsBean type_poils = new TypePoilsBean();
	           type_poils.setCode_type_poils(Integer.parseInt(rs.getString(1)));
	           type_poils.setDescription_type_poils(rs.getString(2));
	           listTypePoils.add(type_poils);
	          }
		 }
		catch(SQLException |NumberFormatException e){e.printStackTrace();} 
        finally {
            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
            try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
        }
		return listTypePoils;
	 }	
	
	 /*retourne une collection Map de tous les codes de types de poils de chiens existants dans le tableau
	  * type_poils_chien comme clés et les noms de toutes les races de chiens dont les codes sont liés à un
	  * type de poils données dans le tableau type_poils_chien concatenés ensemble comme valeurs */
	public static HashMap<Integer, String> getMapChienstypePoilsX() {
			HashMap<Integer, String> typepoils = new HashMap<Integer, String>();		
			try(Connection conn=DBConnection.getConnection();
			 PreparedStatement prstm = conn.prepareStatement("select * from type_poils_chien join races_chiens on "
					+ "type_poils_chien.code_race=races_chiens.code_race order by code_type_poils;");
			 ResultSet rs= prstm.executeQuery();){
			 String key=null, value="";
	         while(rs.next()) { 
	        	if(key==null) { key=rs.getString(1); value=rs.getString(4);}
	        	else if(!key.equals(rs.getString(1))) {
	        		typepoils.put(Integer.parseInt(key), value);
	        		key=rs.getString(1);
	        		value=rs.getString(4);
	        	}
	        	else value+=", "+rs.getString(4);
	          }
	         typepoils.put(Integer.parseInt(key), value);
			}
			catch(SQLException |NumberFormatException e){e.printStackTrace();} 
			catch (Exception e) {e.printStackTrace();}
		  return typepoils;
	}
	


}
