package it.unisa;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import it.model.UserBean;

@WebServlet("/Login")
public class Login extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			String isDriverManager = request.getParameter("driver");
			if(isDriverManager == null || isDriverManager.equals("")) {
				isDriverManager = "datasource";
			}
			
			IProductDao productDao = null;
	
			if (isDriverManager.equals("drivermanager")) {
				DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext()
						.getAttribute("DriverManager");
				productDao = new DaoDriverMan(dm);			
			} else {
				DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
				productDao = new DaoDataSource(ds);
			}
		
			String username = request.getParameter("email");
			String password = request.getParameter("password");
			List<String> errors = new ArrayList<>();
        	RequestDispatcher dispatcherToLoginPage = request.getRequestDispatcher("login.jsp");

			
			if(username == null || username.trim().isEmpty()) {
				errors.add("Il campo username non può essere vuoto!");
			}
            if(password == null || password.trim().isEmpty()) {
            	errors.add("Il campo password non può essere vuoto!");
			}
            if (!errors.isEmpty()) {
            	request.setAttribute("errors", errors);
            	dispatcherToLoginPage.forward(request, response);
            	return; // note the return statement here!!!
            }
            

            /*
            String hashPassword = toHash(password);
			String hashPasswordToBeMatch = 
					"1c573dfeb388b562b55948af954a7b344dde1cc2099e978a992790429e7c01a4205506a93d9aef3bab32d6f06d75b7777a7ad8859e672fedb6a096ae369254d2";
			
			*/
			
			UserBean match=null;
			try {
				match=productDao.loginUserOrAdmin(username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(match==null) {
				errors.add("Username o password non validi!");
				request.setAttribute("errors", errors);
				dispatcherToLoginPage.forward(request, response);
			} else if( match.getAdmin() ) { //sono state usate credenziali di admin
				request.getSession().setAttribute("isAdmin", Boolean.TRUE); //inserisco il token nella sessione
				request.getSession().setAttribute("logged", match);
				response.sendRedirect("admin/ProductView.jsp");			
			} else if( !match.getAdmin() && match!=null ) { //sono state usate credenziali di un utente
				request.getSession().setAttribute("isAdmin", Boolean.FALSE); //inserisco il token nella sessione
				request.getSession().setAttribute("logged", match);
				response.sendRedirect("store.jsp");
			}
			/*
		
			if(username.equals("admin") && hashPassword.equals(hashPasswordToBeMatch)){ //admin
				request.getSession().setAttribute("isAdmin", Boolean.TRUE); //inserisco il token nella sessione
				response.sendRedirect("admin/ProductView.jsp");
			} else if (username.equals("user") && hashPassword.equals(hashPasswordToBeMatch)){ //user
				request.getSession().setAttribute("isAdmin", Boolean.FALSE); //inserisco il token nella sessione
				response.sendRedirect("common/protected.jsp");
			} else {
				errors.add("Username o password non validi!");
				request.setAttribute("errors", errors);
				dispatcherToLoginPage.forward(request, response);
			}
			*/
			
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	private static final long serialVersionUID = 1L;


}
