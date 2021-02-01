package bt.tsi.daw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/headers")
public class Headers extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var out = response.getWriter();

		var headers = request.getHeaderNames();

		out.println("<html>");
		out.println("<body>");
		out.println("<h2>Headers da requisicao</h2>");
		out.println("<table border=1px>");
		out.println("<tr>");
		out.println("<th>Header</th>");
		out.println("<th>Valor</th>");
		out.println("</tr>");

		while (headers.hasMoreElements()) {

			String header = (String) headers.nextElement();
			var name = String.format("<tr><td>%s</td>", header);
			var value = String.format("<td>%s</td></tr>", request.getHeader(header));

			out.println(name);
			out.println(value);
		}

		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
	}
}
