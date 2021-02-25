<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="auth_as_admin.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User registration</title>
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

<div class="container">

    <c:if test="${empty requestScope.message}">
        <div class="alert alert-info" role="alert">
            Informe os dados do funcionário para qual deseja criar um usuário.
        </div>
    </c:if>

    <%@include file="message.jsp"%>

    <form class="row g-3" action="scheduler" method="post">
        <input type="hidden" name="logic" value="SearchEmployee"/>

        <div class="col-md-3">
            <label for="inputCpf" class="form-label">CPF</label>
            <input type="text" name="cpf" class="form-control" value="${requestScope.cpf}" id="inputCpf" required>
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
            <button class="btn btn-outline-custom" type="submit">Recuperar</button>
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