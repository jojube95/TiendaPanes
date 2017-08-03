package com.joan.TiendaPane.persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;

public class ConnectionManager {
	
	
	private DataSource miPool;
	
	private Connection dbcon = null;

	public ConnectionManager(DataSource miPool){
		this.miPool = miPool;
	}

	public void connect() throws SQLException {
		//-----ESTABLECER LA CONEXION-----------
		try {
			this.dbcon = miPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection connect2() {
		//-----ESTABLECER LA CONEXION-----------
		try {
			this.dbcon = miPool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dbcon;
	}

	public void close() throws SQLException {
		if (dbcon != null) {
				dbcon.close();
				dbcon = null;
		}
	}
	
	public ResultSet consultar(String sql){
		ResultSet rSet = null;
		try {
			Statement sentencia = dbcon.createStatement();
			sentencia.executeQuery(sql);
			rSet = sentencia.getResultSet();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rSet;
		
	}
	
	public int getLastGenerated(String sql){
		int i = 0;
		try {
			PreparedStatement sentencia = dbcon.prepareStatement(sql);
			sentencia = dbcon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			sentencia.executeUpdate();
			ResultSet rSet = sentencia.getGeneratedKeys();
			rSet.next();
			i = rSet.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	public int updateDB(String sql, boolean returnId) throws SQLException {
		if(returnId){
			int id = 0;
			Statement sentencia = dbcon.createStatement();
			sentencia.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			
			if(returnId){
				ResultSet rSet = sentencia.getGeneratedKeys();
				rSet.next();
				id = rSet.getInt(1);
			}
			
			return id;
		}
		else{
			Statement sentencia = dbcon.createStatement();
			sentencia.executeUpdate(sql);
			return 0;			
		}
		
		
	}
	
	public int updateDBPS(String sql, ArrayList<Object> list, boolean returnId){
		int id = 0;
		try {
			String clase = "";
			PreparedStatement sentencia = dbcon.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for(int i = 0; i<list.size(); i++){
				clase = list.get(i).getClass().getName();
				switch (clase){
					case "java.lang.Boolean":
						sentencia.setBoolean(i+1, (boolean) list.get(i));
					break;
					case "java.lang.Integer":
						sentencia.setInt(i+1, (int) list.get(i));
					break;
					case "java.lang.String":
						sentencia.setString(i+1, (String) list.get(i));
					break;
					case "java.sql.Date":
						sentencia.setDate(i+1, (Date) list.get(i));
					break;
					
					case "java.lang.Float":
						sentencia.setFloat(i+1, (float) list.get(i));
					break;
						
					case "java.lang.Long":
						sentencia.setLong(i+1, (long) list.get(i));
					break;
				}
			}
			sentencia.executeUpdate();
			
			if(returnId){
				ResultSet rSet = sentencia.getGeneratedKeys();
				rSet.next();
				id = rSet.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public Connection getDbcon() {
		return dbcon;
	}

	public void setDbcon(Connection dbcon) {
		this.dbcon = dbcon;
	}
	
}
