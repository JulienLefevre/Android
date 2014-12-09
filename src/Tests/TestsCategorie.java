package Tests;

import java.sql.Connection;

import myconnections.DBConnection;
import Modele.CategorieDB;
import Modele.CommunauteDB;
import Modele.UtilisateurDB;

public class TestsCategorie {

	public void testCat() {
		DBConnection dbc = new DBConnection();
		Connection con = dbc.getConnection();
		if (con == null) {
			System.err.println("Connection impossible");
			System.exit(0);
		}

		UtilisateurDB.setConnection(con);
		CommunauteDB.setConnection(con);
		CategorieDB.setConnection(con);

		UtilisateurDB admin1 = new UtilisateurDB("Lefevre", "Julien", "02585415", "juju", "007");
		UtilisateurDB admin2 = new UtilisateurDB("Vervier", "Robin","0254854115", "R", "51854");

		
		CommunauteDB commu1 = null, commu2 = null;
		CategorieDB cat1 = null, cat2 = null, cat3 = null;

		try {
			admin1.create();
			admin1.read();

			admin2.create();
			admin2.read();

			commu1 = new CommunauteDB("Java", "Java", admin1);
			commu2 = new CommunauteDB("Java2", "Java2", admin2);

			commu1.create();
			commu1.read();

			commu2.create();
			commu2.read();

		} catch (Exception e) {

		}

		/**
		 * Test d'ajout d'une catégorie
		 */
		try {
			System.out.println("\n> Test d'ajout");
			cat1 = new CategorieDB("Rendez-vous", commu1);
			cat1.create();
			int idCat = cat1.getIdCategorie();
			cat2 = new CategorieDB(idCat);
			cat2.read();
			System.out.println("OK Catégorie : \n" + cat2 + "\nEnregistré");
		} catch (Exception e) {
			System.err.println("BAD Exception d'ajout : \n" + e);
		}
		try {
			cat1.delete();
		} catch (Exception e) {

		}

		/**
		 * Test d'ajout d'une communauté (plusieurs communautés même nom categorie)
		 */
		try {
			System.out.println("\n> test ajout (plusieurs communautés même nom categorie)");
			cat1 = new CategorieDB("Rendez-vous", commu1);
			cat1.create();
			cat1.read();
			cat2 = new CategorieDB("Rendez-vous", commu2);
			cat2.create();
			cat2.read();
			System.out.println("OK \n Catégorie 1: \n" + cat1 + "\nEnregistré");
			System.out.println("Catégorie 2: \n" + cat2 + "\nEnregistré");
		} catch (Exception e) {
			System.err.println("BAD exception d'ajout : \n" + e);
		}
		try {
			cat1.delete();
			cat2.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de doublon
		 */
		try {
			System.out.println("\n> test doublon");
			cat1 = new CategorieDB("Rendez-vous", commu1);
			cat1.create();
			cat2 = new CategorieDB("Rendez-vous", commu1);
			cat2.create();
			System.err.println("BAD Catégorie avec nom identique dans une même communauté ");
		} catch (Exception e) {
			System.out.println("OK exception normale de doublon : " + e);
		}
		try {
			cat1.delete();
			cat2.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de lecture fructueuse
		 */
		try {
			System.out.println("\n> test de lecture fructueuse");
			cat1 = new CategorieDB("Rendez-vous", commu1);
			cat1.create();
			int idCat = cat1.getIdCategorie();
			cat2 = new CategorieDB(idCat);
			cat2.read();
			System.out.println("OK Catégorie : \n" + cat2 + "\n");
		} catch (Exception e) {
			System.err.println("BAD exception de lecture : \n" + e);
		}
		try {
			cat1.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de lecture infructueuse
		 */
		try {
			System.out.println("\n> test de lecture infructueuse (catégorie inconnue)");
			cat1 = new CategorieDB(-50);
			cat1.read();
			System.err.println("BAD Catégorie trouvée : \n" + cat1 + "\n");
		} catch (Exception e) {
			System.out.println("OK exception normale de lecture : \n" + e);
		}
		
		/**
		 * Test effacement d'une catégorie
		 */
		try {
			System.out.println("\n> test d'effacement catégorie");
			cat1 = new CategorieDB("Rendez-vous", commu1);
			cat1.create();
			int numcat = cat1.getIdCategorie();
			cat1.delete();
			cat2 = new CategorieDB(numcat);
			cat2.read();
			System.err.println("BAD : Catégorie pas effacée : \n" + cat2);

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement (Erreur de lecture après l'effacement) : \n"+ e);
		}
		try {
			cat1.delete();
		 } catch (Exception e) {
		}

		/**
		 * Test de mise à jour scénario nominal
		 */
		try {
			System.out.println("\n> test mise à jour fructueux");
			cat1 = new CategorieDB("Rendez-vous", commu1);
			cat1.create();
			cat1.read();
			int numcat = cat1.getIdCategorie();
			cat1.setNomCategorie("nomCategorie");
			cat1.update();
			cat2 = new CategorieDB(numcat);
			cat2.read();
			System.out.println("Ok Cat2=" + cat2);
			} catch (Exception e) {
			System.err.println("BAD exception de mise à jour :\n" + e);
		}

		try {
			cat1.delete();
		 } catch (Exception e) {
		}

		/**
		 * Test de mise à jour (Nouveau nom existant dans même communauté)
		 */
		try {
			cat3 = new CategorieDB("Rendez-vous", commu1);
			cat3.create();
			System.out
					.println("\n> test mise à jour infructueux (Nouveau nom existant même communauté)");
			cat1 = new CategorieDB("Fête", commu1);
			cat1.create();
			cat1.read();
			int numcat = cat1.getIdCategorie();
			cat1.setNomCategorie("Rendez-vous");
			cat1.update();
			cat2 = new CategorieDB(numcat);
			cat2.read();
			System.err.println("BAD Catégorie avec nom identique");
		} catch (Exception e) {
			System.out.println("OK exception normale de doublon : " + e);
		}

		try {
			cat1.delete();
			cat3.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de mise à jour (Nouveau nom existant dans des communautés différentes)
		 */
		try {
			System.out.println("\n> test mise à jour fructueux (Nouveau nom existant dans des communautés différentes)");
			cat3 = new CategorieDB("Rendez-vous", commu2);
			cat3.create();
			cat1 = new CategorieDB("Fête", commu1);
			cat1.create();
			cat1.read();
			int numcat = cat1.getIdCategorie();
			cat1.setNomCategorie("Rendez-vous");
			cat1.update();

			cat2 = new CategorieDB(numcat);
			cat2.read();
			System.out.println("OK cat2=" + cat2);
		} catch (Exception e) {
			System.err.println("BAD exception de mise à jour :\n" + e);
		}

		try {
			cat1.delete();
			cat3.delete();
		} catch (Exception e) {
		}

		try {
			admin1.delete();
			admin2.delete();
			commu1.delete();
			commu2.delete();
			con.close();
		} catch (Exception e) {

		}
	}

}
