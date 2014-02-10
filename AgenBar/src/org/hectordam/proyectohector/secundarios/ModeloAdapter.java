package org.hectordam.proyectohector.secundarios;

import java.util.ArrayList;
import org.hectordam.proyectohector.R;
import org.hectordam.proyectohector.base.Bar;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ModeloAdapter extends BaseAdapter {

	private ArrayList<Bar> lista;
	private LayoutInflater inflater;
	private Context contexto;
	
	static class ViewHolder {
		
		TextView nombre;
		TextView telefono;
	}
	
	public ModeloAdapter(Context contexto, ArrayList<Bar> lista) {
		
		this.contexto = contexto;
		this.lista = lista;
		inflater = LayoutInflater.from(contexto);
	}
	
	public void eliminar(int posicion) {
		
		BaseDatos datos = new BaseDatos(this.contexto);
		SQLiteDatabase db = datos.getWritableDatabase();
		
		db.execSQL("DELETE FROM bares WHERE nombre = '"+ lista.get(posicion).getNombre() +"'");
		
		lista.remove(posicion);
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int posicion, View convertView, ViewGroup padre) {
		
		ViewHolder holder = null;
		
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.lista_bares, padre, false);
			
			holder = new ViewHolder();
			holder.nombre = (TextView) convertView.findViewById(R.id.lbNombreBar);
			holder.telefono = (TextView) convertView.findViewById(R.id.lbTelefonoBar);
			
			convertView.setTag(holder);
		}
		
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Bar contacto = lista.get(posicion);
		
		holder.nombre.setText(contacto.getNombre());
		holder.telefono.setText(contacto.getTelefono());
		
		
		return convertView;
		
	}
	
	@Override
	public int getCount() {

		return lista.size();
	}

	@Override
	public Object getItem(int posicion) {
		
		return lista.get(posicion);
	}

	@Override
	public long getItemId(int posicion) {
		
		return posicion;
	}
}
