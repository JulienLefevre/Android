package Modele;

public class Categorie {

    protected int idCategorie;
    protected String nomCategorie;
    protected Communaute communaute;
    
    public Categorie () {
        
    }
    
    public Categorie ( int idCategorie, String nomCategorie, Communaute communaute ) {
        this.idCategorie = idCategorie;
        this.nomCategorie = nomCategorie;
        this.communaute = communaute;
    }
    public Categorie ( String nomCategorie, Communaute communaute ) {
        this.nomCategorie = nomCategorie;
        this.communaute = communaute;
    }
    public Categorie ( int idCategorie ) {
        this.idCategorie = idCategorie;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public Communaute getCommunaute() {
        return communaute;
    }

    public void setCommunaute(Communaute communaute) {
		this.communaute = communaute;
	}

	@Override
    public String toString() {
        return "Categorie{" + "idCategorie=" + idCategorie + ", nomCategorie=" + nomCategorie + ", communaute=" + communaute + '}';
    }
}