package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import chiens.beans.ElevageBean;

public class DBElevage {
	private static String TABLE = "elevage";
	private static DBCatalogue DBC = new DBCatalogue(TABLE);
    
        /*retourne true si un nom de race passé en paramètre existe déjà dans le tableau "elevage"*/
        public boolean raceExiste(String nom_race) throws Exception  {  
	        return DBC.xExiste(nom_race, "nom_elevage");
	    }
	    
        /*retourne ElevageBean representant une élévage dont le code est passé en paramètre*/
        public static ElevageBean getElevage(String code) {
        	ElevageBean elevage = null;
        	try(Connection conn = DBConnection.getConnection();
        		PreparedStatement prstm = conn.prepareStatement("select * from elevage where code_elevage="+code+";");
        		ResultSet rs= prstm.executeQuery();){
        		elevage = new ElevageBean();
        		if(rs.next()) {
        			elevage.setCodeElevage(rs.getInt("code_elevage"));
        			elevage.setNomElevage(rs.getString("nom_elevage"));
        			elevage.setAdresse(rs.getString("adresse"));
        			elevage.setEmail(rs.getString("email"));
        			elevage.setTelephone(rs.getString("telephone"));
        			elevage.setSiteInternet(rs.getString("site_internet"));
        		}
        	}
        	catch(SQLException e) {
        		e.printStackTrace();
        	}
        	return elevage;
        }
        
        /*supprime une élévage dont le code est passé en paramètre du tableau "elevage"*/
	    public static void deleteElevage(String code) {
	    	DBC.deleteX(code, "code_elevage");
        }
	
	    /*retourne le nom de l'élévage du tableau "elevage" dont le code est passé en paramètre*/
	    public static String getNomParCode(Integer code) {
	        return DBC.getNomParCode(code, "code_elevage", "nom_elevage");
	    }
	
	    /*retourne une collection de ElevageBean representants toutes les élévages du tableau "elevage"*/
		public static ArrayList<ElevageBean> getElevageList(){
		   ArrayList<ElevageBean> listElevages = new ArrayList<ElevageBean>();	
		   try(Connection conn=DBConnection.getConnection();
		   PreparedStatement prstm = conn.prepareStatement("select * from elevage;");
		   ResultSet rs= prstm.executeQuery();){
		      while(rs.next()) {
				 ElevageBean elevage = new ElevageBean();
				 elevage.setCodeElevage(Integer.parseInt(rs.getString(1)));
				 elevage.setNomElevage(rs.getString(2));
				 elevage.setTelephone(rs.getString(3));
				 elevage.setAdresse(rs.getString(4));
				 elevage.setSiteInternet(rs.getString(5));
				 elevage.setEmail(rs.getString(6));
	       	     listElevages.add(elevage);
			  }
		   }
		   catch (SQLException |NumberFormatException e){e.printStackTrace();} 
		   catch (Exception e) {}
		   return listElevages;
	    }
		 
		/*ajoute une élévage dans le tableau "elevage" à partir de données de ElevageBean passé
		 * en paramètre et retourne une clé généré automatiquement si l'opération est reussi et
		 * null dans le cas contraire*/
	    public static Integer addElevage(ElevageBean elevage) {
		    Connection conn = null;  
			PreparedStatement prstm = null;	
			Integer generKey=null;
			ResultSet rs = null;
		  try {
			conn=DBConnection.getConnection();
			prstm = conn.prepareStatement("insert into elevage(nom_elevage, telephone, " + 
					"adresse, site_internet, email) values(?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			prstm.setString(1, elevage.getNomElevage());
			prstm.setString(2, elevage.getTelephone());
			prstm.setString(3, elevage.getAdresse());
			prstm.setString(4, elevage.getSiteInternet());
			prstm.setString(5, elevage.getEmail());
			prstm.executeUpdate();
			rs=prstm.getGeneratedKeys();
			if(rs.next()) generKey=rs.getInt(1);
	      }
	      catch (SQLException |NumberFormatException e){e.printStackTrace();} 
		  catch (Exception e) {e.printStackTrace();}
	      finally {
	       try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
	       try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
	       try { rs.close(); } catch(SQLException se) {se.printStackTrace();}
	      }
	      return generKey;
	   }
	    
	    /*remplace une élévage dont le code est le code de ElevageBean passé en paramètre 
	     * dans le tableau "elevage" à partir de données de ElevageBean passé
		 * en paramètre*/
	   public static void updatElevage(ElevageBean elevage) {
		    Connection conn = null;  
			PreparedStatement prstm = null;	
		  try {
			conn=DBConnection.getConnection();
			prstm = conn.prepareStatement("Update elevage set nom_elevage=?, telephone=?, " + 
					"adresse=?, site_internet=?, email=? where code_elevage=?;");
			
			prstm.setString(1, elevage.getNomElevage());
			prstm.setString(2, elevage.getTelephone());
			prstm.setString(3, elevage.getAdresse());
			prstm.setString(4, elevage.getSiteInternet());
			prstm.setString(5, elevage.getEmail());
			prstm.setInt(6, elevage.getCodeElevage());
			prstm.executeUpdate();
	      }
	      catch (SQLException |NumberFormatException e){e.printStackTrace();} 
		  catch (Exception e) {e.printStackTrace();}
	      finally {
	       try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
	       try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
	      }
	   }
	    
}
