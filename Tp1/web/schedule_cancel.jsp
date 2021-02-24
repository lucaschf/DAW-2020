<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agendamento de visitas</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>

<jsp:useBean id="formatterUtils" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.util.FormatterUtils"/>
<c:set var="schedule" value="${requestScope.schedule}" scope="session"/>

<c:import url="header.jsp"/>

<div class="container">
    <h2 class="mt-3">Dados do agendamento</h2>
    <hr class="mb-4"/>

    <table class="table align-content-center table-striped" style="vertical-align: middle">
        <tr>
            <td><b>Código</b></td>
            <td>${schedule.confirmationCode}</td>
        </tr>
        <tr>
            <td><b>Museu</b></td>
            <td>${schedule.museum.name}</td>
        </tr>
        <tr>
            <td><b>Data da visitação</b></td>
            <td>${schedule.date.format(formatterUtils.brazilianDateFormatter)}</td>
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
            <td><b>E-mail do agendador</b></td>
            <td>${schedule.schedulerEmail}</td>
        </tr>
    </table>

    <c:if test="${not empty schedule}">
        <div class="pt-3">
            <h3>Vistantes</h3>
            <hr class="mb-4"/>
        </div>

        <c:if test="${schedule.visitorsCount == 1}">
            <div class="alert alert-info" role="alert">
                Ao remover uma pessoa de um agendamento com apenas um visitante, o mesmo será cancelado.
            </div>
        </c:if>

        <%@include file="message.jsp" %>

        <c:set var="visitors" value="${schedule.visitors}"/>
        <c:if test="${not empty visitors}">
            <table class="table align-content-center table-striped" style="vertical-align: middle">
                <tr>
                    <th>Ação</th>
                    <th>Cpf</th>
                    <th>Nome</th>
                    <th>Tipo de entrada</th>
                </tr>
                <c:forEach var="visitor" items="${visitors}">
                    <tr>
                        <td>
                            <form action="scheduler" method="post">
                                <c:if test="${schedule.visitorsCount > 1}">
                                    <input type="hidden" name="logic" value="RemoveVisitorOnBdLogic"/>
                                </c:if>
                                <c:if test="${schedule.visitorsCount == 1}">
                                    <input type="hidden" name="logic" value="CancelScheduleLogic"/>
                                </c:if>
                                <input type="hidden" name="cpf" value="${visitor.cpf}"/>
                                <input type="hidden" name="schedule_id" value="${schedule.id}"/>
                                <button class="btn btn-outline-danger" type="submit">Remover</button>
                            </form>
                        </td>

                        <td>${visitor.cpf}</td>
                        <td>${visitor.name}</td>
                        <td>${visitor.ticketType.description}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <form class="row g-3" action="scheduler" method="post">
            <input type="hidden" name="logic" value="CancelScheduleLogic">
            <input type="hidden" name="schedule_id" value="${schedule.id}"/>
            <div class="col-12 pb-3">
                <button class="btn btn-danger" type="submit">Cancelar agendamento</button>
            </div>
        </form>
    </c:if>
</div>

<c:import url="footer.jsp"/>
</body>
</html>