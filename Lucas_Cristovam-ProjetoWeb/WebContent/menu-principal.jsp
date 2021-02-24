<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="auth.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:import url="cabecalho.jsp" />
	<a href="adiciona-aluno.jsp">Adicionar aluno</a>
	<br/>
	<a href="adiciona-professor.jsp">Adicionar professor</a>
	<br/>
	<a href="altera-aluno.jsp">Altera Aluno</a>
	<br/>
	<a href="altera-professor.jsp">Altera professor</a>
	<br/>
	<a href="lista-alunos-elegante.jsp">Listar aluno</a>
	<br/>
	<a href="lista-professores-elegante.jsp">Listar professor</a>
	<br/>
	<a href="remove-aluno.jsp">Remove Aluno</a>
	<br/>
	<a href="remove-professor.jsp">Remove Professor</a>
	<br/>
	<c:import url="rodape.jsp" />
</body>
</html>