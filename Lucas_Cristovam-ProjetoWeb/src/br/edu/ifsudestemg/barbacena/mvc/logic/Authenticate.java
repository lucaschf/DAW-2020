package br.edu.ifsudestemg.barbacena.mvc.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.ifsudestemg.barbacena.dao.UserDao;

public class Authenticate implements Logic{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		var login = request.getParameter("login");
		var pass = request.getParameter("pass");
		
		String url = "login.jsp";
		
		UserDao dao = new UserDao();
		var user = dao.validateCredentials(login, pass);
		
		if(user != null) {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(2 * 60);
			session.setAttribute("status", true);
			session.setAttribute("name", user.getLogin());
			url = "menu-principal.jsp";
		}
		
		request.getRequestDispatcher(url).forward(request, response);
	};

}
