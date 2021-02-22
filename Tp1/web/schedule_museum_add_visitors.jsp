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

<c:set var="schedule" value="${requestScope.schedule}" scope="session"/>
<jsp:useBean id="ticketBean" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.model.TicketTypeBean"/>
<c:set var="errorMessage" value="${requestScope.errorMessage}"/>

<c:import url="header.jsp"/>

<div class="container">
    <h2 class="mt-3">Novo agendamento</h2>
    <hr class="mb-4"/>

    <table class="table align-content-center table-striped" style="vertical-align: middle">
        <tr>
            <td><b>Museu</b></td>
            <td>${schedule.museum.name}</td>
        </tr>
        <tr>
            <td><b>Data da visitacao</b></td>
            <td>${schedule.date}</td>
        </tr>
        <tr>
            <td><b>Horario</b></td>
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

    <c:if test="${not empty schedule}">

        <div class="pt-3">
            <h3>Vistantes</h3>
            <hr class="mb-4"/>
        </div>

        <c:if test="${schedule.visitorsCount > schedule.visitors.size()}">
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">
                        ${errorMessage.message}
                </div>
            </c:if>

            <div class="alert alert-info" role="alert">
                Informe os dados dos ${schedule.visitorsCount} visitantes para confirmar o agendamento.
            </div>

            <form class="row g-3" action="scheduler" method="post">
                <input type="hidden" name="logic" value="AddVisitorLogic"/>

                <input type="hidden" name="schedule" value="${schedule}"/>

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
                        <th>
                            <form action="scheduler" method="post">
                                <input type="hidden" name="logic" value="RemoveVisitorLogic"/>
                                <input type="hidden" name="cpf" value="${visitor.cpf}"/>
                                <button class="btn btn-outline-danger" type="submit">Remover</button>
                            </form>
                        </th>
                        <td>${visitor.cpf}</td>
                        <td>${visitor.name}</td>
                        <td>${visitor.ticketType.description}</td>
                    </tr>
                </c:forEach>
            </table>

            <c:if test="${visitors.size() == schedule.visitorsCount}">
                <form class="row g-3" action="scheduler" method="post">
                    <input type="hidden" name="logic" value="ConfirmScheduleLogic">
                    <div class="col-12 pb-3">
                        <button class="btn btn-custom" type="submit">Confirmar agendamento</button>
                    </div>
                </form>
            </c:if>
        </c:if>
    </c:if>
</div>

<c:import url="footer.jsp"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#inputVisitorCpf').mask('000.000.000-00');
    });
</script>
</body>
</html>
