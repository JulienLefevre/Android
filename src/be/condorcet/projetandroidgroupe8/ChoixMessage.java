package be.condorcet.projetandroidgroupe8;

import java.sql.Connection;
import java.util.ArrayList;

import myconnections.DBConnection;
import Modele.CategorieDB;
import Modele.CommunauteDB;
import Modele.MessageDB;
import Modele.UtilisateurDB;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChoixMessage extends ActionBarActivity {
	
	private ListView liste;
	private Connection con = null;
	private ArrayList <String> nomMessage;
	private ArrayList <MessageDB> messages;
	private ArrayList <String> messagesRaccourcis;
	private ArrayAdapter <String> adapter;
	private ArrayList <Integer> idMessage;
	private int idCategorie;
	private ArrayList <Integer> nombreChamps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choix_message);
		Intent i = getIntent();
		
		idCategorie = Integer.parseInt(i.getStringExtra("idCategorie"));
		
		MyAccesDB adb = new MyAccesDB(ChoixMessage.this); 
		adb.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.choix_message, menu);
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
	
	public void gestionRetour(View view){
		Intent i = new Intent(ChoixMessage.this,ChoixCategorie.class);						
		startActivity(i);
		finish();
	}
	
	class MyAccesDB extends AsyncTask <String,Integer,Boolean> { 
		    
		private String resultat;
		private ProgressDialog pgd = null;

		public MyAccesDB (ChoixMessage pActivity) { }

		private void link (ChoixMessage pActivity) { }

		protected void onPreExecute(){
			super.onPreExecute();
			pgd = new ProgressDialog(ChoixMessage.this);
			pgd.setMessage(getResources().getString(R.string.accesDB));
			pgd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pgd.show();
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			
			if(con==null) {
				con = new DBConnection().getConnection();
				if(con==null) {
					resultat = (getResources().getString(R.string.DBConnection));
					Log.d("connexion","connexion BAD");
					return false;
     	    	}
				Log.d("connexion","connexion OK");
				
				CategorieDB.setConnection(con);
				CommunauteDB.setConnection(con);
				MessageDB.setConnection(con);
				UtilisateurDB.setConnection(con);
			}
			   
			try	{
				nombreChamps = new ArrayList <Integer> ();
				messages = MessageDB.readMessages(idCategorie);
				for ( MessageDB m : messages )
					nombreChamps.add(m.Compte$$());
				//Log.d("ok ","test 0"+messages);
			}
			catch(Exception e) {
				Log.d("DoInBackground","Message d'erreur : "+e.getMessage());
				return false;
			}
			return true;		
		}

		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pgd.dismiss();
			
			if (result) {
				liste = (ListView) findViewById(R.id.listeMessages);
				nomMessage = new  ArrayList<String>();
				messagesRaccourcis = new ArrayList <String> ();
				idMessage = new ArrayList<Integer>();
				
				for ( MessageDB m : messages )
				{	nomMessage.add(m.getTexte());
					messagesRaccourcis.add(m.getTexte().substring(0,23) + " ...");
					idMessage.add(m.getIdMessage());
				}
				
				adapter = new  ArrayAdapter <String> (ChoixMessage.this,android.R.layout.simple_list_item_1,messagesRaccourcis);
				liste.setAdapter(adapter);
				
				liste.setOnItemClickListener (
						new OnItemClickListener()
							{	@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
									Intent i = new Intent(ChoixMessage.this,ModifMsg.class);
									i.putExtra("MESSAGE",nomMessage.get(arg2).toString());
									i.putExtra("NOMBRE_CHAMPS",nombreChamps.get(arg2).toString());
									i.putExtra("IDCAT",""+idCategorie);
									startActivity(i);
									finish();
								}
							}
						);			
			}
		}		
	}
}