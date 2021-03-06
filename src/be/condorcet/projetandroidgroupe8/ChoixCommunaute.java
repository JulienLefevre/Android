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

public class ChoixCommunaute extends ActionBarActivity {
	
	private ArrayList <CommunauteDB> communautes;
	private ArrayList <String> stringCommunautes;
	private ArrayList <Integer> idCommunautes;
	private ArrayAdapter <String> adapter;
	private CommunauteDB communaute;
	
	private int idUtilisateur;
	private ListView liste;
	private Connection con;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choix_communaute);
		
		Intent i = getIntent();
		idUtilisateur = Integer.parseInt(i.getStringExtra("idUtilisateur"));
		
		MyAccesDB adb = new MyAccesDB(ChoixCommunaute.this);
		adb.execute();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.choix_communaute, menu);
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
	
	public void gestionRetour(View view){
		Intent i = new Intent(ChoixCommunaute.this,Accueil.class);						
		startActivity(i);
		finish();
	}
	
	class MyAccesDB extends AsyncTask <String,Integer,Boolean> {
	    
		private String resultat;
		private ProgressDialog pgd = null;

		public MyAccesDB (ChoixCommunaute pActivity) { }

		private void link (ChoixCommunaute pActivity) { }

		protected void onPreExecute(){
			super.onPreExecute();
			pgd = new ProgressDialog(ChoixCommunaute.this);
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
				
				CategorieDB.setConnection(con);
				CommunauteDB.setConnection(con);
				MessageDB.setConnection(con);
				UtilisateurDB.setConnection(con);
			} 
			try	{
				UtilisateurDB utilisateur = new UtilisateurDB();
				utilisateur.setIdUtilisateur(idUtilisateur);
				utilisateur.read();
				communautes = utilisateur.mesCommunautesAdministrees();
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
				liste = (ListView) findViewById(R.id.listeCommunautes);
				stringCommunautes = new ArrayList<String>();
				idCommunautes = new ArrayList<Integer>();
				
				for ( CommunauteDB c : communautes )
				{	stringCommunautes.add(c.getNomCommunaute());
					idCommunautes.add(c.getIdCommunaute());
				}
				
				adapter = new  ArrayAdapter <String> (ChoixCommunaute.this,android.R.layout.simple_list_item_1,stringCommunautes);
				liste.setAdapter(adapter);
				
				liste.setOnItemClickListener (
						new OnItemClickListener()
							{	@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
									Intent intent = new Intent(ChoixCommunaute.this,ChoixCategorie.class);
									intent.putExtra("idCommunaute", idCommunautes.get(arg2).toString());									
									startActivity(intent);
									finish();
								}
							}
						);
			}
		}		
	}
}
