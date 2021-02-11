<html>
<body>
	<%-- comentário em JSP aqui: nossa primeira página JSP --%>
	<%
	String mensagem = "Bem-vindo ao sistema de gestão acadêmica!";
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