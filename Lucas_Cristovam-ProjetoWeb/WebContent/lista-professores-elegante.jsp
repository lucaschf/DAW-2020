<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />

	<jsp:useBean id="dao"
		class="br.edu.ifsudestemg.barbacena.dao.ProfessorDAO" />
	<table border=1>
		<tr>
			<th></th>
			<th>id</th>
			<th>Nome</th>
			<th>E-mail</th>
			<th>Grau instrucao</th>
		</tr>
		<c:forEach var="professor" items="${dao.fetchAll()}" varStatus="id">
			<tr bgcolor="#${id.count %2 == 0 ? 'ae8' : 'fff' }">
				<td><a href="remove-professor?id=${professor.id}">remover</a></td>
				<td>${professor.id}</td>
				<td>${professor.name}</td>
				<td><c:if test="${not empty professor.email}">
						<a href="mailto:${professor.email}">${professor.email}</a>
					</c:if> <c:if test="${empty professor.email}">
						E-mail não informado!
					</c:if></td>
				<td>${professor.degree}</td>
			</tr>
		</c:forEach>
	</table>
	<c:import url="rodape.jsp" />
</body>
</html>