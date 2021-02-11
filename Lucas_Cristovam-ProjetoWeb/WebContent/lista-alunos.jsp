<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*,
			br.edu.barbacena.ifsudestemg.daw.dao.*,
			br.edu.barbacena.ifsudestemg.daw.model.*"%>

<html>
<body>

	<table border=1>
		<tr>
			<th>Nome</th>
			<th>E-mail</th>
			<th>Endereço</th>
			<th>Data de Nascimento</th>
		</tr>
		<%
		StudentDAO dao = new StudentDAO();
		List<Student> students = dao.fetchAll();
		for (Student student :students) {
		%>
		<tr>
			<td><%=student.getName()%></td>
			<td><%=student.getEmail()%></td>
			<td><%=student.getAddress()%></td>
			<td><%=student.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
		</tr>
		<%
		}
		%>
	</table>
</body>
</html>