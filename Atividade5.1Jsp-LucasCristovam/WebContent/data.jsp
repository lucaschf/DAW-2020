<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Data atual</title>
</head>
<body>
	<% LocalDate date = LocalDate.now(); %>
	
	<%= date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>
</body>
</html>