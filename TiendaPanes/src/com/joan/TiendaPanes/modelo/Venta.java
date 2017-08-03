package com.joan.TiendaPanes.modelo;

import java.sql.Date;
import java.util.ArrayList;

public class Venta {
	private int id;
	private Cliente cliente;
	private Date fecha;
	private boolean online;
	private ArrayList<Panes> panes;
	private Tienda tienda;
	private int idTienda;
	private float precio;
	
	public Venta(int id, Cliente cliente, Date fecha, boolean online, ArrayList<Panes> panes, Tienda tienda,
			float precio) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.fecha = fecha;
		this.online = online;
		this.panes = panes;
		this.tienda = tienda;
		this.idTienda = tienda.getId();
		this.precio = calcularPrecioTotal(panes);
	}
	
	public Venta(int id, Cliente cliente, Date fecha, boolean online, ArrayList<Panes> panes, Tienda tienda) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.fecha = fecha;
		this.online = online;
		this.panes = panes;
		this.tienda = tienda;
		this.idTienda = tienda.getId();
		this.precio = calcularPrecioTotal(panes);
	}
	
	public Venta(int id, Cliente cliente, Date fecha, boolean online, ArrayList<Panes> panes, int idTienda,
			float precio) {
		this.id = id;
		this.cliente = cliente;
		this.fecha = fecha;
		this.online = online;
		this.panes = panes;
		this.tienda = null;
		this.idTienda = idTienda;
		this.precio = calcularPrecioTotal(panes);
	}
	
	public Venta(){
		this.id = 0;
		this.cliente = null;
		this.fecha = new Date(10);
		this.online = true;
		this.panes = new ArrayList<>();
		this.tienda = null;
		this.precio = (float) 0.0;
	}

	private float calcularPrecioTotal(ArrayList<Panes> panes){
		float res = 0;
		for(int i = 0; i < panes.size(); i++){
			res+=panes.get(i).getPrecio();
		}
		return res;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public ArrayList<Panes> getPanes() {
		return panes;
	}

	public void setPanes(ArrayList<Panes> panes) {
		this.panes = panes;
		this.precio = calcularPrecioTotal(panes);
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio() {
		this.precio = calcularPrecioTotal(this.panes);
	}
	
	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getIdTienda() {
		return idTienda;
	}

	public void setIdTienda(int idTienda) {
		this.idTienda = idTienda;
	}
	
	
	
	
	
	
}
