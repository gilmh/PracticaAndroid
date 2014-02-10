package org.hectordam.proyectohector;

import org.hectordam.proyectohector.base.Bar;

import uk.me.jstott.jcoord.UTMRef;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Activity donde se muestra el mapa
 * @author Santiago Faci
 *
 */
public class Mapa2 extends FragmentActivity {

	private GoogleMap mapa;
	
	
	//private ArrayList<Bar> bares = new ArrayList<Bar>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        
        //bares = getIntent().getParcelableArrayListExtra("bares");
    	
        try {
            MapsInitializer.initialize(this);
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        
        mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        
        mapa.setMyLocationEnabled(true);
        
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	if (MainActivity.listaBares != null) {
    		marcarBares();
    	} 
    		  
    }
    
	private void marcarBares() {
		
		if (MainActivity.listaBares.size() > 0) {
			for (Bar bar : MainActivity.listaBares) {
				
				UTMRef utm= new UTMRef(bar.getLatitud(), bar.getLongitud(), 'N', 30);
				uk.me.jstott.jcoord.LatLng ubicacion = utm.toLatLng();
				
				double latitud = ubicacion.getLat();
				double longitud = ubicacion.getLng();
				
				mapa.addMarker(new MarkerOptions().position(new LatLng(latitud, longitud)).title(bar.getNombre()));
			}
		}
		
		// Posiciona la vista del usuario en Zaragoza
    	CameraUpdate camara = CameraUpdateFactory.newLatLng(new LatLng(41.6561, -0.8773));
        	 
    	// Coloca la vista del mapa sobre la posición de la ciudad y activa el zoom para verlo de cerca
    	mapa.moveCamera(camara);
    	mapa.animateCamera(CameraUpdateFactory.zoomTo(11.0f), 2000, null);
    	
	}
	
}
