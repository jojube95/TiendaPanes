package com.joan.TiendaPanes.servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import com.joan.TiendaPane.persistencia.ConnectionManager;
import com.joan.TiendaPane.persistencia.TiendaDAO;
import com.joan.TiendaPane.persistencia.VentaDAO;
import com.joan.TiendaPanes.modelo.Cliente;
import com.joan.TiendaPanes.modelo.Tienda;
import com.joan.TiendaPanes.modelo.Venta;


/**
 * Servlet implementation class Usuario
 */
@WebServlet("/Usuario")
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ConnectionManager connectionManager;
    /**
     * @see HttpServlet#HttpServlet()
     */
    
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
		//Obtener la lista de las compras de este usuario
		//this.connectionManager = new ConnectionManager(miPool);
		VentaDAO ventaDAO = new VentaDAO(this.connectionManager);
		ArrayList<Venta> ventasFabrica = ventaDAO.obtenerVentas();
		ArrayList<Venta> ventasCliente = new ArrayList<>();
		HttpSession session = request.getSession();
		
		for(int i = 0; i < ventasFabrica.size(); i++){
			Cliente cliente = (Cliente) session.getAttribute("usuario");
			if(ventasFabrica.get(i).getCliente().getId() == cliente.getId()){
				ventasCliente.add(ventasFabrica.get(i));
			}
		}
		session.setAttribute("listaCompras", ventasCliente);
		response.sendRedirect("compras.jsp");
		//Dispatcher a compras con la lista como atributo
		//RequestDispatcher miDispatcher = request.getRequestDispatcher("/compras.jsp");
		//request.setAttribute("listaCompras", ventasCliente);
		//miDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TiendaDAO tiendaDAO = new TiendaDAO(this.connectionManager);
		ArrayList<Tienda> tiendas = tiendaDAO.obtenerTiendas();
		HttpSession session = request.getSession();
		session.setAttribute("TIENDAS", tiendas);
		response.sendRedirect("tiendas.jsp");
	}

}
