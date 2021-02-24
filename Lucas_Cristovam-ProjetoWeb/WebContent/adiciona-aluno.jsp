<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Adiciona aluno</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	
	
	<form action="mvc" method="post">
		<label>Nome: </label> <input type="text" name="nome" /><br /> <label>E-mail:</label>
		<input type="text" name="email" /><br /> <label>Endereço: </label> <input
			type="text" name="endereco" /><br /> <label>Data
			Nascimento: </label> <input type="text" name="dataNascimento" /> <br /> <input
			type="submit" value="Gravar" /> <input type="hidden" name="logica"
			value="AddStudentLogic">
	</form>
	<c:import url="rodape.jsp" />
</body>
</html>