package com.joan.TiendaPanes.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.joan.TiendaPanes.modelo.Venta;
import com.joan.TiendaPanes.persistencia.ConnectionManager;
import com.joan.TiendaPanes.persistencia.VentaDAO;

/**
 * Servlet implementation class Confirmar
 */
@WebServlet("/Confirmar")
public class Confirmar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ConnectionManager connectionManager;
	
	//Definir el DataSource
  	@Resource(name="jdbc/Panaderia")
  	private DataSource miPool;
  	
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		this.connectionManager = new ConnectionManager(miPool);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Añadir la compra a la base de datos
		HttpSession sesion = request.getSession();
		String elComando = request.getParameter("boton");
		
		switch (elComando) {
		case "aceptar":
			Venta venta = (Venta) sesion.getAttribute("COMPRA");
			VentaDAO ventaDAO = new VentaDAO(this.connectionManager);
			ventaDAO.crearVenta(venta, venta.getPanes(), venta.getTienda());
			response.sendRedirect("usuario.jsp");
			break;
			
		case "cancelar":
			sesion.removeAttribute("COMPRA");
			response.sendRedirect("catalogo.jsp");
			break;
		}

		
		
	}

}
