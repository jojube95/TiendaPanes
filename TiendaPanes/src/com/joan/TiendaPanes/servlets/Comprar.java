package com.joan.TiendaPanes.servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.joan.TiendaPanes.modelo.Cliente;
import com.joan.TiendaPanes.modelo.Panes;
import com.joan.TiendaPanes.modelo.Tienda;
import com.joan.TiendaPanes.modelo.Venta;
import com.joan.TiendaPanes.persistencia.ConnectionManager;
import com.joan.TiendaPanes.persistencia.StockDAO;
import com.joan.TiendaPanes.persistencia.TiendaDAO;


/**
 * Servlet implementation class Comprar
 */
@WebServlet("/Comprar")
public class Comprar extends HttpServlet {
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
		HttpSession sesion = request.getSession();
		int idTienda = Integer.parseInt(request.getParameter("idTienda"));
		ArrayList<Tienda> tiendas = new TiendaDAO(this.connectionManager).obtenerTiendas();
		Tienda tienda = null;
		for(int i = 0; i < tiendas.size(); i++){
			if(tiendas.get(i).getId() == idTienda){
				tienda = tiendas.get(i);
				break;
			}
		}
		sesion.setAttribute("TIENDA", tienda);
		StockDAO stockDAO = new StockDAO(this.connectionManager);
		ArrayList<Panes> panesTienda = stockDAO.obtenerStockTienda(tienda);
		
		sesion.setAttribute("CATALOGO", panesTienda);
		response.sendRedirect("catalogo.jsp");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String elComando = request.getParameter("boton");
		HttpSession sesion = request.getSession();
		
		//Redirigir dependiendo del comando
		switch(elComando){
			case "aceptar":
				@SuppressWarnings("rawtypes") Enumeration paramaterNames = request.getParameterNames();
				paramaterNames.nextElement();
				ArrayList<Panes> panesVenta = new ArrayList<>();
				Tienda tienda = (Tienda) sesion.getAttribute("TIENDA");
				Cliente cliente = (Cliente) sesion.getAttribute("usuario");
				ArrayList<Panes> panesTienda = new StockDAO(this.connectionManager).obtenerStockTienda(tienda);
				while(paramaterNames.hasMoreElements() ) {
					int idPan = Integer.parseInt(request.getParameter((String) paramaterNames.nextElement()));
					int cant = Integer.parseInt(request.getParameter((String) paramaterNames.nextElement()));
					
					//Añadir el pan del stock a la venta
					for(int i = 0; i < panesTienda.size(); i++){
						if(panesTienda.get(i).getIdPanesTienda() == idPan){
							Panes panes = new Panes(idPan, panesTienda.get(i).getPan(), cant);
							panesVenta.add(panes);
						}
					}

				}
				//Apanyar panesVenta, junta duplicados
				for(int i = 0; i < panesVenta.size(); i++){
					for(int j = 0; j < panesVenta.size(); j++){
						if((panesVenta.get(i).getPan().getId() == panesVenta.get(j).getPan().getId()) && i!=j){
							panesVenta.get(i).setCant(panesVenta.get(i).getCant() + panesVenta.get(j).getCant());
							panesVenta.remove(j);
						}
					}
				}
				
				java.util.Date utilDate = new java.util.Date();
				Date date = new Date(utilDate.getTime());
				Venta venta = new Venta(0, cliente, date, true, panesVenta, tienda);
				sesion.setAttribute("COMPRA", venta);
				response.sendRedirect("confirmar.jsp");
				break;
			case "cancelar":
				sesion.removeAttribute("TIENDA");
				sesion.removeAttribute("CATALOGO");
				response.sendRedirect("usuario.jsp");
				
				break;
		}
		
		
		
	}

}
