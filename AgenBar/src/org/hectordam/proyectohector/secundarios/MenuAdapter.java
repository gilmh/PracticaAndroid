package org.hectordam.proyectohector.secundarios;

import java.util.ArrayList;
import org.hectordam.proyectohector.R;
import org.hectordam.proyectohector.base.Menus;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {

	private ArrayList<Menus> menus;
	private ArrayList<Menus> mostrarMenus;
	private LayoutInflater inflater;
	private Context contexto;

	static class ViewHolderMenu {
		
		TextView primerPlato;
		TextView segundoPlato;
		TextView postre;
		TextView bebida;
		TextView precio;
	}
	
	public MenuAdapter(Context contexto, ArrayList<Menus> lista, int id) {
		
		this.contexto = contexto;
		this.menus = lista;
		this.mostrarMenus = new ArrayList<Menus>();
		inflater = LayoutInflater.from(contexto);
	}
	
	public void eliminar(int posicion) {
		
		int id = menus.get(posicion).getId();
		
		BaseDatos datos = new BaseDatos(this.contexto);
		SQLiteDatabase db = datos.getWritableDatabase();
		
		db.execSQL("DELETE FROM menus WHERE id = "+ id);
		
		menus.remove(posicion);
		notifyDataSetChanged();
	}
	
	public void listaFiltrada(boolean almuerzo, boolean comida, boolean cena){
		mostrarMenus.clear();
		
		if(almuerzo){
			for (Menus menu : menus) {
				if(menu.isAlmuerzo()){
					mostrarMenus.add(menu);
					System.out.println(menu.isAlmuerzo());
				}
			}
		}
		if(comida){
			for (Menus menu : menus) {
				if(menu.isComida()){
					mostrarMenus.add(menu);
					System.out.println(menu.isAlmuerzo());
				}
			}
		}
		if(cena){
			for (Menus menu : menus) {
				if(menu.isCena()){
					mostrarMenus.add(menu);
					System.out.println(menu.isAlmuerzo());
				}
			}
		}
		
	}
	
	public void listar(){
		
		mostrarMenus.clear();
		mostrarMenus.addAll(menus);
	}
	
	@Override
	public View getView(int posicion, View convertView, ViewGroup padre) {
		
		ViewHolderMenu holderMenu = null;
		
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.fila_menu, padre, false);
			
			holderMenu = new ViewHolderMenu();
			holderMenu.primerPlato = (TextView) convertView.findViewById(R.id.lbPrimerPlatoFila);
			holderMenu.segundoPlato = (TextView) convertView.findViewById(R.id.lbSegundoPlatoFila);
			holderMenu.postre = (TextView) convertView.findViewById(R.id.lbPostreFila);
			holderMenu.bebida = (TextView) convertView.findViewById(R.id.lbBebidaFila);
			holderMenu.precio = (TextView) convertView.findViewById(R.id.lbPreciosFila);
			
			convertView.setTag(holderMenu);
		}
		else {
			holderMenu = (ViewHolderMenu) convertView.getTag();
		}
		
		Menus menu = mostrarMenus.get(posicion);
		
		SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(contexto);
		String opcionDatos = preferencias.getString("opcion_datos", "Primer plato");
		if (opcionDatos.equals(contexto.getResources().getStringArray(R.array.datos)[0])){
			
			holderMenu.primerPlato.setText(menu.getPrimerPlato());
			holderMenu.segundoPlato.setText(menu.getSegundoPlato());
		}
		else{
			holderMenu.primerPlato.setText(menu.getSegundoPlato());
			holderMenu.segundoPlato.setText(menu.getPrimerPlato());
		}
		
		holderMenu.postre.setText(menu.getPostre());
		holderMenu.bebida.setText(menu.getBebida());
		holderMenu.precio.setText(Integer.toString(menu.getPrecio()));
		
		return convertView;
		
	}
	
	@Override
	public int getCount() {

		return mostrarMenus.size();
	}

	@Override
	public Object getItem(int posicion) {
		
		return mostrarMenus.get(posicion);
	}

	@Override
	public long getItemId(int posicion) {
		
		return posicion;
	}
}
