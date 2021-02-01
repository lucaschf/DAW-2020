package bt.tsi.daw.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/parameters")
public class Parameters extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var out = response.getWriter();
		var params = request.getParameterMap();

		out.println("<html>");
		out.println("<body>");
		out.println("<h2>Parametros da requisicao</h2>");
		out.println("<table border=1px>");
		out.println("<tr>");
		out.println("<th>Nome parametro</th>");
		out.println("<th>Valor</th>");
		out.println("</tr>");

		for (Map.Entry<String, String[]> entry : params.entrySet()) {
			
			var name = String.format("<tr><td>%s</td>", entry.getKey());
			var value = String.format("<td>%s</td></tr>", String.join(" ", Arrays.asList(entry.getValue())));
			
			out.println(name);
			out.println(value);
		}

		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
	}
}