package Modele;

//import static Modele.CategorieDB.dbConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.ResultSet;
//import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//import android.util.Log;

public class CommunauteDB extends Communaute implements CRUD {
    
    protected static Connection dbConnect = null;
    
    public static void setConnection(Connection nouvdbConnect) {
      dbConnect = nouvdbConnect;
    }
/*test */
    public CommunauteDB() {
    }

    public CommunauteDB(int idCommunaute) {
        super(idCommunaute);
    }

    public CommunauteDB(int idCommunaute, String nomCommunaute, String password, Utilisateur administrateur) {
        super(idCommunaute, nomCommunaute, password, administrateur);
    }
    
    public CommunauteDB( String nomCommunaute, String password, Utilisateur administrateur) {
        super( nomCommunaute, password, administrateur);
    }

    @Override
    public void create() throws Exception {
       CallableStatement cstmt = null;
       try
       {  String query = "CALL CREATE_COMMUNAUTE(?,?,?,?)";
          cstmt = dbConnect.prepareCall(query);
          cstmt.registerOutParameter(1,java.sql.Types.INTEGER);
          cstmt.setString(2,this.nomCommunaute);
          cstmt.setString(3,this.password);
          cstmt.setInt(4, this.administrateur.getIdUtilisateur());
          cstmt.executeUpdate();
          this.idCommunaute = cstmt.getInt(1); 
       }
       catch(Exception e ) {
          throw new Exception("Erreur de création "+e.getMessage());
       }   
    }

    @Override
    public void update() throws Exception {
        CallableStatement cstmt = null;
        try
        {   String query = "CALL UPDATE_COMMUNAUTE(?,?,?)";
            cstmt = dbConnect.prepareCall(query);
            cstmt.setInt(1,this.idCommunaute);
            cstmt.setString(2,this.nomCommunaute);
            cstmt.setString(3,this.password);
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
        {   String query = "CALL DELETE_COMMUNAUTE(?)";
            cstmt = dbConnect.prepareCall(query);
            cstmt.setInt(1,this.idCommunaute);
            cstmt.executeUpdate();
        }
	catch(Exception e){
            throw new Exception("Echec de la suppression : "+e.getMessage());   
        }
    }

    @Override
    public void read() throws Exception {
    	String req = "SELECT * FROM VUEUTIL_COMM WHERE ID_COMMUNAUTE = ?"; 
        PreparedStatement pstmt = null;
        try
        {	pstmt=dbConnect.prepareStatement(req);
            pstmt.setInt(1,this.idCommunaute);
     	    ResultSet rs= (ResultSet)pstmt.executeQuery();	
        	if(rs.next()) {
     	    	this.nomCommunaute = rs.getString("NOM_COMMUNAUTE");
                this.password = rs.getString("PASSCOMM");
                int idAdm = rs.getInt("ADMINISTRATEUR");
                String nomAdm = rs.getString("NOM");
                String prnom = rs.getString("PRENOM");
                String tel =  rs.getString("TELEPHONE");
                String passAdm =  rs.getString("PASSWORD");
                String pseudo =  rs.getString("PSEUDO");
                
                this.administrateur = new UtilisateurDB(idAdm, nomAdm, prnom, tel, pseudo, passAdm);
                
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
    
    public ArrayList <Utilisateur> membresCommunauté () throws Exception {
    	ArrayList <Utilisateur> membresCommunaute = new ArrayList <Utilisateur> ();
        Utilisateur user;
    	String req = "SELECT * FROM MEMBRE WHERE COMMUNAUTE = ?"; 
        PreparedStatement pstmt = null;
        
        try
        {  boolean trouvé = false;
	       pstmt = dbConnect.prepareStatement(req);
	       pstmt.setInt(1,this.idCommunaute);
	 	   ResultSet rs = (ResultSet)pstmt.executeQuery();	
           while (rs.next()) {
              trouvé = true; 
              user = new UtilisateurDB(rs.getInt("UTILISATEUR"));
              ((CRUD)user).read();
              membresCommunaute.add(user);
           }
           if ( !trouvé )
               throw new Exception("Aucun membre dans la communauté");
           else 
               return membresCommunaute;
        }
        catch ( Exception e ) {
           throw new Exception(e.getMessage());      
        }
    }
}