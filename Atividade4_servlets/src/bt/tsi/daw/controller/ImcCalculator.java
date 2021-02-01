package bt.tsi.daw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/imc")
public class ImcCalculator extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		var out = response.getWriter();

		out.println("<html>");
		out.println("<body>");
		out.println("<h2>C�lculo imc</h2>");

		try {
			double height = Double.parseDouble(request.getParameter("height"));
			double weight = Double.parseDouble(request.getParameter("weight"));

			var imc = calculateImc(height, weight);

			out.println(String.format("<p>Imc = %1.2f </p>", imc));
			out.println(String.format("<p>%s</p>", generateMessage(imc)));
		} catch (Exception e) {
			out.println("<p>Falha ao recuperar os parametros</p>");
		}

		out.println("</body>");
		out.println("</html>");
	}

	private double calculateImc(double height, double weight) {
		return weight / (height * height);
	}

	private String generateMessage(double imc) {

		if (imc < 18.5)
			return "Cuidado! Voc� est� muito abaixo do peso!";

		if (imc < 25.0)
			return "Parab�ns! Voc� est� em seu peso ideal!";

		if (imc < 30.0)
			return "Aten��o! Voc� est� acima de seu peso ideal!";

		if (imc < 35.0)
			return "Aten��o! Obesidade grau 1!";

		if (imc < 40.0)
			return "Cuidado! Obesidade grau 2!";

		return "Cuidado! Obesidade grau 3";
	}
}
