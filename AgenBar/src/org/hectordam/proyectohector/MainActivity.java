package org.hectordam.proyectohector;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hectordam.proyectohector.base.Bar;
import org.hectordam.proyectohector.gui.PestanaInsertar;
import org.hectordam.proyectohector.gui.PestanaBar;
import org.hectordam.proyectohector.secundarios.TabsListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;

public class MainActivity extends Activity {
	
	public static ArrayList<Bar> listaBares = new ArrayList<Bar>();
	private static final String URL = "http://www.zaragoza.es/georref/json/hilo/ver_Restaurante";
	
	private MediaPlayer mp = new MediaPlayer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		cargarBares();
		setContentView(R.layout.activity_main);
		
		formar();
		
		
	}
	
	private void formar(){
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Resources res = getResources();
		
		ActionBar.Tab tab1 = actionBar.newTab().setText(res.getString(R.string.tab_bares));
	    ActionBar.Tab tab2 = actionBar.newTab().setText(res.getString(R.string.tab_insertar));
		 
	    Fragment fragmentoTab1 = new PestanaBar();
        Fragment fragmentoTab2 = new PestanaInsertar();
		 
        tab1.setTabListener(new TabsListener(fragmentoTab1));
        tab2.setTabListener(new TabsListener(fragmentoTab2));
        
        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        
       
	}
	
	private void cargarBares(){
		
		TareaDescargaDatos tarea = new TareaDescargaDatos();
    	tarea.execute(URL);
	}
	
	private class TareaDescargaDatos extends AsyncTask<String, Void, Void> {

		private boolean error = false;
		
		// Este método no puede acceder a la interfaz
		@Override
		protected Void doInBackground(String... urls) {
			
			InputStream is = null;
	    	String resultado = null;
	    	JSONObject json = null;
	    	JSONArray jsonArray = null;
	    	
	    	try {
	    		// Conecta con la URL y obtenemos el fichero con los datos
		    	HttpClient clienteHttp = new DefaultHttpClient();
		    	HttpPost httpPost = new HttpPost(urls[0]);
		    	HttpResponse respuesta = clienteHttp.execute(httpPost);
		    	HttpEntity entity = respuesta.getEntity();
		    	is = entity.getContent();
		    	
		    	// Lee el fichero de datos y genera una cadena de texto como resultado
		    	BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    	StringBuilder sb = new StringBuilder();
		    	String linea = null;
		    	
		    	while ((linea = br.readLine()) != null) {
		    		sb.append(linea + "\n");
		    	}
		    	
		    	is.close();
		    	resultado = sb.toString();
		    	
		    	json = new JSONObject(resultado);
		    	jsonArray = json.getJSONArray("features");
		    	
		    	String nombre = null;
		    	String coordenadas = null;
		    	Bar bar = null;
		    	for (int i = 0; i < jsonArray.length(); i++) {
		    		nombre = jsonArray.getJSONObject(i).getJSONObject("properties").getString("title");		    		
		    		coordenadas = jsonArray.getJSONObject(i).getJSONObject("geometry").getString("coordinates");
		    		coordenadas = coordenadas.substring(1, coordenadas.length() - 1);
		    		String latlong[] = coordenadas.split(",");
		    		
		    		bar = new Bar();
		    		bar.setNombre(nombre);
		    		bar.setLatitud(Float.parseFloat(latlong[0]));
		    		bar.setLongitud(Float.parseFloat(latlong[1]));
		    		
		    		listaBares.add(bar);
		    	}
		    	
	    	} catch (ClientProtocolException cpe) {
	    		cpe.printStackTrace();
	    		error = true;
	    	} catch (IOException ioe) {
	    		ioe.printStackTrace();
	    		error = true;
	    	} catch (JSONException jse) {
	    		jse.printStackTrace();
	    		error = true;
	    	}
	    	
	    	return null;
		}
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		// Sólo hay una opción, la de lanzar la Activity de dar de alta un contacto
		case R.id.ctx_mapa:
			
			Intent intent = new Intent(this, Mapa2.class);
			//intent.putParcelableArrayListExtra("bares", listaBares);
			startActivity(intent);
			/*
			mp.stop();
			mp.release();
			mp = null;
			*/
			return true;
		case R.id.ctx_acerca_de:
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.acerca_de)
					.setIcon(R.drawable.ic_launcher)
					.setTitle(R.string.app_name)
					.setPositiveButton(R.string.boton_aceptar, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();						
						}
					});
			AlertDialog dialogo = builder.create();
			dialogo.show();
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void onResume() {
		
		mp = MediaPlayer.create(this, R.raw.fondo);
		mp.setLooping(true);
		mp.start();
		
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		
		mp.stop();
		mp.release();
		//mp = null;
		
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		
		mp.stop();
		mp.release();
		mp = null;
		
		super.onDestroy();
	}
}
