<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />

	<jsp:useBean id="dao"
		class="br.edu.ifsudestemg.barbacena.dao.StudentDAO" />
	<table border=1>
		<tr>
			<th>Nome</th>
			<th>E-mail</th>
			<th>Endereço</th>
			<th>Data de Nascimento</th>
		</tr>
		<c:forEach var="student" items="${dao.fetchAll()}" varStatus="id">
			<tr bgcolor="#${id.count %2 == 0 ? 'ae8' : 'fff' }">
				<td>${student.name}</td>
				<td><c:if test="${not empty student.email}">
						<a href="mailto:${student.email}">${student.email}</a>
					</c:if> <c:if test="${empty student.email}">
						E-mail não informado!
					</c:if></td>
				<td>${student.address}</td>
				<td>${student.birthDate}</td>
			</tr>
		</c:forEach>
	</table>
	<c:import url="rodape.jsp" />
</body>
</html>