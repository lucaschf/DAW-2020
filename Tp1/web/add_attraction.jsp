<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="museumDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.MuseumDAO"/>
<c:set var="museums" value="${museumDao.fetchAll()}"/>
<html>
<head>
    <title>Add attraction</title>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<c:import url="header.jsp"/>

<div class="container">
    <h2 class="mt-5 mb-3">Dados da atração</h2>
    <hr class="mb-5"/>

    <%@include file="message.jsp" %>

    <form class="row g-3" action="scheduler" method="post">
        <input type="hidden" name="logic" value="AddAttraction"/>

        <c:if test="${sessionScope.user.systemAdmin}">
            <div class="col-md-3">
                <label for="choiceMuseum" class="form-label">Museu</label>
                <select class="form-select" name="museum_id" id="choiceMuseum" required>
                    <option disabled value="">Escolha...</option>

                    <c:forEach var="museum" items="${ museums}">
                        <option value="${museum.id}">${museum.name}</option>
                    </c:forEach>
                </select>
            </div>
        </c:if>

        <div class="col-md-3">
            <label for="inputTitle" class="form-label">Título</label>
            <input type="text" name="title" class="form-control" id="inputTitle" value="${requestScope.title}" required>
        </div>

        <div class="col-md-3">
            <label for="inputCoverUrl" class="form-label">Url da capa</label>
            <input type="text" name="coverUrl" class="form-control" id="inputCoverUrl" value="${requestScope.coverUrl}"
                   required>
        </div>

        <div class="col-md-3">
            <label for="inputBegin" class="form-label">Início da exibição</label>
            <input type="date" name="begin" class="form-control" id="inputBegin" value="${requestScope.begin}" required>
        </div>

        <div class="col-md-3">
            <label for="inputEnd" class="form-label">Fim da exibição</label>
            <input type="date" name="end" class="form-control" id="inputEnd" value="${requestScope.end}" required>
        </div>

        <div class="col-12">
            <label for="inputDetails" class="form-label">Detalhes</label>
            <textarea type="text" name="details" class="form-control" rows="8" id="inputDetails" required>
                ${requestScope.details}
            </textarea>
        </div>

        <div class="col-12 pb-3">
            <button class="btn btn-outline-custom" type="submit">Adicionar</button>
        </div>
    </form>
</div>

<c:import url="footer.jsp"/>
</body>
</html>
