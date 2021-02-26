<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
<c:set var="schedule" value="${requestScope.schedule}" scope="session"/>

<h2 class="mt-3">${sessionScope.scheduleInfoTitle}</h2>
<hr class="mb-4"/>

<c:if test="${not empty sessionScope.topMessage}">
    <div class="alert alert-info" role="alert">
            ${sessionScope.topMessage}
    </div>
</c:if>

<table class="table align-content-center table-striped" style="vertical-align: middle">
    <c:if test="${not empty schedule.confirmationCode}">
        <tr>
            <td><b>Número</b></td>
            <td>${schedule.confirmationCode}</td>
        </tr>
    </c:if>

    <tr>
        <td><b>Museu</b></td>
        <td>${schedule.museum.name}</td>
    </tr>
    <tr>
        <td><b>Data</b></td>
        <td>${schedule.date}</td>
    </tr>
    <tr>
        <td><b>Horário</b></td>
        <td>${schedule.hours}</td>
    </tr>
    <tr>
        <td><b>Número de visitantes</b></td>
        <td>${schedule.visitorsCount}</td>
    </tr>
    <tr>
        <td><b>Email do agendador</b></td>
        <td>${schedule.schedulerEmail}</td>
    </tr>
</table>
</body>
</html>
