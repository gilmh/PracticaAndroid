package org.hectordam.proyectohector.secundarios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BaseDatos extends SQLiteOpenHelper {

	private static final String BASEDATOS_NOMBRE = "basedatosBares.db";
	
	private static final int BASEDATOS_VERSION = 3;
	
	public BaseDatos(Context contexto) {
		super(contexto, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE bares ( id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, direccion text, codigoPostal text, localidad text, telefono text );");
		db.execSQL("CREATE TABLE menus ( id INTEGER PRIMARY KEY AUTOINCREMENT, primerPlato TEXT, segundoPlato text, postre text, " +
				"bebida text, precio int, id_bar int unsigned not null, almuerzo int, comida int, cena int);");
		
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
		db.execSQL("DROP TABLE IF EXISTS menus");
		db.execSQL("DROP TABLE IF EXISTS bares");
		onCreate(db);
	}

	@Override
	public SQLiteDatabase getReadableDatabase() {
		
		
		
		return super.getReadableDatabase();
	}

	@Override
	public SQLiteDatabase getWritableDatabase() {
		
		
		
		return super.getWritableDatabase();
	}

}
