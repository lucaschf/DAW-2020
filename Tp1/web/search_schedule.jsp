<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cancelar agendamento</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<c:import url="header.jsp"/>

<div class="container pt-3">
    <h2>Buscar agendamento</h2>
    <hr class="mb-5"/>

    <%@include file="message.jsp" %>

    <form class="row g-3" action="scheduler" method="post">
        <input type="hidden" name="logic" value="SearchScheduleForEdit"/>

        <div class="col-md-3">
            <label for="inputConfirmationCode" class="form-label">Código de confirmação</label>
            <input type="text" name="confirmationCode" class="form-control" id="inputConfirmationCode"
                   value="${requestScope.confirmationCode}" required>
        </div>

        <div class="col-md-3">
            <label for="inputEmail" class="form-label">Email</label>
            <input type="text" name="email" class="form-control" id="inputEmail" value="${requestScope.email}" required>
        </div>

        <div class="col-12 pb-3">
            <button class="btn btn-outline-custom" type="submit">Recuperar</button>
        </div>
    </form>
</div>

<c:import url="footer.jsp"/>

</body>
</html>