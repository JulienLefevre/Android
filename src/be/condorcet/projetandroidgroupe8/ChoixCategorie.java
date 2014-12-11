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
import android.widget.Toast;

public class ChoixCategorie extends ActionBarActivity {

	private ListView liste;
	private Connection con = null;
	private ArrayList <String> nomCategories;
	private ArrayList <CategorieDB> categories;
	private ArrayAdapter <String> adapter;
	private ArrayList <Integer> idCategorie;
	private CommunauteDB communaute;
	
	private Class nouvelleActivite;
	private int idCommunaute;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choix_categorie);
		
		try
		{	Intent intent = getIntent();
			idCommunaute = Integer.parseInt(intent.getStringExtra("idCommunaute"));
			
			MyAccesDB adb = new MyAccesDB(ChoixCategorie.this);
			adb.execute();
		}
		catch(Exception e)	{	
			Log.d("Exception",e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.choix_categorie, menu);
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
			 con=null;
			 Log.d("connexion","deconnexion OK");
		 }
		 catch (Exception e) {
		 
		 }
		 Log.d("connexion","deconnexion OK");
	}
	
	public void gestionRetour(View view){
		Intent i = new Intent(ChoixCategorie.this,Accueil.class);						
		startActivity(i);
		finish();
	}
	
	/*public void clickListe(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		Intent i = new Intent(ChoixCategorie.this,ChoixMessage.class);
		i.putExtra("IDCAT",idCategorie.get(arg2).toString());									
		startActivity(i);
		finish();
	}*/
	
	class MyAccesDB extends AsyncTask <String,Integer,Boolean> {
		    
		private String resultat;
		private ProgressDialog pgd = null;

		public MyAccesDB (ChoixCategorie pActivity) { }

		private void link (ChoixCategorie pActivity) { }

		protected void onPreExecute(){
			super.onPreExecute();
			pgd = new ProgressDialog(ChoixCategorie.this);
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
				if ( idCommunaute == -1 ) {
					categories = CategorieDB.readCategories();
					nouvelleActivite = ChoixMessage.class;
				}
				else {
					categories = CategorieDB.readCategories(idCommunaute);
					nouvelleActivite = NouveauMessage.class;
				}
			}
			catch(Exception e) {
				resultat = "erreur" +e.getMessage();
				return false;
			}
			return true;		
		}

		protected void onPostExecute(Boolean result){
			super.onPostExecute(result);
			pgd.dismiss();
			
			if (result) {
				liste = (ListView) findViewById(R.id.listeCategories);
				nomCategories = new  ArrayList<String>();
				idCategorie = new ArrayList<Integer>();
				
				for ( CategorieDB c : categories )
				{	nomCategories.add(c.getNomCategorie());
					idCategorie.add(c.getIdCategorie());
				}
				
				adapter = new  ArrayAdapter <String> (ChoixCategorie.this,android.R.layout.simple_list_item_1,nomCategories);
				liste.setAdapter(adapter);
				
				liste.setOnItemClickListener (
						new OnItemClickListener()
							{	@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
									Intent i = new Intent(ChoixCategorie.this,nouvelleActivite);
									i.putExtra("idCategorie",idCategorie.get(arg2).toString());	
									startActivity(i);
									finish();
								}
							}
						);
			}
		}		
	}
}