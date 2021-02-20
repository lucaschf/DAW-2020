<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Adiciona professor</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	<form action="mvc" method="post">
		<p>
			<label>Nome: </label> <input type="text" name="name" />
		</p>

		<p>
			<label>E-mail:</label><input type="text" name="email" />
		</p>

		<p>
			<label>Grau de formação: </label> <input type="text" name="degree" /><br />
		</p>

		<p>
			<input type="submit" value="Gravar" />
		</p>
		
		<input type="hidden" name="logica" value="AdddProfessorLogic">
	</form>
	<c:import url="rodape.jsp" />
</body>
</html>