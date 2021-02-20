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
<c:import url="header.jsp"/>
<jsp:useBean id="museumDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.MuseumDAO"/>
<jsp:useBean id="ticketBean" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.model.TicketTypeBean"/>
<jsp:useBean id="visitorsDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.VisitorsDao"/>

<div class="container pt-3">
    <h2>Lançamento de agendamentos</h2>
    <hr class="mb-5"/>
</div>

<div class="container">
    <c:set var="incomingSchedule" value="${requestScope.schedule}"/>

    <form class="row g-2" action="scheduler" method="post">
        <input type="hidden" name="logic" value="AddScheduleLogic"/>

        <div class="col-md-3">
            <label for="inputEmail" class="form-label">E-mail</label>
            <input type="text" name="email" class="form-control" id="inputEmail"
                   value="${incomingSchedule.schedulerEmail}" required>
        </div>

        <div class="col-md-3">
            <label for="inputDate" class="form-label">Data</label>
            <input type="text" name="date" class="form-control" id="inputDate" required>
        </div>

        <div class="col-md-3">
            <label for="inputTime" class="form-label">Horário</label>
            <input type="text" name="time" class="form-control" id="inputTime" required>
        </div>

        <div class="col-md-3">
            <label for="inputVisitorsCount" class="form-label">Visitantes</label>
            <input type="number" name="visitors" class="form-control" id="inputVisitorsCount"
                   value="${incomingSchedule.visitorsCount}" required>
        </div>

        <div class="col-md-3">
            <label for="choiceMuseum" class="form-label">Museu</label>
            <select class="form-select" name="museum_id" id="choiceMuseum" required>
                <c:if test="${not empty incomingSchedule}">
                    <option disabled value="">Escolha...</option>
                </c:if>

                <c:if test="${empty incomingSchedule}">
                    <option selected disabled value="">Escolha...</option>
                </c:if>

                <c:forEach var="museum" items="${ museumDao.fetchAll()}">
                    <c:if test="${museum.id == incomingSchedule.museum.id}">
                        <option selected value="${museum.id}">${museum.name}</option>
                    </c:if>

                    <c:if test="${museum.id != incomingSchedule.museum.id}">
                        <option value="${museum.id}">${museum.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <div class="col-12 pb-3">
            <button class="btn btn-outline-custom" type="submit">Agendar</button>
        </div>
    </form>

    <%--    visitors--%>
    <c:if test="${not empty incomingSchedule}">
        <div class="pt-3">
            <h3>Vistantes</h3>
            <hr class="mb-5"/>
        </div>

        <c:if test="${incomingSchedule.visitorsCount > incomingSchedule.visitors.size()}">
            <form class="row g-2" action="scheduler" method="post">
                <input type="hidden" name="logic" value="AddVisitorLogic"/>
                <input type="hidden" name="scheduleCode" value="${incomingSchedule.confirmationCode}"/>

                <div class="col-md-3">
                    <label for="inputVisitorCpf" class="form-label">Cpf</label>
                    <input type="text" name="cpf" class="form-control" id="inputVisitorCpf" required>
                </div>

                <div class="col-md-3">
                    <label for="inputVisitorName" class="form-label">Nome</label>
                    <input type="text" name="visitorName" class="form-control" id="inputVisitorName" required>
                </div>

                <div class="col-md-3">
                    <label for="choiceTicket" class="form-label">Tipo de entrada</label>
                    <select class="form-select" name="ticket" id="choiceTicket" required>
                        <option disabled selected value="">Escolha...</option>
                        <c:forEach items="${ticketBean.values}" var="ticket">
                            <option value="${ticket.code}">${ticket.description}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-12 pb-3">
                    <button class="btn btn-outline-custom" type="submit">Adicionar visitante</button>
                </div>
            </form>
        </c:if>

        <c:set var="visitors" value="${incomingSchedule.visitors}"/>
        <c:if test="${not empty visitors}">
            <table class="table align-content-center" style="vertical-align: middle">
                <tr>
                    <th></th>
                    <th>Cpf</th>
                    <th>Nome</th>
                    <th>Tipo de entrada</th>
                </tr>
                <c:forEach var="visitor" items="${visitors}">
                    <tr>
                        <th>
                            <form action="scheduler" method="post">
                                <button class="btn btn-outline-danger" type="submit">Remover</button>
                                <input type="hidden" name="cpf" value="${visitor.cpf}"/>
                                <input type="hidden" name="scheduleCode" value="${incomingSchedule.confirmationCode}"/>
                                <input type="hidden" name="logic" value="RemoveVisitorLogic"/>
                            </form>
                        </th>
                        <td>${visitor.cpf}</td>
                        <td>${visitor.name}</td>
                        <td>${visitor.ticketType.description}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </c:if>
</div>
<c:import url="footer.jsp"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#inputDate').mask('00/00/0000');
        $('#inputVisitorCpf').mask('000.000.000-00');
        $('#inputTime').mask('00:00');
    });
</script>
</body>
</html>
