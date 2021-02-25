<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="auth.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee registration</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<c:import url="header.jsp"/>
<jsp:useBean id="museumDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.MuseumDAO"/>

<div class="container pt-3">
    <h2>Registro de funcionários</h2>
    <hr class="mb-5"/>
</div>

<div class="container">

    <%@include file="message.jsp"%>

    <form class="row g-3" action="scheduler" method="post">
        <input type="hidden" name="logic" value="AddEmployeeUser"/>

        <div class="col-md-3">
            <label for="inputName" class="form-label">Nome</label>
            <input type="text" name="password"  value="${requestScope.password}" class="form-control" id="inputName" required>
        </div>

        <div class="col-md-3">
            <label for="inputCpf" class="form-label">CPF</label>
            <input type="text" name="username" class="form-control" value="${requestScope.username}" id="inputCpf" required>
        </div>

        <div class="col-md-3">
            <label for="choiceMuseum" class="form-label">Museu</label>
            <select class="form-select" name="museum_id" id="choiceMuseum" required>
                <c:if test="${not empty requestScope.museum_id}">
                    <option disabled value="">Escolha...</option>
                </c:if>

                <c:if test="${empty requestScope.museum_id}">
                    <option selected disabled value="">Escolha...</option>
                </c:if>

                <c:forEach var="museum" items="${ museumDao.fetchAll()}">
                    <c:if test="${museum.id == requestScope.museum_id}">
                        <option selected value="${museum.id}">${museum.name}</option>
                    </c:if>

                    <c:if test="${museum.id != requestScope.museum_id}">
                        <option value="${museum.id}">${museum.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <div class="col-12 pb-3">
            <button class="btn btn-outline-custom" type="submit">Adicionar funcionário</button>
        </div>
    </form>
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