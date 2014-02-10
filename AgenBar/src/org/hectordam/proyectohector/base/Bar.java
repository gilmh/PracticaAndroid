package org.hectordam.proyectohector.base;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Bar implements Parcelable {

	private String nombre;
	private String direccion;
	private String codigoPostal;
	private String localidad;
	private String telefono;
	private int id;
	
	private LatLng posicion;
	private float latitud;
	private float longitud;
	
	public Bar(){
		
	}
	
	public Bar(Parcel entrada) {
        
        nombre = entrada.readString();
        direccion = entrada.readString();
        codigoPostal = entrada.readString();
        localidad = entrada.readString();
        telefono = entrada.readString();
        id = entrada.readInt();
        
        latitud = entrada.readFloat();
        longitud = entrada.readFloat();
    }
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	public LatLng getPosicion() {
		return posicion;
	}
	public void setPosicion(LatLng posicion) {
		this.posicion = posicion;
	}
	@Override
	public int describeContents() {
		
		return 0;
	}
	@Override
	public void writeToParcel(Parcel destino, int flag) {
		
		destino.writeString(nombre);
		destino.writeString(direccion);
		destino.writeString(codigoPostal);
		destino.writeString(localidad);
		destino.writeString(telefono);
		destino.writeInt(id);
		
		destino.writeDouble(latitud);
		destino.writeDouble(longitud);
	}
	
	public static final Parcelable.Creator<Bar> CREATOR = new Parcelable.Creator<Bar>() {
		
		@Override
		public Bar createFromParcel(Parcel in) {
		    return new Bar(in);
		}
		
		@Override
		public Bar[] newArray(int size) {
		    return new Bar[size];
		}
	};
}
