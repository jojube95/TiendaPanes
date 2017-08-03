package com.joan.TiendaPanes.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.joan.TiendaPanes.modelo.Cliente;
import com.joan.TiendaPanes.modelo.Pan;
import com.joan.TiendaPanes.modelo.Panes;
import com.joan.TiendaPanes.modelo.Tienda;
import com.joan.TiendaPanes.modelo.Venta;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class VentaDAO {
	private ConnectionManager connectionManager;
	public VentaDAO(ConnectionManager connectionManager){
		this.connectionManager = connectionManager;
	}
	
	public void crearVenta(Venta v, ArrayList<Panes> panes, Tienda t){
		try {
			connectionManager.connect();
			String sql = "INSERT INTO  ventas(idVenta, idCliente, idTienda, fecha, online, precio) VALUES (?,?,?,?,?,?)";
			ArrayList<Object> lista = new ArrayList<>();
			lista.add(0);
			lista.add(v.getCliente().getId());
			lista.add(t.getId());
			lista.add(v.getFecha());
			lista.add(v.isOnline());
			lista.add(v.getPrecio());
			int idVenta = connectionManager.updateDBPS(sql, lista, true);
			v.setId(idVenta);
			lista.clear();
			
			//para cada pan de la venta, insertar en panesventas
			for(int i = 0; i < panes.size(); i++){
				sql = "INSERT INTO panesventas(idPanesTienda, idVenta, cantidad) VALUES (?,?,?)";
				lista.add(panes.get(i).getIdPanesTienda());
				lista.add(idVenta);
				lista.add(panes.get(i).getCant());
				connectionManager.updateDBPS(sql, lista, false);
				lista.clear();
			}
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connectionManager.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void eliminarVenta(Venta v){
		try {
			connectionManager.connect();
			String sql = "select * from ventas where idVenta = "+v.getId();
			ResultSet resultSet = connectionManager.consultar(sql);
			if(resultSet.next()){
				sql = "DELETE FROM panesventas WHERE idVenta = " + v.getId();
				connectionManager.updateDB(sql, false);
				sql = "DELETE FROM ventas WHERE idVenta = " + v.getId();
				connectionManager.updateDB(sql, false);
			}
			else{
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error");
				alerta.setContentText("La venta a eliminar no existe.");
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
	
	public ArrayList<Venta> obtenerVentas(){
		ArrayList<Venta> ventas = new ArrayList<>();
		ArrayList<Panes> panesVenta = new ArrayList<>();
		
		try {
			connectionManager.connect();
			String sql = "SELECT * FROM ventas";
			ResultSet rSet = connectionManager.consultar(sql);
			//Recorrer ventas
			while(rSet.next()){
				
				sql = "SELECT * FROM panesventas WHERE idVenta = "+rSet.getInt(1);
				ResultSet rSet2 = connectionManager.consultar(sql);
				//Recorrer cada panes de la venta
				while(rSet2.next()){
					//Obtener la cantidad de panes
					sql = "SELECT * FROM panestienda WHERE idPanesTienda = "+rSet2.getInt(1);
					ResultSet rSet3 = connectionManager.consultar(sql);
					rSet3.next();
					Pan pan = new Pan(rSet3.getInt(2), rSet3.getString(3), rSet3.getString(4), rSet3.getFloat(5));
					Panes panes = new Panes(rSet2.getInt(1), pan, rSet2.getInt(3));
					panesVenta.add(panes);
				}
				//Obtener cliente
				ClienteDAO clienteDAO = new ClienteDAO(this.connectionManager);
				Cliente cliente = clienteDAO.buscarCliente(rSet.getInt(2));
				//Obtener tienda
				Tienda tienda = new Tienda(0, "", "", "");
				ResultSet rSet3 = connectionManager.consultar("SELECT * FROM tiendas WHERE idTienda = "+rSet.getInt(3));
				rSet3.next();
				tienda.setId(rSet.getInt(3));
				tienda.setNombre(rSet3.getString(3));
				tienda.setLocalidad(rSet3.getString(2));
				tienda.setContrasenya(rSet3.getString(4));
				//Crear venta
				Venta venta = new Venta(rSet.getInt(1), cliente, rSet.getDate(4), rSet.getBoolean(5), panesVenta, tienda, rSet.getFloat(6));
				//Anyadir venta al array
				ventas.add(venta);
				//Limpiar array panesventa
				panesVenta = new ArrayList<>();
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
		return ventas;
		
	}
	
	public Venta buscarVenta(int id){
		ArrayList<Panes> panesVenta = new ArrayList<>();
		Venta venta = null;
		try {
			connectionManager.connect();
			String sql = "SELECT * FROM ventas WHERE idVenta="+id;
			ResultSet rSet = connectionManager.consultar(sql);
			
			sql = "SELECT * FROM panesventas WHERE idVenta = "+id;
			ResultSet rSet2 = connectionManager.consultar(sql);
			//Recorrer cada panes de la venta
			while(rSet2.next()){
				//Obtener la cantidad de panes
				sql = "SELECT * FROM panestienda WHERE idPanesTienda = "+rSet2.getInt(1);
				ResultSet rSet3 = connectionManager.consultar(sql);
				rSet3.next();
				Pan pan = new Pan(rSet3.getInt(2), rSet3.getString(3), rSet3.getString(4), rSet3.getFloat(5));
				Panes panes = new Panes(rSet2.getInt(1), pan, rSet2.getInt(3));
				panesVenta.add(panes);
			}
			rSet.next();
			//Obtener cliente
			ClienteDAO clienteDAO = new ClienteDAO(this.connectionManager);
			Cliente cliente = clienteDAO.buscarCliente(rSet.getInt(2));
			//Obtener tienda
			Tienda tienda = new Tienda(0, "", "", "");
			ResultSet rSet3 = connectionManager.consultar("SELECT * FROM tiendas WHERE idTienda = "+rSet.getInt(3));
			rSet3.next();
			tienda.setId(rSet.getInt(3));
			tienda.setNombre(rSet3.getString(3));
			tienda.setLocalidad(rSet3.getString(2));
			tienda.setContrasenya(rSet3.getString(4));
			//Crear venta
			venta = new Venta(rSet.getInt(1), cliente, rSet.getDate(4), rSet.getBoolean(5), panesVenta, tienda, rSet.getFloat(6));
			
		
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connectionManager.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return venta;
		
	}
	
	
	public Venta modificarVenta(Venta v){
		Venta venta = new Venta(0, null, null, true, v.getPanes(), v.getIdTienda(), 0);
		ArrayList<Panes> panesVenta = new ArrayList<>();
		ArrayList<Object> lista = new ArrayList<>();
				
		try {
			String sql = "select * from ventas where idVenta = "+v.getId();
			ResultSet resultSet = connectionManager.consultar(sql);
			
			if(resultSet.next()){
				//Eliminar los panes de la venta
				connectionManager.connect();
				sql = "DELETE FROM panesventas WHERE idVenta = "+v.getId();
				connectionManager.updateDB(sql, false);
				
				//Modificar la venta
				//connectionManager.connect();
				//sql = "UPDATE ventas SET idVenta = "+v.getId()+", idCliente = "+v.getCliente().getId()+", idTienda = "+v.getIdTienda()+", fecha = '"+v.getFecha()+"', online = "+v.isOnline()+", precio = "+v.getPrecio();
				sql = "UPDATE ventas SET idCliente = "+v.getCliente().getId()+", idTienda = "+v.getIdTienda()+", fecha = '"+v.getFecha()+"', online = "+v.isOnline()+", precio = "+v.getPrecio()+" WHERE idVenta= "+v.getId();
				connectionManager.updateDB(sql, false);
				
				//Crear los panes de la venta modificada
				for(int i = 0;  i < v.getPanes().size(); i++){
					sql = "INSERT INTO panesventas(idPanesTienda, idVenta, cantidad) VALUES (?,?,?)";
					lista.add(v.getPanes().get(i).getIdPanesTienda());
					lista.add(v.getId());
					lista.add(v.getPanes().get(i).getCant());
					connectionManager.updateDBPS(sql, lista, false);
					lista.clear();
				}
				
				//Obtener la venta modificada
				sql = "SELECT * FROM ventas WHERE idVenta = "+v.getId();
				ResultSet rSet = connectionManager.consultar(sql);
				rSet.next();
				sql = "SELECT * FROM panesventas WHERE idVenta = "+v.getId();
				ResultSet rSet2 = connectionManager.consultar(sql);
				//Recorrer cada panes de la venta
				while(rSet2.next()){
					//Obtener la cantidad de panes
					int cantidad = rSet2.getInt(3);
					int idPanesTienda = rSet2.getInt(1);
					sql = "SELECT * FROM panestienda WHERE idPanesTienda = "+idPanesTienda;
					ResultSet rSet3 = connectionManager.consultar(sql);
					rSet3.next();
					Pan pan = new Pan(rSet3.getInt(2), rSet3.getString(3), rSet3.getString(4), rSet3.getFloat(5));
					Panes panes = new Panes(idPanesTienda, pan, cantidad);
					panesVenta.add(panes);
				}
				//Crear venta
				venta.setId(rSet.getInt(1));
				venta.setCliente(v.getCliente());
				venta.setFecha(rSet.getDate(4));
				venta.setOnline(rSet.getBoolean(5));
				venta.setPanes(panesVenta);
				venta.setTienda(v.getTienda());
				venta.setPrecio(rSet.getFloat(6));
			}
			else{
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error");
				alerta.setContentText("La venta a modificar no existe.");
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
		return venta;
		
	}
	
	public int getLastGenerated(ConnectionManager connectionManager){
		int res = 0;
		try {
			ResultSet resultSet = connectionManager.consultar("select auto_increment from INFORMATION_SCHEMA.TABLES where table_name = 'ventas'");
			resultSet.next();
			res = resultSet.getInt(1);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public int getLastGeneratedPanesTienda(ConnectionManager connectionManager){
		int res = 0;
		try {
			ResultSet resultSet = connectionManager.consultar("select auto_increment from INFORMATION_SCHEMA.TABLES where table_name = 'panestienda'");
			resultSet.next();
			res = resultSet.getInt(1);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
}
