package be.condorcet.projetandroidgroupe8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NouveauMessage extends ActionBarActivity {
	private EditText editText;
	private int idCategorie;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nouveau_message);
		editText = (EditText) findViewById(R.id.editTextNvMsg);
		
		Intent i = getIntent();
		idCategorie = Integer.parseInt(i.getStringExtra("idCategorie"));
		Toast.makeText(this, Integer.toString(idCategorie), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nouveau_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public void GestTilda(View view) {
		String msg = ""+editText.getText().toString();
		editText.setText(msg + " [~] ");
	}
	
	public void EnrMessage(View view) {
		String msg = ""+editText.getText().toString();
		Toast.makeText(this, getResources().getString(R.string.estEnr) + msg , Toast.LENGTH_SHORT).show();
    }
	
	public void gestionRetourAcc(View view){
		Intent i = new Intent(NouveauMessage.this,Accueil.class);						
		startActivity(i);
		finish();
	}
}
