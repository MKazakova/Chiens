package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*une class utilitaire avec les methodes effectuant les opérations les plus courant dans le programme*/
public class DBCatalogue {
	private ResultSet rs;
	private Connection conn;
	private PreparedStatement prstm;
	private final String TABLE;
	private String CHAMP1;
	private String CHAMP2;
	
    
	public DBCatalogue(String table){
		this.TABLE = table;
	}
	
	public DBCatalogue(String table, String champ1, String champ2){
		this.TABLE = table;
		this.CHAMP1=champ1;
		this.CHAMP2=champ2;
	}
	
	/*retourne true si un champs passé en parametre contient une
	 *valeur passée en parametre */
	public boolean xExiste(String valeur, String champ) {  
		boolean existe=false;
		conn = null;  
		prstm = null;	
		try {
		conn=DBConnection.getConnection();
		prstm = conn.prepareStatement("select * from "+TABLE+" where "+champ+"=?;");
		prstm.setString(1, valeur);
		rs= prstm.executeQuery();
        if(rs.next()) { existe=true;
        }
        else {
        	existe=false;
	    	}
		}
		catch(SQLException sqlEx) {
            sqlEx.printStackTrace();
        } 
		catch (Exception e) {e.printStackTrace();}
        finally {
            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
            try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
        }
	      return existe;
	}
	
	/*ajoute une valeur dans le champ donné*/
	public Integer ajouterX(String valeur, String champ) {
	    conn = null;  
		prstm = null;	
		Integer generKey=null;
		try {
		conn=DBConnection.getConnection();
		prstm = conn.prepareStatement("insert into "+TABLE+"("+champ+")values(?);", Statement.RETURN_GENERATED_KEYS);
		prstm.setString(1, valeur);
		prstm.executeUpdate();
		rs=prstm.getGeneratedKeys();
		if(rs.next()) generKey=rs.getInt(1);
    } 
    catch (Exception e) {e.printStackTrace();}
    finally {
       try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
       try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
       try { rs.close(); } catch(SQLException se) {se.printStackTrace();}
      }
      return generKey;
   }
	
	/*supprime les enregistrements ou un champ passé en paramètre est égale à
	 * une valeur passé en paramètre*/
	public void deleteX(String valeur, String champ) {
    	conn = null;  
   		prstm = null;	
   		try {
   		conn=DBConnection.getConnection(); 
   		prstm = conn.prepareStatement("delete from "+TABLE+" where "+champ+"=?;");
   		prstm.setString(1, valeur);  
   		prstm.executeUpdate();
        } 
         catch (Exception e) {e.printStackTrace();}
         finally {
             try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
             try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
         }
    }
	
	/*retourne une String comme valeur d'un nom ou la valeur de code est égale à une Integer passé en paramètre*/
	public String getNomParCode(Integer code, String champCode, String champNom) {
		  conn = null;  
		  prstm = null;	
		  String nom="";
			try {
		    conn=DBConnection.getConnection();
			prstm = conn.prepareStatement("select "+champNom+" from "+TABLE+" where "+champCode+"=?;");
			prstm.setInt(1, code);
			rs= prstm.executeQuery();
	        if(rs.next()) { 
	        	nom = rs.getString(1);
	         }
			}
			catch(SQLException sqlEx) {
	            sqlEx.printStackTrace();
	        }  
	        catch (Exception e) {e.printStackTrace();}
	        finally {
	            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
	            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
	            try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
	        }
		  return nom;
		}
	
	/*retourne true s'il y a un enregistrement liant deux valeurs(codes) passées en paramètres*/
	public boolean lienExiste(Integer code1, Integer code2) throws Exception  {  
		boolean existe=false;
		conn = null;  
		prstm = null;	
		
		try {	
		 conn=DBConnection.getConnection();
		 prstm = conn.prepareStatement("select * from "+TABLE+" where "+CHAMP1+"=? and "+CHAMP2+"=?;");
		 prstm.setInt(1, code1);
		 prstm.setInt(2, code2);
		 rs= prstm.executeQuery();
		
        if(rs.next()) { existe=true;
        }
        else {
        	existe=false;
	    	}
		}
		catch(SQLException sqlEx) {
            sqlEx.printStackTrace();
        } 
        catch(NumberFormatException e){e.printStackTrace();}
        finally {
            try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
            try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
            try { rs.close(); } catch(SQLException se)   {se.printStackTrace();}
        }
	  return existe;
	}
	
	/*ajoute deux valeurs dans les champs passées comme paramètres de constructeur*/
	public boolean addlien(Integer code1, Integer code2) {
		boolean succes=false;
	    conn = null;  
		prstm = null;
		
		try {
             conn=DBConnection.getConnection();
		     prstm = conn.prepareStatement("insert into "+TABLE+"("+CHAMP1+", "+CHAMP2+") values(?, ?);");
			 prstm.setInt(1, code1);
			 prstm.setInt(2, code2);
				
	    	 int result = prstm.executeUpdate();
			 if(result==1) succes=true;
		  } 
		   catch (Exception e) {e.printStackTrace();}
		   finally {
		       try { conn.close(); } catch(SQLException se) {se.printStackTrace();}
		       try { prstm.close(); } catch(SQLException se) {se.printStackTrace();}
		   }
		return succes;
	}
	
}
