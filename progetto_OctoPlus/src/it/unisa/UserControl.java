package it.unisa;

import java.io.IOException; 
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

/**
 * Servlet implementation class AdminControl
 */
@WebServlet("/AdminControl")
public class UserControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String isDriverManager = request.getParameter("driver");
		if(isDriverManager == null || isDriverManager.equals("")) {
			isDriverManager = "datasource";
		}
		
		IUserDao adminDao = null;

		if (isDriverManager.equals("drivermanager")) {
			DriverManagerConnectionPool dm = (DriverManagerConnectionPool) getServletContext()
					.getAttribute("DriverManager");
		//	adminDao = new DaoDriverMan(dm);			
		} else {
			DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
			adminDao = new UserDaoDataSource(ds);
		}
		
		String action = request.getParameter("action");

		try {
			if (action != null) {
				if (action.equalsIgnoreCase("read")) {
					;
				} else if (action.equalsIgnoreCase("delete")) {
					String email = request.getParameter("email");
					System.out.println("email da cancellare: "+email);
					adminDao.doDeleteUser(email);
				} else if (action.equalsIgnoreCase("insert")) {
					
					String email = request.getParameter("email");
					String password = request.getParameter("password");
					String cognome = request.getParameter("cognome");
					String telefono = request.getParameter("phone");
					
					UserBean bean = new UserBean();
					bean.setEmail(email);
					bean.setPassword(password);
					bean.setCognome(cognome);
					bean.setTelefono(telefono);
					adminDao.doSaveAdmin(bean);
				} else if( action.equalsIgnoreCase("cgpass")) {
					String oldPass = request.getParameter("currentPassword");
					String newPass = request.getParameter("newPassword");
					String confPass = request.getParameter("confirmPassword");
					UserBean bean = (UserBean) request.getSession().getAttribute("logged");
					
					List<String> errors = new ArrayList<>();
		        	RequestDispatcher dispatcherChangePassPage = request.getRequestDispatcher("changepass.jsp");
		        	
		        	
		        	System.out.println("Le due nuove pass sono uguali: "+newPass.equals(confPass));
					if( !newPass.equals(confPass) ) {
						errors.add("La password nuova e la password di conferma non corrispondono!");
						dispatcherChangePassPage = request.getRequestDispatcher("changepass.jsp");
						request.setAttribute("errors", errors);
						dispatcherChangePassPage.forward(request, response);
						return;
					}
					
					if( !adminDao.comparePass(bean.getPassword(), oldPass) ) {
						errors.add("La vecchia password inserita non &egrave; valida!");
						dispatcherChangePassPage = request.getRequestDispatcher("changepass.jsp");
						request.setAttribute("errors", errors);
						dispatcherChangePassPage.forward(request, response);
						return;
					}
					
					if( newPass.length()<12 ) {
						dispatcherChangePassPage = request.getRequestDispatcher("changepass.jsp");
						dispatcherChangePassPage.forward(request, response);
						return;
					}
					
					if(adminDao.changePass(confPass, bean.getId()))
							System.out.println("Password cambiata con successo!");
				}
			}			
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
		
		String sort = request.getParameter("sort");
		
		try {
			request.removeAttribute("users");
			request.setAttribute("users", adminDao.doRetrieveAllUsers(sort));
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		String fromStore = request.getParameter("fromStore");
		
		RequestDispatcher dispatcher = null;
		
		if( fromStore.equalsIgnoreCase("cgpass")) {
			dispatcher = getServletContext().getRequestDispatcher("/userprofile.jsp");
		} else if(  fromStore.equalsIgnoreCase("get")) {
			dispatcher = getServletContext().getRequestDispatcher("/singleproduct.jsp");
		} else if ( Boolean.parseBoolean(fromStore) )    
				dispatcher = getServletContext().getRequestDispatcher("/store.jsp");
         	else
         		dispatcher = getServletContext().getRequestDispatcher("/admin/UserView.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
