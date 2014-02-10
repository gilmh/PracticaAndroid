package org.hectordam.proyectohector.gui;

import org.hectordam.proyectohector.R;
import org.hectordam.proyectohector.secundarios.BaseDatos;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PestanaInsertar extends Fragment implements OnClickListener {
	
	private BaseDatos datos;
	
	private MediaPlayer mp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.pestana_insertar_bar, container, false);
		
		Button btAceptar = (Button) view.findViewById(R.id.btAceptarBar);
        btAceptar.setOnClickListener(this);
        Button btCancelar = (Button) view.findViewById(R.id.btCancelarBar);
        btCancelar.setOnClickListener(this);
		
        datos = new BaseDatos(getActivity());
        
		return view;
	}

	@Override
	public void onClick(View v) {
		
		EditText txtNombre = (EditText) getActivity().findViewById(R.id.txtNombre);
		EditText txtDireccion = (EditText) getActivity().findViewById(R.id.txtPostre);
		EditText txtLocalidad = (EditText) getActivity().findViewById(R.id.txtPrecio);
		EditText txtCodigoPostal = (EditText) getActivity().findViewById(R.id.txtBebidas);
		EditText txtTelefono = (EditText) getActivity().findViewById(R.id.txtTelefono);
		
		switch (v.getId()) {
		
		case R.id.btAceptarBar:
			
			SQLiteDatabase db = datos.getWritableDatabase();
			
			if(db != null){
				db.execSQL("INSERT INTO bares (nombre, direccion, codigoPostal, localidad, telefono) " +
						"VALUES ('" + txtNombre.getText().toString() + "', '" + txtDireccion.getText().toString() +"'," +
								"'" + txtCodigoPostal.getText().toString() + "','"+ txtLocalidad.getText().toString() +"'," +
								"'"+ Integer.parseInt(txtTelefono.getText().toString()) +"')");
			}
			
			db.close();
			
			mp = MediaPlayer.create(getActivity(), R.raw.vasosrotos);
    		mp.setLooping(false);
    		mp.start();
			
			break;
			
		case R.id.btCancelarBar:
			txtNombre.setText("");
			txtDireccion.setText("");
			txtLocalidad.setText("");
			txtTelefono.setText("");
			txtCodigoPostal.setText("");
			
			break;
			
		default:
			break;
	}
		
		
	}

}
