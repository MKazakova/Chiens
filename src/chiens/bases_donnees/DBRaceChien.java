package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import chiens.beans.RaceChienBean;

public class DBRaceChien {
	
	private static final String CODE_RACE = "code_race";
	private static final String NOM_RACE = "nom_race"; 
	private static final String TABLE = "races_chiens";
    private static final DBCatalogue DBC = new DBCatalogue(TABLE);
    
    /*retourne true si un nom de la race passé en paramètre existe déjà
     * dans le tableau "races_chiens"*/
	public boolean raceExiste(String nom_race) throws Exception  {  
	      return DBC.xExiste(nom_race, NOM_RACE);
	}
	
	/*supprime une race dont le code est passé en paramètre du tableau "races_chiens"*/
	public static void deleteRace(String code) {
		  DBC.deleteX(code, CODE_RACE);
    }
	
	/*retourne un nom de race dont le code est passé en paramètre*/
	public static String getNomParCode(Integer code) {
	      return DBC.getNomParCode(code, CODE_RACE, NOM_RACE);
	}
	
	/*prend en paramètre RaceChienBean et crée un enregistrement correspondant 
	 * dans le tableau "races_chiens" et retourne un entier representant un code de race de chien généré automatiquement*/
	public Integer addRace(RaceChienBean race) {
		Connection conn = null;  
		PreparedStatement prstm = null;	
		Integer generKey=null;
		ResultSet rs = null;
	 try {
		conn=DBConnection.getConnection();
		prstm = conn.prepareStatement("insert into races_chiens(nom_race, pays_provenance, " + 
				"facilite_education, apte_garde, poids_min, poids_max, taille_min, taille_max, activite,"
				 + " entente_enfants, entente_animaux, image, description) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		prstm.setString(1, race.getNom_race());
		prstm.setInt(2, race.getPays_provenance());
		prstm.setInt(3, race.getFacilite_education());
		prstm.setInt(4, race.getApte_garde());
		prstm.setDouble(5, race.getPoids_min());
		prstm.setDouble(6, race.getPoids_max());
		prstm.setInt(7, race.getTaille_min());
		prstm.setInt(8, race.getTaille_max());
		prstm.setInt(9, race.getActivite());
		prstm.setInt(10, race.getEntente_enfants());
		prstm.setInt(11, race.getEntente_autres_animaux());
		prstm.setString(12, race.getImage());
		prstm.setString(13, race.getDescription());
		prstm.executeUpdate();
		rs=prstm.getGeneratedKeys();
		if(rs.next()) generKey=rs.getInt(1);
     } 
	 catch(SQLException |NumberFormatException e){e.printStackTrace();} 
     finally {
         try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
         try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
         try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
     }
      return generKey;
    }
	
	/*retourne un RaceChienBean =, representant les données de la race de chiens dont le code est passé
	 * en paramètre */
	public static RaceChienBean getRaceChien(Integer code) {
		RaceChienBean chienTrouve = new RaceChienBean();
		Connection conn = null;  
		PreparedStatement prstm = null;	
		ResultSet rs = null;
		try {
		 conn=DBConnection.getConnection();
		 prstm = conn.prepareStatement("select * from races_chiens where code_race=?;");
		 prstm.setInt(1, code);
		 rs= prstm.executeQuery();
         while(rs.next()) { 
        	chienTrouve.setCode_race(code);
        	chienTrouve.setNom_race(rs.getString(2));
        	chienTrouve.setPays_provenance(Integer.parseInt(rs.getString(3)));
        	chienTrouve.setFacilite_education(Integer.parseInt(rs.getString(4)));
        	chienTrouve.setApte_garde(Integer.parseInt(rs.getString(5)));
        	chienTrouve.setPoids_min(Double.parseDouble(rs.getString(6)));
        	chienTrouve.setPoids_max(Double.parseDouble(rs.getString(7)));
        	chienTrouve.setTaille_min(Integer.parseInt(rs.getString(8)));
        	chienTrouve.setTaille_max(Integer.parseInt(rs.getString(9)));
        	chienTrouve.setActivite(Integer.parseInt(rs.getString(10)));
        	chienTrouve.setEntente_enfants(Integer.parseInt(rs.getString(11)));
        	chienTrouve.setEntente_autres_animaux(Integer.parseInt(rs.getString(12)));
        	chienTrouve.setImage(rs.getString(13));
        	chienTrouve.setDescription(rs.getString(14));
            }
		}
		catch(SQLException |NumberFormatException e){e.printStackTrace();} 
        finally {
            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
            try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
        }
		return chienTrouve;
	}

	/*retourne une collection ArrayList de toutes les races de chiens existants dans le tableau races_chiens*/
	public static ArrayList<RaceChienBean> getTousLesRacesChiens() {
		ArrayList<RaceChienBean> chiens = new ArrayList<RaceChienBean>();	
		try(Connection conn=DBConnection.getConnection();
		    PreparedStatement prstm = conn.prepareStatement("select * from races_chiens;");
		    ResultSet rs= prstm.executeQuery();){
           while(rs.next()) { 
        	RaceChienBean chienTrouve = new RaceChienBean();
        	chienTrouve.setCode_race(Integer.parseInt(rs.getString(1)));
        	chienTrouve.setNom_race(rs.getString(2));
        	if(rs.getString(3)!=null)chienTrouve.setPays_provenance(Integer.parseInt(rs.getString(3)));
        	if(rs.getString(4)!=null)chienTrouve.setFacilite_education(Integer.parseInt(rs.getString(4)));
        	if(rs.getString(5)!=null)chienTrouve.setApte_garde(Integer.parseInt(rs.getString(5)));
        	if(rs.getString(6)!=null)chienTrouve.setPoids_min(Double.parseDouble(rs.getString(6)));
        	if(rs.getString(7)!=null)chienTrouve.setPoids_max(Double.parseDouble(rs.getString(7)));
        	if(rs.getString(8)!=null)chienTrouve.setTaille_min(Integer.parseInt(rs.getString(8)));
        	if(rs.getString(9)!=null)chienTrouve.setTaille_max(Integer.parseInt(rs.getString(9)));
        	if(rs.getString(10)!=null)chienTrouve.setActivite(Integer.parseInt(rs.getString(10)));
        	if(rs.getString(11)!=null)chienTrouve.setEntente_enfants(Integer.parseInt(rs.getString(11)));
        	if(rs.getString(12)!=null)chienTrouve.setEntente_autres_animaux(Integer.parseInt(rs.getString(12)));
        	if(rs.getString(13)!=null)chienTrouve.setImage(rs.getString(13));
        	if(rs.getString(14)!=null)chienTrouve.setDescription(rs.getString(14));
        	chiens.add(chienTrouve);
           }
		}
		catch(SQLException |NumberFormatException e){e.printStackTrace();} 
        catch (Exception e) {e.printStackTrace();}
		return chiens;
	}
	
	/*remplace un enregistrement de la race de chiens où le code de race est égale à un code de race de RaceChienBean
	 * et remplace toute information sauf code par l'information de RaceChienBean retourne true si l'opération est
	 * reussi*/
	public boolean editRace(RaceChienBean race) {
		boolean succes=false;
		Connection conn = null;  
		PreparedStatement prstm = null;	
		try {
		 conn=DBConnection.getConnection();
		 prstm = conn.prepareStatement("UPDATE races_chiens SET nom_race=?, pays_provenance=?, "
				+ "facilite_education=?, apte_garde=?, poids_min=?, poids_max=?, taille_min=?, taille_max=?, "
				+ "activite=?, entente_enfants=?, entente_animaux=?, image=?, description=? WHERE code_race=?;");
		 prstm.setString(1, race.getNom_race());
		 prstm.setInt(2, race.getPays_provenance());
		 prstm.setInt(3, race.getFacilite_education());
		 prstm.setInt(4, race.getApte_garde());
		 prstm.setDouble(5, race.getPoids_min());
		 prstm.setDouble(6, race.getPoids_max());
		 prstm.setInt(7, race.getTaille_min());
		 prstm.setInt(8, race.getTaille_max());
		 prstm.setInt(9, race.getActivite());
		 prstm.setInt(10, race.getEntente_enfants());
	   	 prstm.setInt(11, race.getEntente_autres_animaux());
		 prstm.setString(12, race.getImage());
		 prstm.setString(13, race.getDescription());
		 prstm.setInt(14, race.getCode_race());
		int res = prstm.executeUpdate();
		if(res!=0) succes=true;
        } 
		catch(SQLException |NumberFormatException e){e.printStackTrace();} 
        finally {
            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
        }
	 return succes;
    }
    
	/*retourne true si le tableau races_chiens contient des races dont le code du pays de provenance est
	 * égale à un code passé en paramètre*/
	public static boolean RacesOriginairesExistent(String code) {
		boolean existe=false;
		Connection conn = null;  
		PreparedStatement prstm = null;	
		ResultSet rs = null;
		try {
		conn=DBConnection.getConnection();
		prstm = conn.prepareStatement("select * from races_chiens where pays_provenance=?;");
		prstm.setString(1, code);
		rs= prstm.executeQuery();
        if(rs.next()) { existe=true;
        }
        else {
        	existe=false;
	    	}
		}
		catch(SQLException |NumberFormatException e){e.printStackTrace();} 
        finally {
            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
            try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
        }
		return existe;
	}
}
