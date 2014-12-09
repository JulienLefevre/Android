package Modele;

//import static Modele.CommunauteDB.dbConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.ResultSet;
//import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//import android.util.Log;

public class UtilisateurDB extends Utilisateur implements CRUD {
    
    protected static Connection dbConnect = null;
    
    public static void setConnection(Connection nouvdbConnect) {
      dbConnect = nouvdbConnect;
    }

    public UtilisateurDB() {
    }

    public UtilisateurDB(int idUtilisateur) {
        super(idUtilisateur);
    }

    public UtilisateurDB(int idUtilisateur, String nom, String prenom, String telephone, String pseudonyme, String password) {
        super(idUtilisateur, nom, prenom, telephone, pseudonyme, password);
    }
    
    public UtilisateurDB(String nom, String prenom, String telephone, String pseudonyme, String password) {
        super(nom, prenom, telephone, pseudonyme, password);
    }
    
    public UtilisateurDB(String pseudonyme, String password) {
        super( pseudonyme, password);
    }

    @Override
    public void create() throws Exception {
       CallableStatement cstmt = null;
       try
       {  String query = "CALL CREATE_UTILISATEUR(?,?,?,?,?,?)";
          cstmt = dbConnect.prepareCall(query);
          cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
          cstmt.setString(2,this.nom);
          cstmt.setString(3,this.prenom);
          cstmt.setString(4,this.telephone);
          cstmt.setString(5,this.pseudonyme);
          cstmt.setString(6,this.password);
          cstmt.executeUpdate();
          this.idUtilisateur = cstmt.getInt(1); 
       }
       catch(Exception e ) {
          throw new Exception("Erreur de création "+e.getMessage());
       }
       finally
       {   try {
             cstmt.close();
           }
           catch (Exception e) {
               throw new Exception("Erreur lors de la deconnexion");
           }
       }
    }

    @Override
    public void update() throws Exception {
        CallableStatement cstmt = null;
        try
        {   String query = "CALL UPDATE_UTILISATEUR(?,?,?,?,?,?)";
            cstmt = dbConnect.prepareCall(query);
            cstmt.setInt(1,this.idUtilisateur);
            cstmt.setString(2,this.nom);
            cstmt.setString(3,this.prenom);
            cstmt.setString(4,this.telephone);
            cstmt.setString(5,this.password);
            cstmt.setString(6,this.pseudonyme);
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
        {   String query = "CALL DELETE_UTILISATEUR(?)";
            cstmt = dbConnect.prepareCall(query);
            cstmt.setInt(1,this.idUtilisateur);
            cstmt.executeUpdate();
        }
	catch(Exception e){
            throw new Exception("Echec de la suppression : "+e.getMessage());   
        }
    }
    
    @Override
    public void read() throws Exception {
    	String req = "select * from utilisateur where id_Utilisateur = ?"; 
        PreparedStatement pstmt = null;
        try
        {	pstmt = dbConnect.prepareStatement(req);
            pstmt.setInt(1,this.idUtilisateur);
     	    ResultSet rs = (ResultSet)pstmt.executeQuery();	
        	
     	    if(rs.next()) {
                this.nom = rs.getString("NOM");
                this.prenom = rs.getString("PRENOM");
                this.telephone = rs.getString("TELEPHONE");
                this.pseudonyme = rs.getString("PSEUDO");
                this.password = rs.getString("PASSWORD");
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
    
    public ArrayList <Communaute> mesCommunautes () throws Exception {
       
    	ArrayList <Communaute> communities = new ArrayList <Communaute> ();
        Communaute community;
        String req = "SELECT * FROM MEMBRE WHERE UTILISATEUR = ?"; 
        PreparedStatement pstmt = null;
        
        try
        {  boolean trouvé = false;
	       pstmt = dbConnect.prepareStatement(req);
	       pstmt.setInt(1,this.idUtilisateur);
	 	   ResultSet rs = (ResultSet)pstmt.executeQuery();	
           while (rs.next()) {
              trouvé = true; 
              community = new CommunauteDB(rs.getInt("COMMUNAUTE"));
              ((CRUD)community).read();
              communities.add(community);
           }
           if ( !trouvé )
               throw new Exception("N'apartient à aucune communauté");
           else 
               return communities;
        }
        catch ( Exception e ) {
           throw new Exception(e.getMessage());      
        }
    }
    
    public ArrayList <Communaute> mesCommunautesAdministrees () throws Exception {
      
    	ArrayList <Communaute> communities = new ArrayList <Communaute> ();
        Communaute community;
        String req = "SELECT * FROM COMMUNAUTE WHERE ADMINISTRATEUR = ?"; 
        PreparedStatement pstmt = null;
        
        try
        {  boolean trouvé = false;
	       pstmt = dbConnect.prepareStatement(req);
	       pstmt.setInt(1,this.idUtilisateur);
	 	   ResultSet rs = (ResultSet)pstmt.executeQuery();	
           while (rs.next()) {
              trouvé = true; 
              community = new CommunauteDB(rs.getInt("ID_COMMUNAUTE"));
              ((CRUD)community).read();
              communities.add(community);
           }
           if ( !trouvé )
               throw new Exception("Aucune communauté administrée !");
           else 
               return communities;
        }
        catch ( Exception e ) {
           throw new Exception(e.getMessage());      
        }
    	
    	
    }	
      
    
    public void Identification () throws Exception {
         	
    	String req = "select * from utilisateur where pseudo = ? and password = ?"; 
        PreparedStatement pstmt = null;
        try
        {	pstmt = dbConnect.prepareStatement(req);
            pstmt.setString(1,this.pseudonyme);
            pstmt.setString(2,this.password);
            
     	    ResultSet rs = (ResultSet)pstmt.executeQuery();	
        	
     	    if(rs.next()) {
     	    	this.idUtilisateur = rs.getInt("ID_UTILISATEUR");
                this.nom = rs.getString("NOM");
                this.prenom = rs.getString("PRENOM");
                this.telephone = rs.getString("TELEPHONE");
          
            }
     	    else { 
     	    	throw new Exception("Pseudonyme et/ou mot de passe incorrect");
     	    }
     	 }
        catch(Exception e) {
        	System.err.println("erreur"+e);
        	//Log.d("connexion","erreur"+e);    
            throw new Exception("Erreur lors de l'identification");
        }
        finally	{ 
        	try	{
              pstmt.close();
            }
        	catch (Exception e)	{ }
        }
    
    }	
    	
    	
    	
    	
    	
    
    public void rejoindreCommunaute( int idCommunaute, String password ) throws Exception {
        CallableStatement cstmt = null;
        try
        {   String query = "CALL pck_nvmembre.nouveau_membre2(?,?,?)";
            cstmt = dbConnect.prepareCall(query);
            cstmt.setInt(1,idCommunaute);
            cstmt.setInt(2,this.idUtilisateur);
            cstmt.setString(3,password);
            cstmt.executeUpdate();
        }
	catch(Exception e){
            throw new Exception("Erreur : "+e.getMessage());   
        }
    }
}