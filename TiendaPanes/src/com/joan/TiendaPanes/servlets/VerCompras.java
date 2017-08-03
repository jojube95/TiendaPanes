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
 * Servlet implementation class VerCompras
 */
@WebServlet("/VerCompras")
public class VerCompras extends HttpServlet {
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
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		VentaDAO ventaDAO = new VentaDAO(this.connectionManager);
		
		Venta venta = ventaDAO.buscarVenta(id);
		
		System.out.print(venta.getId());
		HttpSession sesion = request.getSession();
		sesion.setAttribute("compra", venta);
		response.sendRedirect("panesCompra.jsp");
		System.out.println("finaliza el doGet");	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
