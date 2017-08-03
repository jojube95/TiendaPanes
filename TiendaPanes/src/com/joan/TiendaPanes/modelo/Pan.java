package com.joan.TiendaPanes.modelo;

public class Pan {
	private int id;
	private String tipo;
	private String nombre;
	private float precio;
	
	public Pan(int id, String tipo, String nombre, float precio) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nombre = nombre;
		this.precio = precio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	
	
}
