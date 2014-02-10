package org.hectordam.proyectohector;

import org.hectordam.proyectohector.R;
import org.hectordam.proyectohector.secundarios.BaseDatos;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class InsertarMenus extends Activity implements OnClickListener {

	private MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insertar_menus);
		
		Button btAceptar = (Button) findViewById(R.id.btAceptarMenu);
    	btAceptar.setOnClickListener(this);
    	Button btCancelar = (Button) findViewById(R.id.btCancelarMenu);
    	btCancelar.setOnClickListener(this);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.insertar_menus, menu);
		return true;
	}



	@Override
	public void onClick(View v) {
		
		EditText txtPrimerPlato = (EditText) findViewById(R.id.txtPrimerPlato);
		EditText txtSegundoPlato = (EditText) findViewById(R.id.txtSegundoPlato);
		EditText txtPostre = (EditText) findViewById(R.id.txtPostre);
		EditText txtBebidas = (EditText) findViewById(R.id.txtBebidas);
		EditText txtPrecio = (EditText) findViewById(R.id.txtPrecio);
		
		CheckBox cbAlmuerzo = (CheckBox) findViewById(R.id.cbAlmuerzo);
		CheckBox cbComida = (CheckBox) findViewById(R.id.cbComida);
		CheckBox cbCena = (CheckBox) findViewById(R.id.cbCena);
		
		switch(v.getId()){
		
		case R.id.btAceptarMenu:
			
			BaseDatos datos = new BaseDatos(this);
			SQLiteDatabase db = datos.getWritableDatabase();
			
			if(db != null){
				db.execSQL("INSERT INTO menus (primerPlato, segundoPlato, postre, bebida, Precio, id_bar, almuerzo, comida, cena)" +
						"VALUES ('" + txtPrimerPlato.getText().toString() + "', '" + txtSegundoPlato.getText().toString() +"'," +
								"'" + txtPostre.getText().toString() + "','"+ txtBebidas.getText().toString() +"',"+ 
								Integer.parseInt(txtPrecio.getText().toString()) +", "+ BaresActivity.id_bar +"," +
								(cbAlmuerzo.isChecked() == true ? 1 : 0) +", "+ (cbComida.isChecked() == true ? 1 : 0) +", "+ (cbCena.isChecked() == true ? 1 : 0) +")");
			}
			
			db.close();
			
			mp = MediaPlayer.create(this, R.raw.vasobrindis);
    		mp.setLooping(false);
    		mp.start();
			
			break;
		case R.id.btCancelarMenu:
			
			onBackPressed();
			
			break;
		default:
			break;
		
		
		}
		
		
	}

}
