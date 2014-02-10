package org.hectordam.proyectohector;

import java.util.ArrayList;

import org.hectordam.proyectohector.R;
import org.hectordam.proyectohector.base.Bar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class Mapa extends FragmentActivity implements LocationListener,ConnectionCallbacks, OnConnectionFailedListener{

	private GoogleMap mapa;
	private LocationClient locationClient;
	
	private CameraUpdate camara;
	
	private static final LocationRequest LOC_REQUEST = LocationRequest.create()
            .setInterval(5000)         
            .setFastestInterval(16)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		
        try {
            MapsInitializer.initialize(this);
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        
        mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        
		
        ArrayList<Bar> bares = getIntent().getParcelableArrayListExtra("bares");
        
        
    	if (bares != null) {
    		//marcarBares(bares);
    	}        
        
    	mapa.setMyLocationEnabled(true);
        configuraLocalizador();
        
    	camara = CameraUpdateFactory.newLatLng(new LatLng(41.6561, -0.8773));
        	 
    	mapa.moveCamera(camara);
    	mapa.animateCamera(CameraUpdateFactory.zoomTo(11.0f), 2000, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mapa, menu);
		return true;
	}

	
	
	/**
	 * Añade las marcas de todas las gasolineras
	 * @param gasolineras
	 */
	private void marcarBares(ArrayList<Bar> bares) {
		
		if (bares.size() > 0) {
			for (Bar bar : bares) {
				
				mapa.addMarker(new MarkerOptions()
					.position(new LatLng(bar.getLatitud(), bar.getLongitud()))
					.title(bar.getNombre()));
			}
		}
		
	}
	
	/**
	 * Se muestra la Activity
	 */
	@Override
	protected void onStart() {
		
		super.onStart();
		locationClient.connect();
	}

	@Override
    protected void onStop() {
   
        super.onStop();
        locationClient.disconnect();
    }

	private void configuraLocalizador() {
        if (locationClient == null) {
            locationClient = new LocationClient(this, this, this);
        }
    }

	@Override
	public void onConnected(Bundle arg0) {
		
		locationClient.requestLocationUpdates(LOC_REQUEST, this);
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
	}

	@Override
	public void onDisconnected() {
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
		
	}
}
