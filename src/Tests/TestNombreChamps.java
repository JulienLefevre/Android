package Tests;

import java.sql.Connection;
import java.util.ArrayList;

import myconnections.DBConnection;
import Modele.CategorieDB;
import Modele.CommunauteDB;
import Modele.MessageDB;
import Modele.UtilisateurDB;

public class TestNombreChamps {

	public static void main(String[] args) {
		DBConnection dbc = new DBConnection();
		Connection con = dbc.getConnection();
		if (con == null) {
			System.err.println("Connection impossible");
			System.exit(0);
		}

		UtilisateurDB.setConnection(con);
		CommunauteDB.setConnection(con);
		CategorieDB.setConnection(con);
		
		ArrayList <MessageDB> messages = new ArrayList <MessageDB> ();
		
		try 
		{ 	/*messages = MessageDB.readMessages(21);
			for ( MessageDB m : messages )
			{	System.out.println(m.getTexte());
			
			}*/
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		

	}

}
