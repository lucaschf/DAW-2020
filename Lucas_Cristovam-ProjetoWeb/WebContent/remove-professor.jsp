<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="auth.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	<h4>Remover Professor</h4>
	
	<form action="mvc" method="post">

		Id do Professor: <input type="text" name="id"> <br /> 
		<input type="hidden" name="logica"
			value="RemoveProfessorLogic"> 
		<input
			type="submit" value="Remover">
	</form>

	<c:import url="rodape.jsp" />
</body>
</html>