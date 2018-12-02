package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import chiens.beans.ElevageBean;

public class DBElevageRace {

	
	private static final String CODE_ELEVAGE = "code_elevage";
	private static final String CODE_RACE = "code_race"; 
	private static final String TABLE = "race_elevage";
	private static final DBCatalogue DBC = new DBCatalogue(TABLE, CODE_ELEVAGE, CODE_RACE);
	
	/*retourne true s'il y a un enregistrement dans le tableau "race_elevage"
	 *liant un code de l'elevage passé en paramètre avec un code de race passé en paramètre*/
	public static boolean lienExiste(Integer codeElevage, Integer codeRace){  
	   try {
		return DBC.lienExiste(codeElevage, codeRace);
	   } 
	   catch (Exception e) {}
	return false;
	}
	
	/*ajoute un enregistrement dans le tableau 'race_elevage' liant un code d'elevage et
	 * un code de race, passes en paramètres */
	public static boolean addlien(Integer codeElevage, Integer codeRace) {
	   return DBC.addlien(codeElevage, codeRace);
	}
	
	/*supprime tous les enregistrements du tableau 'race_elevage' ou le
	 * code d'elevage est égale à une valeur passée en paramètre*/
	public static void deleteElevageParCodeElevage(String code) {
		DBC.deleteX(code, CODE_ELEVAGE);
    }
	
	/*supprime tous les enregistrements du tableau 'race_elevage' ou le
	 * code de race est égale à une valeur passée en paramètre*/
	public static void deleteElevageParCodeRace(String code) {
		DBC.deleteX(code, CODE_RACE);
    }	
	
	/*retourne une collection ArrayList de type ElevageBean, contenant tous les 
	 * élévages ou une race de chiens dont le code est passé en paramètre est élévée
	 */
	public static ArrayList<ElevageBean> getElevagesDeRace(String codeRace){  
		Connection conn = null;
		PreparedStatement prstm = null;	
		ResultSet rs = null;
		ArrayList<ElevageBean> listChienElevages = new ArrayList<>();
		try {
		 conn=DBConnection.getConnection();
		 prstm = conn.prepareStatement("select * from elevage join "
				+ "race_elevage on race_elevage.code_elevage=elevage.code_elevage where race_elevage.code_race=?;");
		 prstm.setString(1, codeRace);
		 rs= prstm.executeQuery();
         while(rs.next()) {
           ElevageBean elevage = new ElevageBean();
           elevage.setCodeElevage(rs.getInt("code_elevage"));
           elevage.setNomElevage(rs.getString("nom_elevage"));
           elevage.setAdresse(rs.getString("adresse"));
           elevage.setTelephone(rs.getString("telephone"));
           elevage.setEmail(rs.getString("email"));
           elevage.setSiteInternet(rs.getString("site_internet"));
           listChienElevages.add(elevage);
          }
		}
		catch(SQLException |NumberFormatException e){e.printStackTrace();} 
        finally {
            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
            try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
        }
	 return listChienElevages;
	}
	
	/*retourne une collection map avec tous les élévages comme les clés et les chaines de caractère
	 * avec les noms de toutes les races élévées dans l'élévage donné concatenées ensemble comme valeurs*/
	public static Map<Integer, String> getRacesElevage(){
		HashMap<Integer, String> elevages = new HashMap<Integer, String>();
		try(Connection conn=DBConnection.getConnection();
		    PreparedStatement prstm = conn.prepareStatement("select * from races_chiens join race_elevage on "
				+ "races_chiens.code_race=race_elevage.code_race order by code_elevage;");
		    ResultSet rs= prstm.executeQuery();){
		 String key=null, value="";
         while(rs.next()) {
        	if(key==null) { key=rs.getString("code_elevage"); value=rs.getString("nom_race");}
        	else if(!key.equals(rs.getString("code_elevage"))) {
        		elevages.put(Integer.parseInt(key), value);
        		key=rs.getString("code_elevage");
        		value=rs.getString("nom_race");
        	}
        	else value+=", "+rs.getString("nom_race");
          }
          elevages.put(Integer.parseInt(key), value);
		}
		catch(SQLException |NumberFormatException e){e.printStackTrace();} 
		catch (Exception e) {}
	  return elevages;
	}
	
	/*retourne une Map de toutes les races de chiens élévées(si le boolean present, passé en paramètre, est égale à true
	 * ou pas élévées(dans le cas contraire) dans l'élévage dont le code est passé en paramètre. Les clés de Map sont 
	 * les codes de races et les valeurs leurs noms*/
	public static Map<Integer, String> getRacesElevage(String code, boolean present){
		HashMap<Integer, String> races_elevage = new HashMap<>();
		String query = present?
	    "select * from races_chiens join race_elevage on races_chiens.code_race=race_elevage.code_race where code_elevage="+code+";":
	    "select * from races_chiens where code_race not in (select races_chiens.code_race from races_chiens join race_elevage on races_chiens.code_race=race_elevage.code_race where code_elevage!="+code+")";
		try(Connection conn=DBConnection.getConnection();
		    PreparedStatement prstm = conn.prepareStatement(query);
		    ResultSet rs= prstm.executeQuery();){
         while(rs.next()) {
               races_elevage.put(rs.getInt("code_race"), rs.getString("nom_race"));
          }
		}
		catch(SQLException |NumberFormatException e){e.printStackTrace();} 
		catch (Exception e) {}
	  return races_elevage;
	}
}
