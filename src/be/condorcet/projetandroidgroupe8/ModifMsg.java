package be.condorcet.projetandroidgroupe8;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ModifMsg extends ActionBarActivity {

	private String message;
	private int nombreChamps;
	private TextView textViewMessage;
	private TextView textViewChamp;
	private EditText editText;
	private EditText editTextTel;
	private int curseurModification;
	private Button Env, Modif;
	private Intent intent;
	private SauvMessage sauvegarde;
	private int idCat;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modif_msg);

		Env = (Button) findViewById(R.id.boutonEnvoyer);
		Modif = (Button) findViewById(R.id.boutonModifier);

		editTextTel = (EditText) findViewById(R.id.editTextTel);

		textViewMessage = (TextView) findViewById(R.id.textViewMessage);
		textViewChamp = (TextView) findViewById(R.id.textViewChamp);
		editText = (EditText) findViewById(R.id.editTextChamp);
		
		curseurModification = 0;
            intent = getIntent();
			message = intent.getStringExtra("MESSAGE");
			idCat = Integer.parseInt(intent.getStringExtra("IDCAT"));
			message = numerotationChamps(message);
		    sauvegarde = (SauvMessage) getLastCustomNonConfigurationInstance();
		
		if (sauvegarde == null) {
			 sauvegarde = new SauvMessage();
			 
			 
		} else {
			editTextTel.setText(sauvegarde.getTel());
			
			for(String m : sauvegarde.getListModif()){
				modif(m);
			}
			Log.e("Verifff",message);
			//message = sauvegarde.getTexte();
		}
           textViewMessage.setText(message);
		     // textViewMessage.setText(message + "\nNombre de champs : " +  nombreChamps);
		     textViewChamp.setText(""+(getResources().getString(R.string.numChamp)) + (curseurModification + 1));	
		
			
	}

	public void gestionRetour(View view){
		Intent i = new Intent(ModifMsg.this,ChoixMessage.class);						
		i.putExtra("idCategorie",""+idCat);
		startActivity(i);
		finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.modif_msg, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public String numerotationChamps(String phrase) {
		String phraseSplitted[] = phrase.split("[~]");
		String nouvellePhrase = "";
		this.nombreChamps = phraseSplitted.length - 1;

		for (int i = 0; i < phraseSplitted.length - 1; i++)
			nouvellePhrase += phraseSplitted[i] + i;
		nouvellePhrase += phraseSplitted[phraseSplitted.length - 1];
		return nouvellePhrase;
	}

	public void ModificationMessage(View view) {	 
		sauvegarde.setTel(editTextTel.getText().toString());
		modif(editText.getText().toString());
	    sauvegarde.addListModif(editText.getText().toString());
	    editText.setText("");
	 }
	
public void modif(String Champ) {
		message = message.replace("[" + (curseurModification) + "]", Champ);
		
		textViewMessage.setText(message);
		curseurModification++;
		textViewChamp.setText(""+(getResources().getString(R.string.numChamp))  + (curseurModification + 1));

		if (curseurModification == nombreChamps) {
			editText.setVisibility(View.INVISIBLE);
			textViewChamp.setVisibility(View.INVISIBLE);
			Env.setEnabled(true);
			Modif.setEnabled(false);
		}
		
	}
	
	
	public Object onRetainCustomNonConfigurationInstance() {
	     return sauvegarde;
		}
	
	public void EnvoiMessage(View view) {
			 //Toast.makeText(this, R.string.pasDvlp,Toast.LENGTH_SHORT).show();
			 Uri sms= Uri.parse("sms:"+editTextTel.getText()+"?body="+message);
			 Intent secondeActivite = new Intent(Intent.ACTION_SENDTO, sms);
			 startActivity(secondeActivite);
		
	}
}