package com.joan.TiendaPanes.modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Tienda {
	private int id;
	private String nombre;
	private String localidad;
	private String contrasenya;
	private Stock stock;
	private ArrayList<Cliente> clientes;
	private ArrayList<Venta> ventas;
	private HashMap<Cliente, Venta> ventasCliente;
	
	
	public Tienda(int id, String nombre, String localidad, String contrasenya, Stock stock, ArrayList<Cliente> clientes, ArrayList<Venta> ventas, HashMap<Cliente, Venta> ventasCliente) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.localidad = localidad;
		this.contrasenya = contrasenya;
		this.stock = stock;
		this.clientes = clientes;
		this.ventas = ventas;
		this.ventasCliente = ventasCliente;
	}
		
	public Tienda(int id, String nombre, String localidad, String contrasenya, Stock stock, ArrayList<Cliente> clientes, ArrayList<Venta> ventas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.localidad = localidad;
		this.contrasenya = contrasenya;
		this.stock = stock;
		this.clientes = clientes;
		this.ventas = ventas;
	}
		
	public Tienda(int id, String nombre, String localidad, String contrasenya) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.localidad = localidad;
		this.contrasenya = contrasenya;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}

	public ArrayList<Venta> getVentas() {
		return ventas;
	}

	public void setVentas(ArrayList<Venta> ventas) {
		this.ventas = ventas;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Tienda [id=" + id + ", localidad=" + localidad + ", nombre=" + nombre + "]";
	}

	public HashMap<Cliente, Venta> getVentasCliente() {
		return ventasCliente;
	}

	public void setVentasCliente(HashMap<Cliente, Venta> ventasCliente) {
		this.ventasCliente = ventasCliente;
	}

}
