<html>
<body>
	<%-- coment�rio em JSP aqui: nossa primeira p�gina JSP --%>
	<%
	String mensagem = "Bem-vindo ao sistema de gest�o acad�mica!";
	%>
	<%
	out.println(mensagem);
	%><br />
	<%
	String desenvolvido = "Desenvolvido por Lucas Cristovam Henriques Fonseca";
	%>
	<%=desenvolvido%><br />
	<%
	System.out.println("Tudo foi executado!");
	%>
</body>