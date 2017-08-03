package com.joan.TiendaPanes.modelo;

import java.sql.Date;

public class Cliente {
	
	private String nombre;
	private int id;
	private String localidad;
	private Date fechaNac;
	private boolean online;
	private String usuario;
	private String pass;
	
	public Cliente(String nombre, int id, String localidad, Date fechaNac, boolean online, String usuario,
			String pass) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.localidad = localidad;
		this.fechaNac = fechaNac;
		this.online = online;
		this.usuario = usuario;
		this.pass = pass;
	}
	
	public Cliente(String nombre, int id, String localidad, Date fechaNac, boolean online) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.localidad = localidad;
		this.fechaNac = fechaNac;
		this.online = online;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
	
	
	
	
}
