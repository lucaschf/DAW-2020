package bt.tsi.daw.controller;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/fatorial")
public class Factorial extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		var out = response.getWriter();
		int maximum;

		try {
			maximum = Integer.parseInt(request.getParameter("maximo"));
		} catch (Exception e) {
			maximum = 10;
		}

		out.println("<html>");
		out.println("<body>");
		out.println("<h2>Fatorial</h2>");
		out.println("<table border=1px>");
		out.println("<tr>");
		out.println("<th>numero</th>");
		out.println("<th>fatorial</th>");
		out.println("</tr>");

		for (int i = 0; i <= maximum; i++) {
			var number = String.format("<tr><td>%d</td>", i);
			var value = String.format("<td>%s</td></tr>", factorial(i));

			out.println(number);
			out.println(value);
		}

		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
	}

	private BigDecimal factorial(int number) {
		BigDecimal fact = BigDecimal.ONE;

		for (int i = 1; i <= number; i++) {
			fact = fact.multiply(BigDecimal.valueOf(i));
		}

		return fact;
	}

}
