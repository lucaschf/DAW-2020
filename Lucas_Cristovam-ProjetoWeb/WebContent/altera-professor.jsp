<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<c:import url="cabecalho.jsp" />
	Formulário para alteração de professor:
	<br />
	
	<form action="mvc" method="POST">
		Id: <input type="text" name="id" /><br />
		Nome: <input type="text" name="nome" /><br /> 
		E-mail: <input type="text" name="email" /><br />
		Grau de formação: <input type="text" name="formacao"  /><br /> 
		
		<input type="hidden" name="logica" value="UpdateProfessorLogic" /> 
		<input type="submit" value="Enviar" />
	</form>
	<c:import url="rodape.jsp" />
</body>
</html>