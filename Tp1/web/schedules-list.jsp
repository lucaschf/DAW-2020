<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
<jsp:useBean id="scheculeDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao"/>

<div class="container pt-3">
    <h2>Agendamentos</h2>
    <hr class="mb-5"/>
</div>

<div class="container">
    <c:set var="schedules" value="${scheculeDao.fetchAll()}"/>
    <c:if test="${not empty schedules}">
        <table class="table">
            <tr>
                <th>Codigo de confirmacao</th>
                <th>Email do agendador</th>
                <th>Data</th>
                <th>Hora</th>
                <th>Museu</th>
                <th>Visitantes</th>
            </tr>
            <c:forEach var="schecule" items="${schedules}">
                <tr>
                    <td>${schecule.confirmationCode}</td>
                    <td>${schecule.schedulerEmail}</td>
                    <td>${schecule.date.toString()}</td>
                    <td>${schecule.hours.toString()}</td>
                    <td>${schecule.museum.name}</td>
                    <td>${schecule.visitorsCount}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>

<c:import url="footer.jsp"/>
</body>
</html>