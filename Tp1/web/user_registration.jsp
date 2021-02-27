<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="auth_as_admin.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de usuário</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<c:import url="header.jsp"/>
<jsp:useBean id="museumDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.MuseumDAO"/>

<div class="container pt-3">
    <h2>Registro de usuário</h2>
    <hr class="mb-5"/>
</div>

<c:set var="employee" value="${requestScope.employee}" scope="session"/>

<div class="container">
    <table class="table align-content-center table-striped" style="vertical-align: middle">
        <tr>
            <td><b>Cpf</b></td>
            <td>${employee.cpf}</td>
        </tr>
        <tr>
            <td><b>Nome do funcionário</b></td>
            <td>${employee.name}</td>
        </tr>
        <tr>
            <td><b>Museu</b></td>
            <td>${employee.museum.name}</td>
        </tr>
    </table>

    <div class="pt-3">
        <h3>Dados do usuario</h3>
        <hr class="mb-4"/>
    </div>

    <%@include file="message.jsp" %>

    <form class="row g-3" action="scheduler" method="post">
        <input type="hidden" name="logic" value="RegisterUserForEmployee"/>

        <div class="col-md-3">
            <label for="inputUserName" class="form-label">Usuário</label>
            <input type="text" name="username" value="${requestScope.username}" class="form-control" id="inputUserName" required>
        </div>

        <div class="col-md-3">
            <label for="inputPassword" class="form-label">Senha</label>
            <input type="password" name="password" class="form-control"  id="inputPassword" required>
        </div>

        <div class="col-md-3">
            <label for="inputConfirmationPassword" class="form-label">Confirme a senha</label>
            <input type="password" name="confirmation_password" class="form-control"  id="inputConfirmationPassword" required>
        </div>

        <div class="col-12 pb-3">
            <button class="btn btn-outline-custom" type="submit">Adicionar usuário</button>
        </div>
    </form>
</div>

<c:import url="footer.jsp"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>

</body>
</html>