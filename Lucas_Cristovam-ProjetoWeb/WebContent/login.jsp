<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<c:import url="cabecalho.jsp" />
	
	<form action="mvc" method="POST">
		Login: <input type="text" name="login"/><br />
		Senha: <input type="password" name="pass"/><br /> 
		
		<input type="hidden" name="logica" value="Authenticate" />
		<input type="submit" value="Entrar" />
	</form>
	<c:import url="rodape.jsp" />
</body>
</html>