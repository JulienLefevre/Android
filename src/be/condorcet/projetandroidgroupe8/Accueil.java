package be.condorcet.projetandroidgroupe8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Accueil extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accueil);
	}
	
	public void gestionChoixCat(View view)
    {	Intent i = new Intent(Accueil.this,ChoixCategorie.class);
		startActivity(i);
		finish();
    }
	
	public void gestionRejComm(View view) {
		Toast.makeText(this, R.string.pasDvlp, Toast.LENGTH_SHORT).show();
    }
	
	public void gestionAddComm(View view) {
		Toast.makeText(this, R.string.pasDvlp, Toast.LENGTH_SHORT).show();
    }
	
	public void gestionQuitter(View view) {
        finish();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.accueil, menu);
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
}