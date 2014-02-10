package org.hectordam.proyectohector.gui;

import java.util.ArrayList;

import org.hectordam.proyectohector.BaresActivity;
import org.hectordam.proyectohector.R;
import org.hectordam.proyectohector.base.Bar;
import org.hectordam.proyectohector.secundarios.BaseDatos;
import org.hectordam.proyectohector.secundarios.ModeloAdapter;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PestanaBar extends Fragment implements OnCreateContextMenuListener, OnItemClickListener{

	public static ArrayList<Bar> lista = new ArrayList<Bar>();
	public ModeloAdapter adaptador;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.pestana_bares, container, false);
		
		BaseDatos datos = new BaseDatos(getActivity());
		SQLiteDatabase db = datos.getWritableDatabase();
		
		String[] FROM_CURSOR = {"id", "nombre","direccion", "codigoPostal", "localidad", "telefono"};
		Cursor cursor = db.query("bares", FROM_CURSOR, null, null, null, null, null, null);
		
		lista.clear();
		while(cursor.moveToNext()){
			
			Bar bar = new Bar();
			
			bar.setId(cursor.getInt(0));
			bar.setNombre(cursor.getString(1));
			bar.setDireccion(cursor.getString(2));
			bar.setCodigoPostal(cursor.getString(3));
			bar.setLocalidad(cursor.getString(4));
			bar.setTelefono(cursor.getString(5));
			
			lista.add(bar);
		}		
		
		adaptador = new ModeloAdapter(getActivity(), lista);
		ListView lvLista = (ListView) view.findViewById(R.id.listaMenus);
		lvLista.setAdapter(adaptador);
		
		lvLista.setEmptyView(view.findViewById(R.id.lbContactos));
		
		lvLista.setOnItemClickListener(this);
		this.registerForContextMenu(lvLista);
		return view;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		getActivity().getMenuInflater().inflate(R.menu.menu_contextual_bares, menu);
	}	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		switch (item.getItemId()){
		
		case R.id.ctx_eliminar:
			
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			final int posicion = info.position;
			
			adaptador.eliminar(posicion);
			
			adaptador.notifyDataSetChanged();
			break;
			default: 
				break;
		
		}
		
		return super.onContextItemSelected(item);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int posicion, long id) {
		
		int num = lista.get(posicion).getId();
		
		/*
		String[] campos = new String[] {"id"};
		String[] where = new String[] {nombre};
		
		BaseDatos datos = new BaseDatos(getActivity());
		SQLiteDatabase db = datos.getWritableDatabase();
		
		Cursor cursor = db.query("bares", campos, "nombre = ?", where, null, null, null);
		
		while(cursor.moveToFirst()){
			num = cursor.getInt(0);
		}
		*/
		Intent intent = new Intent(getActivity(), BaresActivity.class);
		intent.putExtra("id", num);
		startActivity(intent);
	}
	

	@Override
	public void onResume() {
		super.onResume();
		
		adaptador.notifyDataSetChanged();
	}
	
}
