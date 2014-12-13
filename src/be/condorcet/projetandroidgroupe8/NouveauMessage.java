package be.condorcet.projetandroidgroupe8;

import java.sql.Connection;

import myconnections.DBConnection;
import Modele.Categorie;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NouveauMessage extends ActionBarActivity {
	private EditText editText;
	private int idCategorie;
	private Connection con = null;
	private String msg = "";
	
	//private MessageDB messag = new MessageDB();
	private  Button mButtonEnr = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nouveau_message);
		editText = (EditText) findViewById(R.id.editText1);
		
		Intent i = getIntent();
		idCategorie = Integer.parseInt(i.getStringExtra("idCategorie"));
		
		MyAccesDB adb = new MyAccesDB(NouveauMessage.this);
		adb.execute();
		
		
		
		
		Toast.makeText(this, Integer.toString(idCategorie), Toast.LENGTH_SHORT).show();
		
		mButtonEnr = (Button) findViewById(R.id.boutonEnr);
		
		//if(retour.substring(0, 9).equals("ORA-00001")){
		//	Toast.makeText(this, getResources().getString(R.string.errDoublMsg) , Toast.LENGTH_SHORT).show();
		   
		//}
		
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
		msg = editText.getText().toString();
		editText.setText(msg + " [~] ");
		msg = ""+editText.getText().toString();
	}
	
	public void EnrMessage(View view) {
		msg = ""+editText.getText().toString();
		
	
		try	{
			MessageDB messag = new MessageDB(msg,new Categorie(idCategorie));
		    messag.create();
		    
		}
		catch(Exception e) {
			if(e.getMessage().equals("null"))
			{
				Toast.makeText(this, getResources().getString(R.string.estEnr) + " " + msg , Toast.LENGTH_SHORT).show();
				Log.e("erreur !!!!!!!",e.getMessage());
			}
			else{
				Log.e("erreur2 !!!!!!!",e.getMessage());
			if(e.getMessage().substring(0,5).equals("OALL8")){
				Toast.makeText(this, getResources().getString(R.string.errDoublMsg) , Toast.LENGTH_SHORT).show();
			  
			} 
			
			}
		}
		/*try	{
			Log.e("erreur commit !!!!!!!","non1");
		    con.commit();
		    Log.e("erreur commit !!!!!!!","non2");
	     }
		catch(Exception e) {
			Log.e("erreur commit !!!!!!!",e.getMessage());
		}*/
		Intent i = new Intent(NouveauMessage.this,Accueil.class);						
		startActivity(i);
		finish();
		
	}
	public void gestionRetourAcc(View view){
		Intent i = new Intent(NouveauMessage.this,Accueil.class);						
		startActivity(i);
		finish();
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		 try {
			 con.close();
			 con = null;
			 Log.d("connexion","deconnexion OK");
		 }
		 catch (Exception e) {
		 
		 }
		 Log.d("connexion","deconnexion OK");
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
				
				//messag.setConnection(con);
				MessageDB.setConnection(con);				
			} 
			
			return true;		
		}

		protected void onPostExecute(Boolean result){
			super.onPostExecute(result);
			pgd.dismiss();
			/*retour = resultat;
			mButtonEnr.setOnClickListener(new  View.OnClickListener() {
				@Override
					public  void onClick(View v) {
					
					msg = ""+editText.getText().toString();
					Log.d("okkkkkkkkk",msg);
					//Toast.makeText(this, getResources().getString(R.string.estEnr) + " " + msg , Toast.LENGTH_SHORT).show();
				
					try	{
					    messag = new MessageDB(msg,idCategorie);
					    //messag.setTexte(msg);
					    //messag.setCategorie(new Categorie(idCategorie));
					    messag.create();	
					}
					catch(Exception e) {
						Log.e("erreur !!!!!!!",e.getMessage());
					}
							}
				});*/
			
		}
}
}

