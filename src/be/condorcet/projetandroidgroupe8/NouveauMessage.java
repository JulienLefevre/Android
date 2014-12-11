package be.condorcet.projetandroidgroupe8;

import java.sql.Connection;

import be.condorcet.projetandroidgroupe8.ChoixCommunaute.MyAccesDB;
import myconnections.DBConnection;
import Modele.MessageDB;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NouveauMessage extends ActionBarActivity {
	private EditText editText;
	private int idCategorie;
	private Connection con = null;
	private String msg = "";
	private String retour;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nouveau_message);
		editText = (EditText) findViewById(R.id.editTextNvMsg);
		
		Intent i = getIntent();
		idCategorie = Integer.parseInt(i.getStringExtra("idCategorie"));
		MyAccesDB adb = new MyAccesDB(NouveauMessage.this);
		adb.execute();
		//Toast.makeText(this, Integer.toString(idCategorie), Toast.LENGTH_SHORT).show();
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
		msg = ""+editText.getText().toString();
		editText.setText(msg + " [~] ");
		msg = ""+editText.getText().toString();
	}
	
	public void EnrMessage(View view) {
		msg = ""+editText.getText().toString();
		try	{
		    MessageDB messag = new MessageDB(msg,idCategorie);
		    messag.create();	
		Toast.makeText(this, getResources().getString(R.string.estEnr) + msg , Toast.LENGTH_SHORT).show();
		}
		catch(Exception e) {
			if(retour.substring(0, 9).equals("ORA-00001")){
				Toast.makeText(this, getResources().getString(R.string.errDoublMsg) + msg , Toast.LENGTH_SHORT).show();
			Log.e("erreur",retour);
			}
			
		}	
		
	}
	
	public void gestionRetourAcc(View view){
		Intent i = new Intent(NouveauMessage.this,Accueil.class);						
		startActivity(i);
		finish();
	}
	
class MyAccesDB extends AsyncTask <String,Integer,Boolean> {
	    
		private String resultat = "";
		private ProgressDialog pgd = null;

		public MyAccesDB (NouveauMessage pActivity) { }

		private void link (NouveauMessage pActivity) { }

		protected void onPreExecute(){
			super.onPreExecute();
			pgd = new ProgressDialog(NouveauMessage.this);
			pgd.setMessage(getResources().getString(R.string.accesDB));
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pgd.show();
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			
			if (con==null) {
				con = new DBConnection().getConnection();
				if(con==null) {
					resultat = (getResources().getString(R.string.DBConnection));
					Log.d("connexion","connexion BAD");
					return false;
     	    	}
				Log.d("connexion","connexion OK");
				
				MessageDB.setConnection(con);
			} 
			
			return true;		
		}

		protected void onPostExecute(Boolean result){
			super.onPostExecute(result);
			pgd.dismiss();
			retour = resultat;
			
		}
}
}

