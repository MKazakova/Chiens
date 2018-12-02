package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import chiens.beans.AptitudeBean;

public class DBAptitudeChien {
	
	  private static final String CODE_APT = "code_aptitude";
	  private static final String CODE_RC = "code_race"; 
	  private static final String TABLE = "chien_aptitude";
	  private static final DBCatalogue DBC = new DBCatalogue(TABLE, CODE_APT, CODE_RC);/*une class utilitaire avec les methodes 
	  correspondants � des op�rations les plus courants de manipulation de bases de donnn�es dans le projet*/
	  
	  /*return true si une race de code pass� en param�tre est li� dans un tableau 'chien_aptitude'*
	   * � une aptitude de code qui est aussi pass� en parametre*/
	  public static boolean lienExiste(Integer codeAptitude, Integer codeRace) { 
			try {
				return DBC.lienExiste(codeAptitude, codeRace);
			} 
			catch (Exception e) {}
			return false;
	  }
	
	  /*ajoute un nouvel enregistrement dans un tableau "chien_aptitude" de liaison*
	   * entre code aptitude et code race pass�s en param�tres 
	   */
	  public static boolean addlien(Integer codeAptitude, Integer codeRace) {
		 return DBC.addlien(codeAptitude, codeRace);
	  }  
	    
	  /*retourne un ArrayList de noms d'aptitudes de race dont le code est pass� en param�tre*/
	  public static ArrayList<String> getAptitudesDeRace(Integer codeRace) throws Exception  {  
		 Connection conn = null;  
		 PreparedStatement prstm = null;	
		 ArrayList<String> listChienAptitude = new ArrayList<String>();
		 ResultSet rs=null;
		try {
		  conn=DBConnection.getConnection();
		  prstm = conn.prepareStatement("select aptitude.nom_aptitude from aptitude join "
				+ "chien_aptitude on chien_aptitude.code_aptitude=aptitude.code_aptitude where chien_aptitude.code_race=?;");
		  prstm.setInt(1, codeRace);
		  rs= prstm.executeQuery();
		 
          while(rs.next()) { 
            String aptitude;
            aptitude=rs.getString(1);
            listChienAptitude.add(aptitude);
           }
		 }
		 catch(SQLException sqlEx) {
            sqlEx.printStackTrace();
         } 
         catch(NumberFormatException e){}
         finally {
            try { conn.close(); } catch(SQLException se)  {se.printStackTrace();}
            try { prstm.close();} catch(SQLException se)  {se.printStackTrace();}
            try { rs.close();   } catch(SQLException se)  {se.printStackTrace();}
         }
	   return listChienAptitude;
	 }
	
	/*retourne un ArrayList de type <AptitudeBean> avec les aptitudes li�s � la race 
	 * dont le code est pass� en param�tre dans le tableau "chien_aptitude"*/
	public static ArrayList<AptitudeBean> getListAptitudesPresents(Integer codeRace){
		Connection conn = null;  
		PreparedStatement prstm = null;	
		ArrayList<AptitudeBean> listAptitude = new ArrayList<AptitudeBean>();
		ResultSet rs=null;
		try {
			conn=DBConnection.getConnection();
			prstm = conn.prepareStatement("select * from aptitude join "
					+ "chien_aptitude on chien_aptitude.code_aptitude=aptitude.code_aptitude where chien_aptitude.code_race=?;");
			prstm.setInt(1, codeRace);
			rs= prstm.executeQuery();
	        while(rs.next()) { 
	           AptitudeBean aptitude = new AptitudeBean();
	           aptitude.setCode_aptitude(Integer.parseInt(rs.getString(1)));
	           aptitude.setNom_aptitude(rs.getString(2));
	           listAptitude.add(aptitude);
	          }
			}
		catch (Exception e) {e.printStackTrace();}
	    finally {
	            try { conn.close(); } catch(SQLException se)  {se.printStackTrace();}
	            try { prstm.close();} catch(SQLException se)  {se.printStackTrace();}
	            try { rs.close();   } catch(SQLException se)  {se.printStackTrace();}
	    }
		return listAptitude;
	 }

	/*retourne un ArrayList de type <AptitudeBean> avec les aptitudes qui NE SONT PAS li�s � la race 
	 * dont le code est pass� en param�tre dans le tableau "chien_aptitude"*/
	public static ArrayList<AptitudeBean> getListAptitudesAbsents(Integer codeRace){
		Connection conn = null;  
		PreparedStatement prstm = null;	
		ResultSet rs=null;
		ArrayList<AptitudeBean> listAptitude = new ArrayList<AptitudeBean>();
		try {
			conn=DBConnection.getConnection();
			prstm = conn.prepareStatement("select * from aptitude where code_aptitude not in(select aptitude.code_aptitude "
					+ "from aptitude join chien_aptitude on chien_aptitude.code_aptitude=aptitude.code_aptitude "
					+ "where chien_aptitude.code_race=?);");
			prstm.setInt(1, codeRace);
			rs= prstm.executeQuery();
	        while(rs.next()) { 
	           AptitudeBean aptitude = new AptitudeBean();
	           aptitude.setCode_aptitude(Integer.parseInt(rs.getString(1)));
	           aptitude.setNom_aptitude(rs.getString(2));
	           listAptitude.add(aptitude);
	          }
		}
		catch (Exception e) {}
	    finally {
	            try { conn.close(); } catch(SQLException se)  {se.printStackTrace();}
	            try { prstm.close();} catch(SQLException se)  {se.printStackTrace();}
	            try { rs.close();   } catch(SQLException se)  {se.printStackTrace();}
	    }
		return listAptitude;
	 }	
	
	/*Retourne le HashMap avec les codes de toutes les races existants comme cl�s et le String*
	 * consistant de noms d'aptitudes coll�es ensemble, separ�s par les virgules comme valeur */
	public static HashMap<Integer, String> getMapAptitudes(){
		HashMap<Integer, String> aptitudes = new HashMap<Integer, String>();
		Connection conn = null;  
		PreparedStatement prstm = null;	
		ResultSet rs=null;
		try {
		 conn=DBConnection.getConnection();
		 prstm = conn.prepareStatement("select * from chien_aptitude join aptitude on "
				+ "chien_aptitude.code_aptitude=aptitude.code_aptitude order by code_race;");
		 rs= prstm.executeQuery();
		 String key=null, value="";
         while(rs.next()) { 
        	if(key==null) { key=rs.getString(2); value=rs.getString(4);}
        	else if(!key.equals(rs.getString(2))) {
        		aptitudes.put(Integer.parseInt(key), value);
        		key=rs.getString(2);
        		value=rs.getString(4);
        	}
        	else value+=", "+rs.getString(4);
           }
          aptitudes.put(Integer.parseInt(key), value);
		}
		catch(SQLException sqlEx) {
            sqlEx.printStackTrace();
        } 
		catch (Exception e) {}
        finally {
            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
            try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
        }
	  return aptitudes;
	}
	
	/*supprimer tous les liens liant une race pass�e en param�tre *
	 *avec les aptitudes dans le taableau "chien_aptitude"*/
	public static void deleteAptitudeChien(String code) {
		DBC.deleteX(code, CODE_RC);
      }
	
	/*supprimer tous les liens concernants une aptitude pass�e en param�tre *
	 *avec les aptitudes dans le taableau "chien_aptitude"*/
	public static void deleteAptitudeParCodeAptitude(String code) {
		DBC.deleteX(code, CODE_APT);
    }
	
	/*retourne un HashMap avec toutes les aptitude avec code aptitude comme cl�
	 * et string avec les noms de toutes les races ayant cette aptitude li�s ensemble
	 * et separ� par virgule comme value*/
	public static HashMap<Integer, String> getMapChiensAptitudeX(){
	  HashMap<Integer, String> chiens_aptitude = new HashMap<Integer, String>();
		
	  try(Connection conn=DBConnection.getConnection();
		  PreparedStatement prstm = conn.prepareStatement("select * from chien_aptitude join races_chiens on "
				+ "chien_aptitude.code_race=races_chiens.code_race order by code_aptitude;");
		  ResultSet rs= prstm.executeQuery()){
		    String key=null, value="";
          while(rs.next()) { 
        	if(key==null) { key=rs.getString(1); value=rs.getString(4);}
        	else if(!key.equals(rs.getString(1))) {
        		chiens_aptitude.put(Integer.parseInt(key), value);
        		key=rs.getString(1);
        		value=rs.getString(4);
        	}
        	else value+=", "+rs.getString(4);
           }
           chiens_aptitude.put(Integer.parseInt(key), value);
		 }
		 catch (Exception e) {}
	    return chiens_aptitude;
	}
}