package com.joan.TiendaPanes.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import com.joan.TiendaPane.persistencia.ClienteDAO;
import com.joan.TiendaPane.persistencia.ConnectionManager;
import com.joan.TiendaPanes.modelo.Cliente;


/**
 * Servlet implementation class login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
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
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		switch (action) {
		case "logIn":
			logIn(request, response);
			break;
			
		case "registrar":
			registrar(request, response);
			break;
		case "registrarUsuario":
			registrarUsuario(request, response);
			break;
			
		case "cancelarRegistro":
			cancelarRegistro(request, response);
			break;
		default:
			break;
		}
		
	}
	
	private Cliente usuarioExiste(String usuario, String contrasenya){
		Cliente res = null;
		ClienteDAO clienteDAO = new ClienteDAO(connectionManager);
		ArrayList<Cliente> usuarios = clienteDAO.obtenerClientes();
		for(int i = 0; i < usuarios.size(); i++){
			if(usuarios.get(i).getUsuario().equals(usuario)){
				if(usuarios.get(i).getPass().equals(contrasenya)){
					res = usuarios.get(i);
					return res;
				}
				else{
					return null;
				}
			}
		}
		return null;
	}
	
	private void logIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Cliente cliente = usuarioExiste(request.getParameter("tUsuario"), request.getParameter("tContrasenya"));
		HttpSession sesion = request.getSession();
		if(cliente!=null && sesion.getAttribute("usuario") == null){
			//Enviar ese request a la pagina jsp
			sesion.setAttribute("usuario", cliente);
			
			//RequestDispatcher miDispatcher = request.getRequestDispatcher("/usuario.jsp");
			//miDispatcher.forward(request, response);
			response.sendRedirect("usuario.jsp");
		}
		else{
			//Volver a la pagina de login
			RequestDispatcher miDispatcher = request.getRequestDispatcher("/index.jsp");
			miDispatcher.forward(request, response);
			//response.sendRedirect("index.jsp");
		}
	}
	
	private void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Enviar ese request a la pagina jsp
		RequestDispatcher miDispatcher = request.getRequestDispatcher("/registro.jsp");
		miDispatcher.forward(request, response);
	}
	
	private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Añadir usuario a la base de datos y pasar a la pagina de logIn
		ClienteDAO clienteDAO = new ClienteDAO(this.connectionManager);
		try {
			this.connectionManager.connect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int id = clienteDAO.getLastGenerated(this.connectionManager);
		try {
			this.connectionManager.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    //Date convertedCurrentDate = sdf.parse(request.getParameter("tFecha"));
	    java.sql.Date fecha = java.sql.Date.valueOf(request.getParameter("tFecha"));
		Cliente cliente = new Cliente(request.getParameter("tNombre"), id, request.getParameter("tLocalidad"), fecha, true, request.getParameter("tUsuario"), request.getParameter("tContrasenya"));
		
		clienteDAO.crearCliente(cliente);
		//Volver a la pagina de login
		RequestDispatcher miDispatcher = request.getRequestDispatcher("/index.jsp");
		miDispatcher.forward(request, response);
		
		
	}
	
	private void cancelarRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Volver a la pagina de index
		RequestDispatcher miDispatcher = request.getRequestDispatcher("/index.jsp");
		miDispatcher.forward(request, response);
	}

}
