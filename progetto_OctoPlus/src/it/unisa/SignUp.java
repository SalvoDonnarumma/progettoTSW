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
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IUserDao userDao = null;

		
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		userDao = new UserDaoDataSource(ds);
	
		String username = request.getParameter("email");
		String name = request.getParameter("firstname");
		String surname = request.getParameter("lastname");
		String password = request.getParameter("password");
		String confpassword = request.getParameter("conf_password");
		String telefono = request.getParameter("phone");
		
		List<String> errors = new ArrayList<>();
		/* controllo se le due password combaciano, in caso contrario blocco la registrazione 
		 * e segnalo all'utente tramite un alert (presente nel .js della registrazione)*/
		String registrazione = "registrazione.jsp";
        if ( !password.equals(confpassword) ) {  //controllo se password e conferma password sono uguali
        	request.setAttribute("errors", errors);
        	RequestDispatcher dispatcherToLoginPage = request.getRequestDispatcher(registrazione);
        	dispatcherToLoginPage.forward(request, response);
        	return;
        }
        
        if( password.length()<12 ) {
        	RequestDispatcher dispatcherToLoginPage = request.getRequestDispatcher(registrazione);
        	dispatcherToLoginPage.forward(request, response);
			return;
		}
		
        /* Quando un utente si deve registrare, cotrollo se l'email che vuole usare è
         * già presente e salvata nel database */
        Boolean result = false;
        try {
			List<String> emails = userDao.getAllEmails();
			for( String email : emails) 
				if(email.equals(username))
					result = true;
		} catch (SQLException e1) {
			/*commento per riempire il try-catch*/
		}
        
        if( result ) {
        	errors.add("L'email che hai inserito non è disponibile!");
        	request.setAttribute("errors", errors);
        	RequestDispatcher dispatcherToLoginPage = request.getRequestDispatcher(registrazione);
        	dispatcherToLoginPage.forward(request, response);
			return;
		}
        
		UserBean user= new UserBean();
		try {
			user.setEmail(username);
			user.setPassword(password);
			user.setNome(name);
			user.setCognome(surname);
			user.setTelefono(telefono);		
			userDao.doSaveUser(user);
		} catch (SQLException e) {
			/*commento per riempire il try-catch*/
		}
		
		request.getSession().setAttribute("isAdmin", Boolean.FALSE); //inserisco il token nella sessione
		request.getSession().setAttribute("logged", user);
		response.sendRedirect("store.jsp");	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
