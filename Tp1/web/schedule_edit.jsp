<%@ page contentType="text/html;charset=UTF-8" %>
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
<c:set var="scheduleInfoTitle" value="Dados do agendamento" scope="session"/>

<c:if test="${schedule.allVisitorsCheckedIn}">
    <c:set var="topMessage"
           value="Não é possivel realizar alterações em um agendamento onde todos os visitantes confirmaram presença."
           scope="session"/>
</c:if>

<c:import url="header.jsp"/>

<div class="container">

    <%@include file="base_schedule_info.jsp" %>

    <c:if test="${not empty schedule}">
        <div class="pt-3">
            <h3>Vistantes</h3>
            <hr class="mb-4"/>
        </div>

        <c:if test="${schedule.visitorsCount == 1 && !schedule.allVisitorsCheckedIn}">
            <div class="alert alert-info" role="alert">
                Ao remover uma pessoa de um agendamento com apenas um visitante, o mesmo será cancelado.
            </div>
        </c:if>

        <%@include file="message.jsp" %>

        <c:set var="visitors" value="${schedule.visitors}"/>
        <c:if test="${not empty visitors}">
            <table class="table align-content-center table-striped" style="vertical-align: middle">
                <tr>
                    <c:if test="${!schedule.allVisitorsCheckedIn}">
                        <th>Ação</th>
                    </c:if>
                    <th>Cpf</th>
                    <th>Nome</th>
                    <th>Tipo de entrada</th>
                    <th>Compareceu</th>
                </tr>
                <c:forEach var="visitor" items="${visitors}">
                    <tr>
                        <c:if test="${!schedule.allVisitorsCheckedIn}">
                            <td>
                                <c:if test="${!visitor.attended}">
                                    <div class="d-flex">
                                        <form action="scheduler" method="post">
                                            <c:if test="${schedule.visitorsCount > 1}">
                                                <input type="hidden" name="logic" value="RemoveVisitorOnBd"/>
                                            </c:if>
                                            <c:if test="${schedule.visitorsCount == 1}">
                                                <input type="hidden" name="logic" value="CancelSchedule"/>
                                            </c:if>
                                            <input type="hidden" name="cpf" value="${visitor.cpf}"/>
                                            <input type="hidden" name="schedule_id" value="${schedule.id}"/>
                                            <button class="btn btn-outline-danger" type="submit">Remover</button>
                                        </form>
                                        <form style="margin-left: 10px" class="mt-auto mb-auto" action="scheduler"
                                              method="post">
                                            <button class="btn btn-success" type="submit">Checkin</button>
                                            <input type="hidden" name="cpf" value="${visitor.cpf}"/>
                                            <input type="hidden" name="schedule_id" value="${schedule.id}"/>
                                            <input type="hidden" name="logic" value="VisitorCheckin"/>
                                        </form>
                                    </div>
                                </c:if>
                            </td>
                        </c:if>
                        <td>${visitor.cpf}</td>
                        <td>${visitor.name}</td>
                        <td>${visitor.ticketType.description}</td>
                        <td>
                            <c:if test="${visitor.attended}">
                                <label for="iptChkAttended"></label>
                                <input class="form-check-input" type="checkbox" disabled id="iptChkAttended" checked
                                       name="chAttended"/>
                            </c:if>

                            <c:if test="${!visitor.attended}">
                                <label for="iptChkAttended"></label>
                                <input class="form-check-input" type="checkbox" disabled id="iptChkAttended" name="chAttended"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <c:if test="${!schedule.allVisitorsCheckedIn}">
                <div class="d-flex">

                    <form class="row g-3" action="scheduler" method="post">
                        <input type="hidden" name="logic" value="CancelSchedule">
                        <input type="hidden" name="schedule_id" value="${schedule.id}"/>
                        <div class="col-12 pb-3">
                            <button class="btn btn-danger" style="margin-right:10px" type="submit">Cancelar
                                agendamento
                            </button>
                        </div>
                    </form>

                    <form class="row g-3" action="scheduler" method="post">
                        <input type="hidden" name="logic" value="CheckinAll">
                        <input type="hidden" name="schedule_id" value="${schedule.id}"/>
                        <div class="col-12 pb-3">
                            <button class="btn btn-success" type="submit">Confirmar todos</button>
                        </div>
                    </form>
                </div>
            </c:if>
        </c:if>

    </c:if>
</div>

<c:import url="footer.jsp"/>
</body>
</html>