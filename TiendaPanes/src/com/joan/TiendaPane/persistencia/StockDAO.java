package com.joan.TiendaPane.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.joan.TiendaPanes.modelo.Pan;
import com.joan.TiendaPanes.modelo.Panes;
import com.joan.TiendaPanes.modelo.Tienda;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class StockDAO {
	private ConnectionManager connectionManager;
	public StockDAO(ConnectionManager connectionManager){
		this.connectionManager = connectionManager;
	}
	
	public void crearStockFabrica(ArrayList<Panes> panes){
		
		try {
			connectionManager.connect();
			//Obteder idFabrica
			String sql = "SELECT * FROM stockfabrica";
			ResultSet rSet = connectionManager.consultar(sql);
			rSet.next();
			int idFabrica = rSet.getInt(1);
			
			//Insertar panes en PANESSTOCKFABRICA
			
			ArrayList<Object> lista = new ArrayList<>();
			for(int i = 0; i < panes.size(); i++){
				//Comprobar que pan existe en stock
				rSet = connectionManager.consultar("SELECT idPan FROM panesstockfabrica WHERE idPan = " + panes.get(i).getPan().getId());
				
				if(rSet.next()){//pan existe
					String sql2 = "UPDATE panesstockfabrica SET idStock = ?, idPan = ?, cantidad = ? WHERE idPan = " + panes.get(i).getPan().getId() + " AND idStock = " + idFabrica;
					rSet = connectionManager.consultar("SELECT cantidad FROM panesstockfabrica WHERE idPan = " + panes.get(i).getPan().getId());
					rSet.next();
					int cant = rSet.getInt(1);
					lista.add(idFabrica);
					lista.add(panes.get(i).getPan().getId());
					lista.add((panes.get(i).getCant()) + cant);
					connectionManager.updateDBPS(sql2, lista, false);
					lista.clear();
				
				}
				else{//pan no existe
					String sql1 = "INSERT INTO panesstockfabrica (idStock, idPan, cantidad) VALUES(?,?,?)";
					lista.add(idFabrica);
					lista.add(panes.get(i).getPan().getId());
					lista.add(panes.get(i).getCant());
					connectionManager.updateDBPS(sql1, lista, false);
					lista.clear();
				}
				
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
	
	public void eliminarStockFabrica(Panes panes){
		try {
			connectionManager.connect();
			String sql = "select * from panesstockfabrica where idPan = "+ panes.getPan().getId();
			ResultSet resultSet = connectionManager.consultar(sql);
			if(resultSet.next()){
				//Obteder idFabrica
				sql = "SELECT * FROM stockfabrica";
				resultSet = connectionManager.consultar(sql);	
				resultSet.next();
				int idFabrica = resultSet.getInt(1);
				//Consulta DELETE
				sql = "DELETE FROM panesstockfabrica WHERE idPan = " + panes.getPan().getId() + " AND idStock = " + idFabrica;
				connectionManager.updateDB(sql, false);
			}
			else{
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error");
				alerta.setContentText("El pan a eliminar no existe.");
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
	
	public ArrayList<Panes> obtenerStockFabrica(){
		ArrayList<Panes> panes = new ArrayList<>();
		Panes pan2 = null;
		Pan pan = null;
		int cantidad = 0;
		int idPan = 0;
				
		try {
			connectionManager.connect();
			//Obteder idFabrica
			String sql = "SELECT * FROM stockfabrica";
			ResultSet rSet = connectionManager.consultar(sql);	
			rSet.next();
			int idFabrica = rSet.getInt(1);
			//Consulta SELECT
			sql = "SELECT * FROM panesstockfabrica WHERE idStock = " + idFabrica;
			rSet = connectionManager.consultar(sql);
			while(rSet.next()){//Recorre canda clase panes del stockfabrica
				cantidad = rSet.getInt(3);
				idPan = rSet.getInt(2);
				//Obtener el pan para crear la clase panes
				sql = "SELECT * FROM panesfabrica WHERE idPan = "+idPan;
				ResultSet rSet2 = connectionManager.consultar(sql);
				rSet2.next();
				pan = new Pan(rSet2.getInt(1), rSet2.getString(2), rSet2.getString(3), rSet2.getFloat(4));
				
				pan2 = new Panes(pan.getId(), pan, cantidad);
								
				panes.add(pan2);
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
		
		
		return panes;
	}
	
	public Panes modificarStockFabrica(Panes panes){
		Panes pan2 = null;
		Pan pan = null;
		int cantidad = 0;
				
		try {
			connectionManager.connect();
			String sql = "SELECT * FROM panesstockfabrica WHERE idPan = "+ panes.getPan().getId();
			ResultSet rSet = connectionManager.consultar(sql);
			if(rSet.next()){
				//Obteder idFabrica
				sql = "SELECT * FROM stockfabrica";
				rSet = connectionManager.consultar(sql);
				rSet.next();
				int idFabrica = rSet.getInt(1);
				//Consulta UPDATE
				sql = "UPDATE panesstockfabrica SET idStock = " + idFabrica + ", idPan = " + panes.getPan().getId() + 
						", cantidad = " + panes.getCant() + " WHERE idPan = " + panes.getPan().getId() + " AND idStock = " + idFabrica;
				connectionManager.updateDB(sql, false);
				//devolver el panes modificado
				sql = "SELECT * FROM panesstockfabrica WHERE idStock = "+ idFabrica + " AND idPan = "+ panes.getPan().getId();
				rSet = connectionManager.consultar(sql);
				rSet.next();
				cantidad = rSet.getInt(3);
				//obtener el pan del panes
				sql = "SELECT * FROM panesfabrica WHERE idPan = "+panes.getPan().getId();
				ResultSet rSet2 = connectionManager.consultar(sql);
				rSet2.next();
				pan = new Pan(0, "", "", (float) 0.0);
				pan.setId(rSet2.getInt(1));
				pan.setTipo(rSet2.getString(2));
				pan.setNombre(rSet2.getString(3));
				pan.setPrecio(rSet2.getFloat(4));
				
				pan2 = new Panes(pan.getId(), pan, cantidad);
					
				
			}
			else{
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error");
				alerta.setContentText("El pan a modificar no existe.");
				alerta.show();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				connectionManager.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pan2;
	}
	
	
	public void crearStockTienda(Tienda tienda, ArrayList<Panes> panes){
		try {
			connectionManager.connect();
			//Comprobar si la tienda tiene stock
			String sql = "SELECT * FROM stocktienda WHERE idTienda = " + tienda.getId();
			ResultSet rSet = connectionManager.consultar(sql);
			ResultSet rSet2 = null;
			if (!rSet.next()){//Si no tiene stock
			    //Crear el stock en la tabla STOCKTIENDA
				sql = "INSERT INTO stocktienda (idStock, idTienda) VALUES(0,"+ tienda.getId()+")";
				//Obtener el idStock
				int idStock = connectionManager.updateDB(sql, true);
								
				//Para cada Panes
				ArrayList<Object> lista = new ArrayList<>();
				for(int i = 0; i < panes.size(); i++){
					//Comprobar que pan y el idStock existe en PanesTienda
					rSet = connectionManager.consultar("SELECT panestienda.idPanesTienda FROM panestienda, panesstock WHERE panestienda.idPan = "+panes.get(i).getPan().getId() +" AND panestienda.idPanesTienda = panesstock.idPanesTienda AND panesstock.idStock = "+idStock);
					//SELECT panestienda.idPanesTienda FROM panestienda WHERE panestienda.idPan = 3 AND panestienda.idStock = 1
					if(rSet.next()){//pan existe
						String sql2 = "";
						int idPanesTienda = rSet.getInt(1);
						sql2 = "UPDATE panesstock SET idStock = ?, idPanesTienda = ?, cantidad = ? WHERE idPanesTienda = " + idPanesTienda + " AND idStock = " + idStock;
						rSet2 = connectionManager.consultar("SELECT cantidad FROM panesstock WHERE panesstock.idStock = " + idStock +" AND panesstock.idPanesTienda = "+idPanesTienda);
						rSet2.next();
						int cant = rSet2.getInt(1);
						lista.add(idStock);
						lista.add(idPanesTienda);
						lista.add((panes.get(i).getCant()) + cant);
						connectionManager.updateDBPS(sql2, lista, false);
						lista.clear();
					
					}
					else{//pan no existe
						String sql1 = "INSERT INTO panestienda (idPanesTienda, idPan, tipo, nombre, precio) VALUES(?,?,?,?,?)";
						String sql3 = "INSERT INTO panesstock (idStock, idPanesTienda, cantidad) VALUES(?,?,?)";
						lista.add(0);
						lista.add(panes.get(i).getPan().getId());
						lista.add(panes.get(i).getPan().getTipo());
						lista.add(panes.get(i).getPan().getNombre());
						lista.add(panes.get(i).getPan().getPrecio());
						int idPanesTienda = connectionManager.updateDBPS(sql1, lista, true);
						lista.clear();
						
						lista.add(idStock);
						lista.add(idPanesTienda);
						lista.add(panes.get(i).getCant());
						connectionManager.updateDBPS(sql3, lista, false);
						lista.clear();
							
					}
										
				}
				
			}
			else{
				//Insertar stock en PANESSTOCK
				int idStock = rSet.getInt(1);
				sql = "INSERT INTO panesstock (idStock, idPanesTienda, cantidad) VALUES(?,?,?)";
				//Para cada Panes
				ArrayList<Object> lista = new ArrayList<>();
				for(int i = 0; i < panes.size(); i++){
					//Comprobar que pan y el idStock existe en PanesTienda
					rSet = connectionManager.consultar("SELECT panestienda.idPanesTienda FROM panestienda, panesstock WHERE panestienda.idPan = "+panes.get(i).getPan().getId() +" AND panestienda.idPanesTienda = panesstock.idPanesTienda AND panesstock.idStock = "+idStock);
					//SELECT panestienda.idPanesTienda FROM panestienda WHERE panestienda.idPan = 3 AND panestienda.idStock = 1
					if(rSet.next()){//pan existe
						String sql2 = "";
						int idPanestienda = rSet.getInt(1);
						sql2 = "UPDATE panesstock SET idStock = ?, idPanesTienda = ?, cantidad = ? WHERE idPanesTienda = " + idPanestienda + " AND idStock = " + idStock;
						rSet2 = connectionManager.consultar("SELECT cantidad FROM panesstock WHERE panesstock.idPanesTienda = " + idPanestienda +" AND panesstock.idStock = "+idStock);
						rSet2.next();
						int cant = rSet2.getInt(1);
						lista.add(idStock);
						lista.add(idPanestienda);
						lista.add((panes.get(i).getCant()) + cant);
						connectionManager.updateDBPS(sql2, lista, false);
						lista.clear();
					
					}
					else{//pan no existe
						String sql1 = "INSERT INTO panestienda (idPanesTienda, idPan, tipo, nombre, precio) VALUES(?,?,?,?,?)";
						String sql3 = "INSERT INTO panesstock (idStock, idPanesTienda, cantidad) VALUES(?,?,?)";
						lista.add(0);
						lista.add(panes.get(i).getPan().getId());
						lista.add(panes.get(i).getPan().getTipo());
						lista.add(panes.get(i).getPan().getNombre());
						lista.add(panes.get(i).getPan().getPrecio());
						int idPanesTienda = connectionManager.updateDBPS(sql1, lista, true);
						lista.clear();
						
						lista.add(idStock);
						lista.add(idPanesTienda);
						lista.add(panes.get(i).getCant());
						connectionManager.updateDBPS(sql3, lista, false);
						lista.clear();
						
					}
										
				}
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
	
	public void eliminarStockTienda(Tienda tienda, Panes panes){
		try {
			connectionManager.connect();
			String sql = "select * from tiendas where idTienda = "+tienda.getId();
			ResultSet rSet = connectionManager.consultar(sql);
			
			if(rSet.next()){
				sql = "select * from panesstock where idPanesTienda = "+panes.getIdPanesTienda();
				rSet = connectionManager.consultar(sql);
				if(rSet.next()){
					//Obteder el idStock de la tienda
					sql = "SELECT * FROM stocktienda WHERE idTienda = "+ tienda.getId();
					rSet = connectionManager.consultar(sql);
					rSet.next();
					int idStock = rSet.getInt(1);
					//Consulta DELETE sobre PANESSTOCK
					sql = "DELETE FROM panesstock WHERE idPanesTienda = " + panes.getIdPanesTienda() + " AND idStock = " + idStock;
					connectionManager.updateDB(sql, false);
					sql = "DELETE FROM panestienda WHERE idPanesTienda = "+ panes.getIdPanesTienda();
					connectionManager.updateDB(sql, false);
				}else{
					Alert alerta = new Alert(AlertType.ERROR);
					alerta.setTitle("Error");
					alerta.setContentText("El pan a eliminar no existe");
					alerta.show();
				}
			}else{
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error");
				alerta.setContentText("La tienda introducida no existe");
				alerta.show();
			}
			
		} catch (SQLException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setContentText("El pan a eliminar esta usandose en una venta");
			alerta.show();
			e.printStackTrace();
		} finally {
			try {
				connectionManager.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public ArrayList<Panes> obtenerStockTienda(Tienda tienda){
		ArrayList<Panes> panes = new ArrayList<>();
		Panes pan2 = null;
		Pan pan = null;
		int cantidad = 0;
		int idPanesTienda;
		try {
			connectionManager.connect();
			//Obteder el idStock de la tienda
			String sql = "SELECT * FROM stocktienda WHERE idTienda = "+ tienda.getId();
			ResultSet rSet = connectionManager.consultar(sql);
			rSet.next();
			int idStock = rSet.getInt(1);
			//Consulta SELECT sobre PANESSTOCK
			sql = "SELECT * FROM panesstock WHERE idStock = " + idStock;
			rSet = connectionManager.consultar(sql);
			while(rSet.next()){
				cantidad = rSet.getInt(3);
				idPanesTienda = rSet.getInt(2);
				//Obtener el pan para crear la clase panes
				//ArrayList<Pan> panConsulta = new ArrayList<>();
				sql = "SELECT * FROM panestienda WHERE idPanesTienda = "+idPanesTienda;
				ResultSet rSet2 = connectionManager.consultar(sql);
				rSet2.next();
				pan = new Pan(rSet2.getInt(2), rSet2.getString(3), rSet2.getString(4), rSet2.getFloat(5));
				pan2 = new Panes(idPanesTienda, pan, cantidad);
				panes.add(pan2);
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
		
		return panes;
	}
	
	public Panes modificarStockTienda(Tienda tienda, Panes panes){
		Panes pan2 = null;
		Pan pan = null;
		int cantidad = 0;
		
		try {
			connectionManager.connect();
			String sql = "select * from tiendas where idTienda = "+tienda.getId();
			ResultSet rSet = connectionManager.consultar(sql);
			
			if(rSet.next()){
				sql = "select * from panesstock where idPanesTienda = "+panes.getIdPanesTienda();
				rSet = connectionManager.consultar(sql);
				if(rSet.next()){
					//Obteder el idStock de la tienda
					sql = "SELECT * FROM stocktienda WHERE idTienda = "+ tienda.getId();
					rSet = connectionManager.consultar(sql);
					rSet.next();
					int idStock = rSet.getInt(1);
					//Consulta UPDATE sobre panesstock
					sql = "UPDATE panesstock SET idStock = " + idStock + ", idPanesTienda = " + panes.getIdPanesTienda() + 
							", cantidad = " + panes.getCant() + " WHERE idPanesTienda = " + panes.getIdPanesTienda() + " AND idStock = " + idStock;
					connectionManager.updateDB(sql, false);
					//devolver el panes modificado
					sql = "SELECT * FROM panesstock WHERE idStock = "+ idStock + " AND idPanesTienda = "+ panes.getIdPanesTienda();
					rSet = connectionManager.consultar(sql);
					rSet.next();
					cantidad = rSet.getInt(3);
					
					//ArrayList<Pan> panConsulta = new ArrayList<>();
					sql = "SELECT * FROM panestienda WHERE idPanesTienda = " + panes.getIdPanesTienda();
					ResultSet rSet2 = connectionManager.consultar(sql);
					rSet2.next();
					pan = new Pan(0, "", "", (float) 0.0);
					pan.setId(rSet2.getInt(2));
					pan.setTipo(rSet2.getString(3));
					pan.setNombre(rSet2.getString(4));
					pan.setPrecio(rSet2.getFloat(5));
								
					pan2 = new Panes(panes.getIdPanesTienda(), pan, cantidad);
				}else{
					Alert alerta = new Alert(AlertType.ERROR);
					alerta.setTitle("Error");
					alerta.setContentText("El pan a eliminar no existe");
					alerta.show();
				}
			}else{
				Alert alerta = new Alert(AlertType.ERROR);
				alerta.setTitle("Error");
				alerta.setContentText("La tienda introducida no existe");
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
		return pan2;
	}
	
	public int getLastGeneratedPanesTienda(ConnectionManager connectionManager){
		int res = 0;
		int autoincrement = 0;
		try {
			ResultSet resultSet = connectionManager.consultar("select auto_increment from INFORMATION_SCHEMA.TABLES where table_name = 'panestienda'");
			resultSet.next();
			res = resultSet.getInt(1);
			autoincrement = res + 1;
			connectionManager.updateDB("ALTER TABLE panestienda AUTO_INCREMENT =" + autoincrement, false);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public int getLastGeneratedPanesFabrica(ConnectionManager connectionManager){
		int res = 0;
		try {
			ResultSet resultSet = connectionManager.consultar("select auto_increment from INFORMATION_SCHEMA.TABLES where table_name = 'panesfabrica'");
			resultSet.next();
			res = resultSet.getInt(1);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
}
