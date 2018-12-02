package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import chiens.bases_donnees.DBConnection;

public class DBAccesAdmin {
	private String message;
	
	/*compare le mot de passe saisie avec le mot de passe dans le tableau 'acces'*/
	public boolean verifAcces(String motPasse) throws Exception  {  
		boolean acces_autorise=false;
		ResultSet rs = null;
		Connection conn = null;  
		PreparedStatement prstm = null;	
		try {
		conn=DBConnection.getConnection();
		prstm = conn.prepareStatement("select * from acces where login = 'admin' and mot_passe=MD5(?);");
		prstm.setString(1, motPasse);
		rs= prstm.executeQuery();
        if(!rs.next()) { message = "Mot de passe n'est pas correct";
                         acces_autorise=false;
        }
        else {
        	acces_autorise=true;
	    	}
		}
		catch(SQLException sqlEx) {
            sqlEx.printStackTrace();
        } 
        catch(NumberFormatException e){}
        finally {
            try { conn.close(); } catch(SQLException se) {}
            try { prstm.close(); } catch(SQLException se) {}
            try { rs.close(); } catch(SQLException se)   {}
        }
	      return acces_autorise;
	
	}
	
	/*change le mot de passe d'administrateur dans la base 'acces'*/
	public int changerMotPasse(String nouveauMP) {
        int ok=0;
		Connection conn = null;  
		PreparedStatement prstm = null;	
		try {
		conn=DBConnection.getConnection();
		prstm = conn.prepareStatement("UPDATE acces SET mot_passe=MD5(?) where login = 'admin';");
		prstm.setString(1, nouveauMP);
		ok = prstm.executeUpdate();
		}
		catch(SQLException sqlEx) {
            sqlEx.printStackTrace();
        } 
        catch(NumberFormatException e){}
        finally {
            try { conn.close(); } catch(SQLException se) {}
            try { prstm.close(); } catch(SQLException se) {}
        }
		return ok;
	}

	/*retourne le message d'erreur si le mot de passe n'est pas correct*/
	public String getMessage() {
		return message;
	}	
	
}
