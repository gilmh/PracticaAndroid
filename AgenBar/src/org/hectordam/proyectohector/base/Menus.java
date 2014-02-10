package org.hectordam.proyectohector.base;

public class Menus {

	private String primerPlato;
	private String segundoPlato;
	private String postre;
	private String bebida;
	private int precio;
	private int id;
	private int id_bar;
	
	private boolean almuerzo;
	private boolean comida;
	private boolean cena;
	
	public String getPrimerPlato() {
		return primerPlato;
	}
	public void setPrimerPlato(String primerPlato) {
		this.primerPlato = primerPlato;
	}
	public String getSegundoPlato() {
		return segundoPlato;
	}
	public void setSegundoPlato(String segundoPlato) {
		this.segundoPlato = segundoPlato;
	}
	public String getPostre() {
		return postre;
	}
	public void setPostre(String postre) {
		this.postre = postre;
	}
	public String getBebida() {
		return bebida;
	}
	public void setBebida(String bebida) {
		this.bebida = bebida;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public boolean isAlmuerzo() {
		return almuerzo;
	}
	public void setAlmuerzo(boolean almuerzo) {
		this.almuerzo = almuerzo;
	}
	public boolean isComida() {
		return comida;
	}
	public void setComida(boolean comida) {
		this.comida = comida;
	}
	public boolean isCena() {
		return cena;
	}
	public void setCena(boolean cena) {
		this.cena = cena;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_bar() {
		return id_bar;
	}
	public void setId_bar(int id_bar) {
		this.id_bar = id_bar;
	}
	
}
