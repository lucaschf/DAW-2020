<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="auth.jsp" %>
<html>
<body>
	<c:import url="cabecalho.jsp" />
	Formulário para alteração de alunos:
	<br />
	
	<form action="mvc" method="POST">
		Id: <input type="text" name="id" disabled="disabled" value="${requestScope.student.id }"/><br />
		Nome: <input type="text" name="nome" value="${requestScope.student.name}"/><br /> 
		E-mail: <input type="text" name="email" value="${requestScope.student.email}" /><br />
		Endereço: <input type="text" name="endereco" value="${requestScope.student.address}" /><br /> 
		Data de Nascimento: <input type="text" name="dataNascimento" value="${requestScope.student.birthDate}"/><br /> <input
		
		type="hidden" name="logica" value="UpdateStudentLogic" /> <input
		type="submit" value="Enviar" />
	</form>
	<c:import url="rodape.jsp" />
</body>
</html>