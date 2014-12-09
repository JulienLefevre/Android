package Modele;

public class Message {

    protected int idMessage;
    protected String texte;
    protected Categorie categorie;

    public Message(int idMessage, String texte, Categorie categorie) {
        this.idMessage = idMessage;
        this.texte = texte;
        this.categorie = categorie;
    }

    
    public Message() {
    }

    public Message(String texte, Categorie categorie) {
		super();
		this.texte = texte;
		this.categorie = categorie;
	}


	public Message(int idMessage) {
        this.idMessage = idMessage;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }


	@Override
	public String toString() {
		return "Message [idMessage=" + idMessage + ", texte=" + texte
				+ ", categorie=" + categorie + "]";
	}
}