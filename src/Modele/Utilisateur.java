package Modele;

public class Utilisateur {

    protected int idUtilisateur;
    protected String nom;
    protected String prenom;
    protected String telephone;
    protected String pseudonyme;
    protected String password;
    
    public Utilisateur () {
    
    }

    public Utilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Utilisateur(int idUtilisateur, String nom, String prenom, String telephone, String pseudonyme, String password) {
        this.idUtilisateur = idUtilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.pseudonyme = pseudonyme;
        this.password = password;
    }
    
    
    public Utilisateur(String nom, String prenom, String telephone, String pseudonyme, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.pseudonyme = pseudonyme;
        this.password = password;
    }
    
    public Utilisateur(String pseudonyme, String password) {
        this.pseudonyme = pseudonyme;
        this.password = password;
	}

	public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPseudonyme() {
        return pseudonyme;
    }

    public void setPseudonyme(String pseudonyme) {
        this.pseudonyme = pseudonyme;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "idUtilisateur=" + idUtilisateur + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone + ", pseudonyme=" + pseudonyme + ", password=" + password + '}';
    }
}