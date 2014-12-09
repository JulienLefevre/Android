package be.condorcet.projetandroidgroupe8;

import java.util.ArrayList;

public class SauvMessage {
	String tel;
    ArrayList<String> listModif;

	public SauvMessage() {
		super();
		this.tel = " ";
		listModif = new ArrayList<String>();
	}
	
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public ArrayList<String> getListModif() {
	return listModif;
    }


	public void addListModif(String m) {
		listModif.add(m);
	}


}
