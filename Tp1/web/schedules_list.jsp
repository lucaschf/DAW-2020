<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="auth.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agendamentos</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
    <link rel="stylesheet" type="text/css" href="css/fontawesome.min.css"/>
</head>
<body>
<c:import url="header.jsp"/>
<jsp:useBean id="scheculeDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleDao"/>
<jsp:useBean id="formaterUtil" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.util.FormatterUtils"/>

<div class="container pt-3">
    <h2>Agendamentos</h2>
    <hr class="mb-5"/>
</div>

<div class="container">
    <c:if test="${sessionScope.user.systemAdmin}">
        <c:set var="schedules" value="${scheculeDao.fetchAll()}"/>
    </c:if>

    <c:if test="${!sessionScope.user.systemAdmin}">
        <c:set var="schedules" value="${scheculeDao.fetchAllByMuseum(sessionScope.user.employee.museum.id)}"/>
    </c:if>

    <c:if test="${not empty schedules}">
        <table class="table align-content-center table-striped" style="vertical-align: middle">
            <tr>
                <th>Ação</th>
                <th>Codigo de confirmacao</th>
                <th>Email do agendador</th>
                <th>Data</th>
                <th>Horário</th>
                <th>Data de aceite aos termos</th>
                <th>Museu</th>
                <th>Visitantes</th>
            </tr>
            <c:forEach var="schedule" items="${schedules}">
                <tr>
                    <td>
                        <div class="d-flex">
                            <form style="margin-left: 10px" class="mt-auto mb-auto" action="scheduler" method="post">
                                <c:if test="${!schedule.allVisitorsCheckedIn}">
                                    <button class="btn btn-outline-custom" type="submit">Editar</button>
                                </c:if>
                                <c:if test="${schedule.allVisitorsCheckedIn}">
                                    <button class="btn btn-outline-primary" type="submit">Visualizar</button>
                                </c:if>
                                <input type="hidden" name="email" value="${schedule.schedulerEmail}"/>
                                <input type="hidden" name="confirmationCode" value="${schedule.confirmationCode}"/>
                                <input type="hidden" name="logic" value="SearchSchedule"/>
                            </form>
                        </div>
                    </td>
                    <td>${schedule.confirmationCode}</td>
                    <td>${schedule.schedulerEmail}</td>
                    <td>${schedule.date.format(formaterUtil.brazilianDateFormatter)}</td>
                    <td>${schedule.hours.toString()}</td>
                    <td>${schedule.termsAcceptanceDate.format(formaterUtil.brazilianDateTimeFormatter)}</td>
                    <td>${schedule.museum.name}</td>
                    <td>${schedule.visitorsCount}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty schedules}">
        <div class="mt-2 mb-3"><h5>Nenhum agendamento encontrado...</h5></div>
    </c:if>
</div>

<c:import url="footer.jsp"/>
</body>
</html>