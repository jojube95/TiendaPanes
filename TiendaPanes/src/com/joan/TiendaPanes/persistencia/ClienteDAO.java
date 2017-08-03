package com.joan.TiendaPanes.persistencia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.joan.TiendaPanes.modelo.Cliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClienteDAO {
	private ConnectionManager connectionManager;
	public ClienteDAO(ConnectionManager connectionManager){
		this.connectionManager = connectionManager;
	}
	
	public void crearCliente(Cliente c){
		try {
			connectionManager.connect();
			String sql = "INSERT INTO panaderia.clientes (idCliente, nombre, localidad, fechaNacimiento, usuario, contrasenya, online) VALUES (?,?,?,?,?,?,?)";
			ArrayList<Object> lista = new ArrayList<>();
			lista.add(0);
			lista.add(c.getNombre());
			lista.add(c.getLocalidad());
			lista.add(c.getFechaNac());
			lista.add(c.getUsuario());
			lista.add(c.getPass());
			lista.add(true);
			int i = connectionManager.updateDBPS(sql, lista, true);
			c.setId(i);
						
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connectionManager.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void eliminarCliente(Cliente c){
		try {
			connectionManager.connect();
			String sql = "SELECT * FROM clientes WHERE IdCliente = "+c.getId();
			ResultSet rSet = connectionManager.consultar(sql);
			if(rSet.next()){
				//Eliminar sus ventas
				sql = "delete from panesventas where idVenta in (select idVenta from ventas where idCliente = "+c.getId()+")";
				connectionManager.updateDB(sql, false);
				sql = "delete from ventas where idCliente = "+c.getId();
				connectionManager.updateDB(sql, false);
				//Eliminar cliente
				sql = "DELETE FROM panaderia.clientes WHERE idCliente = " + c.getId();
				connectionManager.updateDB(sql, false);
			}
			else{
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error");
				alerta.setContentText("El cliente a eliminar no existe.");
				alerta.show();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connectionManager.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Cliente> obtenerClientes(){
		ArrayList<Cliente> clientes = new ArrayList<>();
		ResultSet rSet = null;
		try {
			connectionManager.connect();
			String sql = "SELECT * FROM panaderia.clientes";
			rSet = connectionManager.consultar(sql);
			
			while(rSet.next()){
				Cliente c = new Cliente(rSet.getString(2), rSet.getInt(1), rSet.getString(3), rSet.getDate(4), rSet.getBoolean(7),rSet.getString(5),rSet.getString(6));
				clientes.add(c);
			}
			connectionManager.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connectionManager.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return clientes;
		
	}
	
	public Cliente modificarCliente(Cliente c){
		Cliente c2 = new Cliente("", 0, "", new Date(10), false,"","");
		
		try {
			connectionManager.connect();
			String sql = "SELECT * FROM clientes WHERE idCliente = "+c.getId();
			ResultSet rSet = connectionManager.consultar(sql);
			if(rSet.next()){
				sql = "UPDATE panaderia.clientes SET idCliente = " + c.getId() + ", nombre = '" + c.getNombre() + 
						"', localidad = '" + c.getLocalidad() + "', fechaNacimiento = '" + c.getFechaNac().toString() + 
						"', usuario = '" + c.getUsuario() + "', contrasenya = '" + c.getPass() + "', online = " + c.isOnline() + " WHERE idCliente = " + c.getId() + "";
				connectionManager.updateDB(sql, false);
				
				//devolver el cliente modificado
				sql = "SELECT * FROM clientes WHERE idCliente = " + c.getId();
				rSet = connectionManager.consultar(sql);
				rSet.next();
				c2.setId(rSet.getInt(1));
				c2.setNombre(rSet.getString(2));
				c2.setLocalidad(rSet.getString(3));
				c2.setFechaNac(rSet.getDate(4));
				c2.setUsuario(rSet.getString(5));
				c2.setPass(rSet.getString(6));
				c2.setOnline(rSet.getBoolean(7));
				
			}
			else{
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error");
				alerta.setContentText("El cliente a modificar no existe.");
				alerta.show();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connectionManager.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return c2;
	}
	
	public Cliente buscarCliente(int idCliente){
		Cliente cliente = new Cliente("", 0, "", new Date(10), true);
		
		try{
			String sql = "SELECT * FROM clientes WHERE idCliente = " + idCliente;
			ResultSet rSet = connectionManager.consultar(sql);
			if(rSet.next()){
				cliente.setId(rSet.getInt(1));
				cliente.setNombre(rSet.getString(2));
				cliente.setLocalidad(rSet.getString(3));
				cliente.setFechaNac(rSet.getDate(4));
				cliente.setUsuario(rSet.getString(5));
				cliente.setPass(rSet.getString(6));
				cliente.setOnline(rSet.getBoolean(7));
			}
			else{
				cliente = null;
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error");
				alerta.setContentText("El cliente no existe.");
				alerta.show();
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return cliente;
	}
	
	public int getLastGenerated(ConnectionManager connectionManager){
		
		int res = 0;
		try {
			ResultSet resultSet = connectionManager.consultar("select auto_increment from INFORMATION_SCHEMA.TABLES where table_name = 'clientes'");
			resultSet.next();
			res = resultSet.getInt(1);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
}
