package chiens.bases_donnees;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";        
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/chiens?useUnicode=true&useSSL=false";        
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "Bassettaksadog13";
	
	static Connection getConnection(){
		Connection dbConnection = null;  
		try {             
			Class.forName(DB_DRIVER); 
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);       
			} 
		catch (ClassNotFoundException|SQLException  e) {                    
                 e.printStackTrace();
			}       
		return dbConnection;
	}
}
