package Tests;

import java.sql.Connection;
import java.util.ArrayList;

import myconnections.DBConnection;
import Modele.Communaute;
import Modele.CommunauteDB;
import Modele.UtilisateurDB;

public class TestsUtilisateur {

	public void testUti() {
		DBConnection dbc = new DBConnection();
		Connection con = dbc.getConnection();
		if (con == null) {
			System.err.println("connexion impossible");
			System.exit(0);
		}
		UtilisateurDB.setConnection(con);
		UtilisateurDB u1 = null, u2 = null, u3 = null;

		/**
		 * Test d'ajout d'un utilisateur
		 */
		try {
			System.out.println("\n> test ajout");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();
			int numUtilisateur = u1.getIdUtilisateur();
			u2 = new UtilisateurDB(numUtilisateur);
			u2.read();
			System.out.println("OK utilisateur : \n" + u2 + "\nenregistré");
		} catch (Exception e) {
			System.err.println("BAD exception d'ajout : \n" + e);
		}
		try {
			u1.delete();
		   } catch (Exception e) {
		}
		/**
		 * Test d'ajout d'un utilisateur (pseudo / tel différents)
		 */
		try {
			System.out.println("\n> test ajout (pseudo / tel différents)");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();
			u1.read();
			u2 = new UtilisateurDB("Dupont", "Jules", "065123455", "DJules","tests");
			u2.create();
			u2.read();
			System.out.println("OK \n Utilisateur 1: \n" + u1 + "\nEnregistré");
			System.out.println("Utilisateur 2: \n" + u2 + "\nEnregistré");
		} catch (Exception e) {
			System.err.println("BAD exception d'ajout : \n" + e);
		}
		try {
			u1.delete();
			u2.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de doublon
		 */
		try {
			System.out.println("\n> test doublon");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();
			u2 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u2.create();
			System.err.println("BAD utilisateur entré 2x ");
		} catch (Exception e) {
			System.out.println("OK exception normale de doublon : " + e);
		}
		try {
			u1.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de lecture fructueuse
		 */
		try {
			System.out.println("\n> test de lecture fructueuse");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();
			int numUtilisateur = u1.getIdUtilisateur();
			u2 = new UtilisateurDB(numUtilisateur);
			u2.read();
			System.out.println("OK Utilisateur : \n" + u2 + "\n");
		} catch (Exception e) {
			System.err.println("BAD exception de lecture : \n" + e);
		}
		try {
			u1.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de lecture infructueuse
		 */
		try {
			System.out.println("\n> test de lecture infructueuse (utilisateur inconnu)");
			u1 = new UtilisateurDB(-50);
			u1.read();
			System.err.println("BAD Utilisateur trouvé : \n" + u1 + "\n");
		} catch (Exception e) {
			System.out.println("OK exception normale de lecture : \n" + e);
		}
		

		/**
		 * Test effacement d'un utilisateur
		 */
		try {
			System.out.println("\n> test d'effacement d'un utilisateur");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();
			int numU = u1.getIdUtilisateur();
			u1.delete();
			u2 = new UtilisateurDB(numU);
			u2.read();
			System.err.println("BAD : Utilisateur pas effacé : \n" + u2);

		} catch (Exception e) {
			System.out.println("OK exception normale d'effacement (Erreur de lecture après l'effacement) : \n"+ e);
		}
		try {
			u1.delete();
		} catch (Exception e) {
		}

		/**
		 * Test de mise à jour fructueux
		 */
		try {
			System.out.println("\n> test mise à jour fructueux");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();
			int numUti = u1.getIdUtilisateur();
			u1.setNom("nouvnom");
			u1.setPrenom("nouvprenom");
			u1.setTelephone("nouvtel");
			u1.setPseudonyme("nouvpseudo");
			u1.setPassword("nouvpassword");
			u1.update();
			u2 = new UtilisateurDB(numUti);
			u2.read();
			System.out.println("Ok u2=" + u2);
		} catch (Exception e) {
			System.err.println("BAD exception de mise à jour :\n" + e);
		}

		try {
			u1.delete();
		} catch (Exception e) {
		}

		/**
		 * Test mise à jour infructueux (Nouveau pseudo existant )
		 */
		try {
			System.out.println("\n> test mise à jour infructueux (Nouveau pseudo existant)");
			
			u3 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u3.create();
			u1 = new UtilisateurDB("Dupond", "Jules", "065123448", "DJules","tests");
			u1.create();
			u1.read();
			int numuti = u1.getIdUtilisateur();
			u1.setPseudonyme("DJule");
			u1.update();
			u2 = new UtilisateurDB(numuti);
			u2.read();
			System.err.println("BAD utilisateur avec pseudo identique");
		} catch (Exception e) {
			System.out.println("OK exception normale de doublon : " + e);
		}

		try {
			u1.delete();
			u3.delete();
		   } catch (Exception e) {
		}

		/**
		 * ------- Tests Communaute - Utilisateur -----------
		 */
		CommunauteDB.setConnection(con);
		UtilisateurDB adm = new UtilisateurDB("Lenoir", "Marc", "065122555","LMarc", "azerty");
		UtilisateurDB adm2 = new UtilisateurDB("Leblanc", "Yves", "065144555", "LYves", "qwerty");
		UtilisateurDB adm3 = new UtilisateurDB("Lerouge", "Marie", "066144555",	"LM", "nbvcx");
		CommunauteDB comm1 = null, comm2 = null, comm3 = null;

		try {
			adm.create();
			adm.read();
			adm2.create();
			adm2.read();
			adm3.create();
			adm3.read();

			comm1 = new CommunauteDB("Java", "Java", adm);
			comm2 = new CommunauteDB("Java2", "Java2", adm2);
			comm3 = new CommunauteDB("Java3", "Java3", adm);
			
			comm1.create();
			comm1.read();
			comm2.create();
			comm2.read();
			comm3.create();
			comm3.read();

		} catch (Exception e) {
		}

		/**
		 * Test rejoindre communauté scénario nominal
		 */

		try {
			System.out.println("\n\n> test rejoindre une communauté (fructueux)");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule", "tests");
			u1.create();
			u1.rejoindreCommunaute(comm1.getIdCommunaute(), "Java");
			ArrayList<Communaute> communities = new ArrayList<Communaute>();
			communities = u1.mesCommunautes();
			System.out.println("OK : u1 : " + u1 + " - appartient à la communauté : ");
			for (Communaute comm : communities) {
				System.out.println(" - " + comm);
			}

		} catch (Exception e) {
			System.err.println("BAD exception de mise à jour :\n" + e);
		}

		try {
			u1.delete();

		} catch (Exception e) {
		}
		
		/**
		 * Test rejoindre communauté (Mot de passe communaute incorrect)
		 */

		try {
			System.out.println("\n\n> test rejoindre une communauté (Mot de passe incorrect)");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();

			u1.rejoindreCommunaute(comm1.getIdCommunaute(), "blabla");
			ArrayList<Communaute> communities = new ArrayList<Communaute>();
			communities = u1.mesCommunautes();
			System.err.println("BAD : Identification a échoué u1 : appartient à la communauté : ");
			for (Communaute comm : communities) {
				System.err.println(" - " + comm);
			}
		} catch (Exception e) {
			System.out.println("OK exception normale :\n" + e);
		}

		try {
			u1.delete();

		} catch (Exception e) {
		}

		/**
		 * Test identification scénario nominal
		 */

		try {
			System.out.println("\n\n> test identification (fructueux)");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();
			u2 = new UtilisateurDB("DJule", "tests");
			u2.Identification();
			u2.read();
			System.out.println("ok : u2 : " + u2);

		} catch (Exception e) {
			System.err.println("BAD exception identification :\n" + e);
		}

		try {
			u1.delete();
		} catch (Exception e) {
		}

		/**
		 * Test identification (mot de passe incorrect)
		 */
		try {
			System.out.println("\n\n> test identification (Mot de passe incorrect)");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();
			u2 = new UtilisateurDB("DJule", "blabla");
			u2.Identification();
			u2.read();
			System.err.println("BAD : Identification a échoué");
		} catch (Exception e) {
			System.out.println("OK exception normale :\n" + e);
		}

		try {
			u1.delete();

		} catch (Exception e) {
		}

		/**
		 * Test identification (pseudo incorrect)
		 */

		try {
			System.out.println("\n\n> test identification (pseudo incorrect)");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();
			u2 = new UtilisateurDB("Blabla", "tests");
			u2.Identification();
			u2.read();
			System.err.println("BAD : Identification a échoué");
		} catch (Exception e) {
			System.out.println("OK exception normale :\n" + e);
		}

		try {
			u1.delete();
		} catch (Exception e) {
		}

		/**
		 * Test voir mes communautés
		 */

		try {
			System.out.println("\n\n> test voir mes communautés (fructueux)");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();

			u1.rejoindreCommunaute(comm1.getIdCommunaute(), "Java");
			u1.rejoindreCommunaute(comm2.getIdCommunaute(), "Java2");
			u1.rejoindreCommunaute(comm3.getIdCommunaute(), "Java3");

			ArrayList<Communaute> communities = new ArrayList<Communaute>();
			communities = u1.mesCommunautes();
			System.out.println("OK : u1 : " + u1 + " - appartient à la communauté : ");
			for (Communaute comm : communities) {
				System.out.println(" - " + comm);
			}

		} catch (Exception e) {
			System.err.println("BAD exception identification :\n" + e);
		}

		try {
			u1.delete();

		} catch (Exception e) {
		}

		/**
		 * Test voir mes communautés (pas de communauté)
		 */

		try {
			System.out.println("\n\n> test voir mes communautés (pas de communauté)");
			u1 = new UtilisateurDB("Dupont", "Jules", "065123456", "DJule",	"tests");
			u1.create();

			ArrayList<Communaute> communities = new ArrayList<Communaute>();
			communities = u1.mesCommunautes();
			System.err.println("BAD u1 apartient à la communauté : ");
			for (Communaute comm : communities) {
				System.err.println(" - " + comm);
			}
		} catch (Exception e) {
			System.out.println("OK exception normale :\n" + e);
		}

		try {
			u1.delete();

		} catch (Exception e) {
		}

		/**
		 * Test voir mes communautés administrées
		 */

		try {
			System.out
					.println("\n\n> test voir mes communautés administrée (fructueux)");
			ArrayList<Communaute> communities = new ArrayList<Communaute>();
			communities = adm.mesCommunautesAdministrees();
			System.out.println("OK : adm : " + adm + " administre : ");
			for (Communaute comm : communities) {
				System.out.println(" - " + comm);
			}

		} catch (Exception e) {
			System.err.println("BAD exception communaute non trouvée :\n" + e);
		}

		try {
			u1.delete();

		} catch (Exception e) {
		}

		/**
		 * Test voir mes communautés administrées (pas de communauté)
		 */

		try {
			System.out.println("\n\n> test voir mes communautés administrée (pas de communauté)");
			ArrayList<Communaute> communities = new ArrayList<Communaute>();
			communities = adm3.mesCommunautesAdministrees();
			System.err.println("BAD communauté administré par adm3 trouvée : ");
			for (Communaute comm : communities) {
						System.err.println(" - " + comm);
				}
		} catch (Exception e) {
			System.out.println("OK exception normale :\n" + e);
		}

		try {
			u1.delete();

		} catch (Exception e) {
		}

		try {
			comm1.delete();
			comm2.delete();
			comm3.delete();
			adm.delete();
			adm2.delete();
			adm3.delete();
		} catch (Exception e) {
		}

		try {

			con.close();
		} catch (Exception e) {
		}
	}

}
