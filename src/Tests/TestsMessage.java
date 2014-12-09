package Tests;

import java.sql.Connection;

import myconnections.DBConnection;
import Modele.CategorieDB;
import Modele.CommunauteDB;
import Modele.MessageDB;
import Modele.UtilisateurDB;

public class TestsMessage {

	public void testMsg() {
		DBConnection dbc = new DBConnection();
		Connection con = dbc.getConnection();
		if (con == null) {
			System.err.println("Connection impossible");
			System.exit(0);
		}

		MessageDB.setConnection(con);
		UtilisateurDB.setConnection(con);
		CommunauteDB.setConnection(con);
		CategorieDB.setConnection(con);

		UtilisateurDB admin1 = new UtilisateurDB("Lefevre", "Julien", "02585415", "juju", "007");
		
		CommunauteDB commu1 = null;
		CategorieDB cat1 = null, cat2 = null;
		MessageDB msg1 = null, msg2 = null, msg3 = null;

		try {
			admin1.create();
			admin1.read();

			commu1 = new CommunauteDB("Java", "Java", admin1);
			commu1.create();
			commu1.read();
			
			cat1 = new CategorieDB("Rendez-vous", commu1);
			cat1.create();
			cat1.read();
			
			cat2 = new CategorieDB("Retard", commu1);
			cat2.create();
			cat2.read();

			
		} catch (Exception e) {
		}

		/**
		 * Test d'ajout d'un message
		 */

		try {
			System.out.println("\n> test d'ajout");
			msg1 = new MessageDB("blablabla $$ blabla $$", cat1);
			msg1.create();
			msg2 = new MessageDB(msg1.getIdMessage());
			msg2.read();
			System.out.println("OK Message : \n" + msg2 + "\nEnregistré");
		} catch (Exception e) {
			System.err.println("BAD Exception d'ajout : \n" + e);
		}
		try {
			msg1.delete();
			msg2.delete();
		} catch (Exception e) {

		}
		
		
		/**
		 * Test de doublon
		 */
		try {
			System.out.println("\n> test doublon");
			msg1 = new MessageDB("blablabla $$ blabla $$", cat1);
			msg1.create();
			msg2 = new MessageDB("blablabla $$ blabla $$", cat1);
			msg2.create();
			System.err.println("BAD Message identique dans la même catégorie ");
		} catch (Exception e) {
			System.out.println("OK exception normale de doublon : " + e);
		}
		try {
			msg1.delete();
			} catch (Exception e) {
		}
		
		/**
		 * Test de lecture fructueuse
		 */
		try {
			System.out.println("\n> test de lecture fructueuse");
			msg1 = new MessageDB("blablabla $$ blabla $$", cat1);
			msg1.create();
			int nummsg = msg1.getIdMessage();
			msg2 = new MessageDB(nummsg);
			msg2.read();
			System.out.println("OK message : \n" + msg2 + "\n");
		} catch (Exception e) {
			System.err.println("BAD exception de lecture : \n" + e);
		}
		try {
			msg1.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de lecture infructueuse
		 */
		try {
			System.out.println("\n> test de lecture infructueuse (message inconnu)");
			msg1 = new MessageDB(-50);
			msg1.read();
			System.err.println("BAD message trouvé : \n" + msg1 + "\n");
		} catch (Exception e) {
			System.out.println("OK exception normale de lecture : \n" + e);
		}
		
		
		/**
		 * Test effacement d'un message
		 */
		try {
			System.out.println("\n> test d'effacement message");
			msg1 = new MessageDB("blablabla $$ blabla $$", cat1);
			msg1.create();
			int nummsg = msg1.getIdMessage();
			msg1.delete();
			msg2 = new MessageDB(nummsg);
			msg2.read();
			System.err.println("BAD : Message pas effacé : \n" + cat2);

		} catch (Exception e) {
			System.out.println( "OK exception normale d'effacement (Erreur de lecture après l'effacement) : \n"+ e);
		}
				
		
		 /**
         * Test de mise à jour message scénario nominal
         */
        try {
            System.out.println("\n> test mise à jour fructueux");
            msg1 = new MessageDB("blablabla $$ blabla $$", cat1);
            msg1.create();
            int nummsg = msg1.getIdMessage();
            msg1.setTexte("NVMessage");
            msg1.update();
            msg2 = new MessageDB(nummsg);
            msg2.read();
            System.out.println("OK msg2=" + msg2);
          } 
        catch (Exception e) {
            System.err.println("BAD exception de mise à jour :\n" + e);
        }
        try {
        	msg1.delete(); 
        } 
        catch (Exception e) {
        }
               
        /**
         * Test de mise à jour (texte du message existant dans la catégorie)
         */
        try {
        	msg3 = new MessageDB("NVMessage", cat1);
        	msg3.create();
            System.out.println("\n> test mise à jour infructueux (texte du message existant dans la catégorie)");
            msg1 = new MessageDB("blablabla $$ blabla $$", cat1);
            msg1.create();
            int nummsg = msg1.getIdMessage();
            msg1.setTexte("NVMessage");
            msg1.update();
            msg2 = new MessageDB(nummsg);
            msg2.read();
            System.err.println("BAD doublon message ");
        } 
        catch (Exception e) {
        	System.out.println("OK exception normale de doublon : " + e);
        }
        
        try {
        	msg1.delete(); 
        	msg3.delete();
        	} 
        catch (Exception e) {
        }
		
        /**
         * Test de mise à jour fructueux (texte du message existant dans une autre catégorie)
         */
        try {
        	msg3 = new MessageDB("NVMessage", cat2);
        	msg3.create();
            System.out.println("\n> test mise à jour fructueux (texte du message existant dans une autre catégorie)");
            msg1 = new MessageDB("blablabla $$ blabla $$", cat1);
            msg1.create();
            int nummsg = msg1.getIdMessage();
            msg1.setTexte("NVMessage");
            msg1.update();
            msg2 = new MessageDB(nummsg);
            msg2.read();
            System.out.println("OK msg 2=" + msg2);
        } 
        catch (Exception e) {
            System.err.println("BAD exception de mise à jour :\n" + e);
        }
        
        try {
        	msg1.delete(); 
        	msg3.delete();
        	} 
        catch (Exception e) {
        }
		
		

		try {
			admin1.delete();
			commu1.delete();
			cat1.delete();
			cat2.delete();
			con.close();
		} catch (Exception e) {
		}
	}
}
