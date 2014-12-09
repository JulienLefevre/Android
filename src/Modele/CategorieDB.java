package Modele;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.ResultSet;
import java.util.ArrayList;

//import android.util.Log;

public class CategorieDB extends Categorie implements CRUD {

    protected static Connection dbConnect = null;
    
    public static void setConnection(Connection nouvdbConnect) {
      dbConnect = nouvdbConnect;
   }

   public CategorieDB () {
	   
   }
     
   public CategorieDB ( int idCategorie, String nomCategorie, Communaute communaute ) {
       super(idCategorie,nomCategorie, communaute);
   }
     
   public CategorieDB ( String nomCategorie, Communaute communaute ) {
	   super(nomCategorie, communaute);
   }
    
   public CategorieDB ( int idCategorie ) {
       super(idCategorie );
   }

   @Override
   public void create() throws Exception {
       CallableStatement cstmt = null;
       try
       {  String query = "CALL CREATE_CATEGORIE(?,?,?)";
          cstmt = dbConnect.prepareCall(query);
          cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
          cstmt.setString(2,this.nomCategorie);
          cstmt.setInt(3,this.communaute.getIdCommunaute());
          cstmt.executeUpdate();
          this.idCategorie = cstmt.getInt(1); 
       }
       catch(Exception e ) {
          throw new Exception("Erreur de création "+e.getMessage());
       }
    }

    @Override
    public void update() throws Exception {
        CallableStatement cstmt = null;
        try
        {   String query = "CALL UPDATE_CATEGORIE(?,?)";
            cstmt = dbConnect.prepareCall(query);
            cstmt.setInt(1,this.idCategorie);
            cstmt.setString(2,this.nomCategorie);
            cstmt.executeUpdate();
        }
	catch(Exception e){
            throw new Exception("Erreur de mise à jour : "+e.getMessage());   
        }
    }

    @Override
    public void delete() throws Exception {
        CallableStatement cstmt = null;
        try
        {   String query = "CALL DELETE_CATEGORIE(?)";
            cstmt = dbConnect.prepareCall(query);
            cstmt.setInt(1,this.idCategorie);
            cstmt.executeUpdate();
        }
	catch(Exception e){
            throw new Exception("Echec de la suppression : "+e.getMessage());   
        }
    }

    @Override
    public void read() throws Exception {
	    String req = "SELECT * FROM CATEGORIE WHERE ID_CATEGORIE = ?"; 
        PreparedStatement pstmt = null;
        try
        {	pstmt=dbConnect.prepareStatement(req);
            pstmt.setInt(1,this.idCategorie);
     	    ResultSet rs= (ResultSet)pstmt.executeQuery();	
        	
     	    if(rs.next()) {
     	    	this.nomCategorie = rs.getString("NOM_CATEGORIE");
     	    	this.communaute = new CommunauteDB(rs.getInt("COMMUNAUTE"));
     	    	((CRUD)this.communaute).read();
            }
     	    else { 
     	    	throw new Exception("Code inconnu");
     	    }

        }
        catch(Exception e) {
        	System.err.println("erreur"+e);
        	//Log.d("connexion","erreur"+e);   
            throw new Exception("Erreur de lecture "+e.getMessage());
        }
        finally	{ 
        	try	{
              pstmt.close();
            }
        	catch (Exception e)	{ }
        }
     }
    
    public static ArrayList <CategorieDB> readCategories () throws Exception {
    	ArrayList <CategorieDB> categories = new ArrayList <CategorieDB> ();
    	CategorieDB categorie = new CategorieDB();
    	String req = "SELECT * FROM CATEGORIE";
    	PreparedStatement pstmt = null;
    	
    	try
    	{	pstmt = dbConnect.prepareStatement(req);
    		ResultSet rs = (ResultSet)pstmt.executeQuery();
    		while (rs.next()) {
    			categorie.setIdCategorie(rs.getInt("ID_CATEGORIE"));
    			categorie.setCommunaute(new CommunauteDB(rs.getInt("COMMUNAUTE")));
     	    	((CRUD)categorie).read();
     	    	categories.add(categorie);
     	    	categorie = new CategorieDB();
    		}
    		if ( categories.isEmpty() )
    			throw new Exception("Aucune catégorie disponible !");
    		else
    			return categories;
    	}
    	catch (Exception e) { 
    		throw new Exception("Echec de la lecture : "+e.getMessage());
    	}
    }
}