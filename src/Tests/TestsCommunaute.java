package Tests;

import java.sql.Connection;

import myconnections.DBConnection;
import Modele.CommunauteDB;
import Modele.UtilisateurDB;

public class TestsCommunaute {

	public void testComm() {
		DBConnection dbc = new DBConnection();
		Connection con = dbc.getConnection();
		if (con == null) {
			System.err.println("connexion impossible");
			System.exit(0);
		}
		UtilisateurDB.setConnection(con);
		UtilisateurDB uti = new UtilisateurDB("Dupont", "Jules", "065123456","DJule", "tests");
		UtilisateurDB uti2 = new UtilisateurDB("Dupont2", "Jules2", "0655656",	"DJule2", "tests2");
		CommunauteDB.setConnection(con);
		CommunauteDB comm1 = null, comm2 = null, comm3 = null;

		try {
			uti.create();
			uti.read();
			uti2.create();
			uti2.read();
		} catch (Exception e) {
		}

		/**
		 * Test d'ajout d'une communauté
		 */
		try {
			System.out.println("\n> test ajout");
			comm1 = new CommunauteDB("Java", "Java", uti);
			comm1.create();
			int numComm = comm1.getIdCommunaute();
			comm2 = new CommunauteDB(numComm);
			comm2.read();
			System.out.println("OK Communauté : \n" + comm2 + "\nEnregistré");
		} catch (Exception e) {
			System.err.println("BAD exception d'ajout : \n" + e);
		}

		try {
			comm1.delete();
		} catch (Exception e) {
		}

		/**
		 * Test d'ajout d'une communauté (nom de la communauté)
		 */
		try {
			System.out.println("\n> test ajout (nom de la communauté différent)");
			comm1 = new CommunauteDB("Java", "Java", uti);
			comm1.create();
			comm1.read();
			comm2 = new CommunauteDB("Java2", "Java", uti);
			comm2.create();
			comm2.read();
			System.out.println("OK \n Communauté 1: \n" + comm1 + "\nEnregistré");
			System.out.println("Communauté 2: \n" + comm2 + "\nEnregistré");
		} catch (Exception e) {
			System.err.println("BAD exception d'ajout : \n" + e);
		}
		try {
			comm1.delete();
			comm2.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de doublon
		 */
		try {
			System.out.println("\n> test doublon");
			comm1 = new CommunauteDB("Java", "Java", uti);
			comm1.create();
			comm2 = new CommunauteDB("Java", "Java", uti);
			comm2.create();
			System.err.println("BAD Communauté avec nom identique ");
		} catch (Exception e) {
			System.out.println("OK exception normale de doublon : " + e);
		}
		try {
			comm1.delete();
			comm2.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de lecture fructueuse
		 */
		try {
			System.out.println("\n> test de lecture fructueuse");
			comm1 = new CommunauteDB("Java", "Java", uti);
			comm1.create();
			int numComm = comm1.getIdCommunaute();
			comm2 = new CommunauteDB(numComm);
			comm2.read();
			System.out.println("OK Communauté : \n" + comm2 + "\n");
		} catch (Exception e) {
			System.err.println("BAD exception de lecture : \n" + e);
		}
		try {
			comm1.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de lecture infructueuse
		 */
		try {
			System.out.println("\n> test de lecture infructueuse (communauté inconnue)");
			comm1 = new CommunauteDB(-50);
			comm1.read();
			System.err.println("BAD Communauté trouvée : \n" + comm1 + "\n");
		} catch (Exception e) {
			System.out.println("OK exception normale de lecture : \n" + e);
		}

		/**
		 * Test effacement d'une communauté
		 */
		try {
			System.out.println("\n> test d'effacement communauté");
			comm1 = new CommunauteDB("Java", "Java", uti);
			comm1.create();
			int numcomm = comm1.getIdCommunaute();
			comm1.delete();
			comm2 = new CommunauteDB(numcomm);
			comm2.read();
			System.err.println("BAD : Communauté pas effacée : \n" + comm2);

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement (Erreur de lecture après l'effacement) : \n"+ e);
		}
		try {
			comm1.delete();
		  } catch (Exception e) {
		}

		/**
		 * Test de mise à jour scénario nominal
		 */
		try {
			System.out.println("\n> test mise à jour fructueux");
			comm1 = new CommunauteDB("Java", "Java", uti);
			comm1.create();
			int numcom = comm1.getIdCommunaute();
			comm1.setNomCommunaute("nomCommunaute");
			comm1.setPassword("password");
			comm1.update();
			comm2 = new CommunauteDB(numcom);
			comm2.read();
			System.out.println("Ok comm2=" + comm2);
		} catch (Exception e) {
			System.err.println("BAD exception de mise à jour :\n" + e);
		}
		
		try {
			comm1.delete();
		  } catch (Exception e) {
		}

		/**
		 * Test de mise à jour (Nouveau nom existant)
		 */
		try {
			System.out.println("\n> test mise à jour infructueux (Nouveau nom existant)");
			
			comm3 = new CommunauteDB("Java2", "Java", uti);
			comm3.create();
			
			comm1 = new CommunauteDB("Java", "Java", uti);
			comm1.create();
			int numcom = comm1.getIdCommunaute();
			comm1.setNomCommunaute("Java2");
			comm1.setPassword("password");
			comm1.update();

			comm2 = new CommunauteDB(numcom);
			comm2.read();
			System.err.println("BAD Communaute avec nom identique");
		} catch (Exception e) {
			System.out.println("OK exception normale de doublon : " + e);
		}

		try {
			comm1.delete();
			comm3.delete();
		} catch (Exception e) {
		}

		try {
			uti.delete();
			uti2.delete();
			con.close();
		} catch (Exception e) {
		}
	}

}
