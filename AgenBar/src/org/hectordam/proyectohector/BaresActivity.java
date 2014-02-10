package org.hectordam.proyectohector;

import java.util.ArrayList;

import org.hectordam.proyectohector.base.Menus;
import org.hectordam.proyectohector.secundarios.BaseDatos;
import org.hectordam.proyectohector.secundarios.MenuAdapter;
import org.hectordam.proyectohector.secundarios.Preferencia;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class BaresActivity extends Activity {

	public static ArrayList<Menus> listaMenus = new ArrayList<Menus>();
	public MenuAdapter adaptadorMenu;
	
	public static int id_bar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bares);
		
		id_bar = getIntent().getIntExtra("id", 0);
		
		guardarLista();
		
		adaptadorMenu = new MenuAdapter(this, listaMenus, id_bar);
		
		ListView lvLista = (ListView) findViewById(R.id.listaMenus);
		lvLista.setAdapter(adaptadorMenu);
		
		lvLista.setEmptyView(findViewById(R.id.lbDatosMenus));
		
		this.registerForContextMenu(lvLista);
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.bares, menu);
		
		
		return true;
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()){
		
		case R.id.insertar_menu:
			
			Intent intent = new Intent(this, InsertarMenus.class);
			startActivity(intent);
			
			break;
			
		case R.id.menu_preferencias:
			
			intent = new Intent(this, Preferencia.class);
			startActivity(intent);
			
			break;
		default:
			break;		
		}		
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		this.getMenuInflater().inflate(R.menu.menu_contextual_menu, menu);
	}	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		
		
		switch (item.getItemId()){
		
		case R.id.ctx_eliminar:
			
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			final int posicion = info.position;
			
			adaptadorMenu.eliminar(posicion);
			
			adaptadorMenu.notifyDataSetChanged();
			break;
			default: 
				break;
		
		}
		
		return super.onContextItemSelected(item);
	}

	@Override
	public void onResume() {
		super.onResume();
		
		guardarLista();
		
		SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
		boolean verAlmuerzos = preferencias.getBoolean("opcion_ver_almuerzos", false);
		boolean verComidas = preferencias.getBoolean("opcion_ver_comidas", false);
		boolean verCenas = preferencias.getBoolean("opcion_ver_cenas", false);
		
		if(verAlmuerzos || verComidas || verCenas){
			adaptadorMenu.listaFiltrada(verAlmuerzos, verComidas, verCenas);
		}
		else{
			adaptadorMenu.listar();
		}
		
		adaptadorMenu.notifyDataSetChanged();
	}
	
	public void guardarLista(){
		BaseDatos datos = new BaseDatos(this);
		SQLiteDatabase db = datos.getWritableDatabase();
		
		String[] campos = {"id", "primerPlato","segundoPlato", "postre", "bebida", "precio", "id_bar", "almuerzo", "comida", "cena"};
		
		Cursor cursor = db.query("menus", campos, null, null, null, null, null);
		
		listaMenus.clear();
		while(cursor.moveToNext()){
			
			if(cursor.getInt(6) == id_bar){
					Menus menu = new Menus();
				
				menu.setId(cursor.getInt(0));
				menu.setPrimerPlato(cursor.getString(1));
				menu.setSegundoPlato(cursor.getString(2));
				menu.setPostre(cursor.getString(3));
				menu.setBebida(cursor.getString(4));
				menu.setPrecio(cursor.getInt(5));
				menu.setId_bar(cursor.getInt(6));
				menu.setAlmuerzo(cursor.getInt(7) == 1 ? true : false);
				menu.setComida(cursor.getInt(8) == 1 ? true : false);
				menu.setCena(cursor.getInt(9) == 1 ? true : false);
				
				listaMenus.add(menu);
				
			}
			
		}	
	}
	
}
