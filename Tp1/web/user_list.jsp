<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="auth_as_admin.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User list</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<c:import url="header.jsp"/>
<jsp:useBean id="userDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.UserDao"/>

<div class="container pt-3">
    <h2>Usuários</h2>
    <hr class="mb-5"/>
</div>

<div class="container">

    <%@include file="message.jsp"%>

    <c:set var="users" value="${userDao.fetchAll()}"/>
    <c:if test="${not empty users}">
        <table class="table table-striped" style="vertical-align: middle">
            <tr>
                <th>Ação</th>
                <th>Usuário</th>
                <th>Cargo</th>
            </tr>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td >
                        <c:if test="${!u.systemAdmin}">
                            <form action="scheduler" class="mt-auto mb-auto" method="post">
                                <input type="hidden" name="logic" value="RemoveUser"/>
                                <input type="hidden" name="username" value="${u.username}"/>
                                <button class="btn btn-danger" type="submit">Remover</button>
                            </form>
                        </c:if>
                    </td>
                    <td>${u.username}</td>
                    <td>${u.role}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty users}">
        <div class="mt-2 mb-3"><h5>Nenhum usuário encontrado...</h5></div>
    </c:if>
</div>

<c:import url="footer.jsp"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#inputCpf').mask('000.000.000-00');
    });
</script>
</body>
</html>