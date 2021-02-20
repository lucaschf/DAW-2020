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
    <link rel="stylesheet" type="text/css" href="css/fontawesome.min.css"/>
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
        <table class="table align-content-center" style="vertical-align: middle">
            <tr>
                <th></th>
                <th></th>
                <th>Codigo de confirmacao</th>
                <th>Email do agendador</th>
                <th>Data</th>
                <th>Hora</th>
                <th>Museu</th>
                <th>Visitantes</th>
            </tr>
            <c:forEach var="schedule" items="${schedules}">
                <tr>
                    <th>
                        <form action="scheduler" method="post">
                            <button class="btn btn-outline-danger" type="submit">Cancelar</button>
                            <input type="hidden" name="code" value="${schedule.confirmationCode}"/>
                            <input type="hidden" name="logic" value="CancelScheduleLogic"/>
                        </form>
                    </th>
                    <th>
                        <form action="scheduler" method="post">
                            <button class="btn btn-outline-custom" type="submit">Editar</button>
                            <input type="hidden" name="code" value="${schedule.confirmationCode}"/>
                            <input type="hidden" name="logic" value="EditScheduleLogic"/>
                        </form>
                    </th>
                    <td>${schedule.confirmationCode}</td>
                    <td>${schedule.schedulerEmail}</td>
                    <td>${schedule.date.toString()}</td>
                    <td>${schedule.hours.toString()}</td>
                    <td>${schedule.museum.name}</td>
                    <td>${schedule.visitorsCount}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>

<c:import url="footer.jsp"/>
</body>
</html>