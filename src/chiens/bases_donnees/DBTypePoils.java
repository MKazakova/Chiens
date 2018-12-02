package chiens.bases_donnees;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import chiens.beans.TypePoilsBean;

public class DBTypePoils {
	private static final String CODE_TYPE_POILS = "code_type_poils";
	private static final String DESCRIPTION_TYPE_POILS = "description_type_poils"; 
	private static final String TABLE = "type_poils";
	private static final DBCatalogue DBC = new DBCatalogue(TABLE, CODE_TYPE_POILS, DESCRIPTION_TYPE_POILS);
	
	   /*retourne true si le nom de pays passé en paramètre existe déjà dans le tableau "type_poils"*/
	   public boolean nomExiste(String nom) throws Exception  {  
		      return DBC.xExiste(nom, CODE_TYPE_POILS);
	   }

	   /*ajoute un nom de pays passé en paramètre dans le tableau "type_poils" et retourne
	    * un entiere - code de pays - généré automatiquement*/
	   public Integer ajouterTypePoils(String nom_typePoils) {
		      return DBC.ajouterX(nom_typePoils, DESCRIPTION_TYPE_POILS);
	   }
	   
	   /*retourne le nom de pays dont le code est passé en paramètre*/
	   public static String getNomParCode(Integer code) {
			  return DBC.getNomParCode(code, CODE_TYPE_POILS, DESCRIPTION_TYPE_POILS);
	   }
		
	   /*supprime le type de poils dont le code est égalè à celui passé en paramètre du tableau "type_poils"*/
	   public static void deleteTypePoils(String code) {
		      DBC.deleteX(code, CODE_TYPE_POILS);
	   }
		
	   /*retourne une collection ArrayList de tous les types de poils du tableau "type_poils" sous forme 
	    * de TypePoilsBean*/
	   public ArrayList<TypePoilsBean> getTypePoilsList(){
		    ArrayList<TypePoilsBean> listTypePoils = new ArrayList<TypePoilsBean>();	
			try(Connection conn=DBConnection.getConnection();
			    PreparedStatement prstm = conn.prepareStatement("select * from type_poils;");
			    ResultSet rs= prstm.executeQuery();){
			    while(rs.next()) { 
				 TypePoilsBean typepoils = new TypePoilsBean();
				 typepoils.setDescription_type_poils(rs.getString(2));
				 typepoils.setCode_type_poils(Integer.parseInt(rs.getString(1)));
				 listTypePoils.add(typepoils);
			  }
			}
			catch (SQLException | NumberFormatException e){ e.getStackTrace();} 
			catch (Exception e) {
				e.getStackTrace();
			}
		   return listTypePoils;
		}
		 
	}
