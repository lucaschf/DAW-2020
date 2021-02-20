package br.edu.ifsudestemg.barbacena.mvc.logic;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PrimeiraLogica implements Logic {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Executando a lógica e redirecionando...");
		
		RequestDispatcher rd = request.getRequestDispatcher("/primeira-logica.jsp");
		rd.forward(request, response);
	}

}
