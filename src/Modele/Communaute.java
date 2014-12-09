package Modele;

import java.util.ArrayList;

public class Communaute {

    protected int idCommunaute;
    protected String nomCommunaute;
    protected String password;
    protected Utilisateur administrateur;
    protected ArrayList <Utilisateur> membres;
    
    public Communaute () {
    
    }
    
    public Communaute ( int idCommunaute ) {
        this.idCommunaute = idCommunaute;
    }
    
    public Communaute ( int idCommunaute, String nomCommunaute, String password, Utilisateur administrateur ) {
        this.administrateur = administrateur;
        this.idCommunaute = idCommunaute;
        this.password = password;
        this.nomCommunaute = nomCommunaute;
    }
public Communaute (String nomCommunaute, String password, Utilisateur administrateur ) {
        this.administrateur = administrateur;
        this.password = password;
        this.nomCommunaute = nomCommunaute;
    }
    public int getIdCommunaute() {
        return idCommunaute;
    }

    public void setIdCommunaute(int idCommunaute) {
        this.idCommunaute = idCommunaute;
    }

    public String getNomCommunaute() {
        return nomCommunaute;
    }

    public void setNomCommunaute(String nomCommunaute) {
        this.nomCommunaute = nomCommunaute;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Utilisateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Utilisateur administrateur) {
        this.administrateur = administrateur;
    }

    public ArrayList<Utilisateur> getMembres() {
        return membres;
    }

    public void setMembres(ArrayList<Utilisateur> membres) {
        this.membres = membres;
    }
    
    @Override
    public String toString() {
        return "Communaute{" + "idCommunaute=" + idCommunaute + ", nomCommunaute=" + nomCommunaute + ", password=" + password + ", administrateur=" + administrateur + '}';
    }
}