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
	<img src="images/logo.png">
	<h2>Sistema de Gest�o Acad�mica do Lucas Cristovam</h2>
	<hr />
	<c:if test="${sessionScope.status == true}">
		Ol� ${sessionScope.name }, seja bem-vindo! <br />
		
		Clique <a href="mvc?logica=Logout">aqui</a> para sair<br />
	</c:if>
</body>
</html>