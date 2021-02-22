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
<jsp:useBean id="formatterUtils" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.util.FormatterUtils"/>
<c:set var="museums" value="${museumDao.fetchAll()}"/>
<c:set var="errorMessage" value="${requestScope.errorMessage}"/>
<c:set var="schedule" value="${requestScope.schedule}"/>

<div class="container pt-3">
    <h2>Novo agendamento</h2>
    <hr class="mb-5"/>

    <table class="table align-content-center table-striped" style="vertical-align: middle">
        <tr>
            <th>Museu</th>
            <th>Visitantes simultâneos</th>
            <th>Funcionamento</th>
            <th>Hórario de funcionamento</th>
            <th>Intervalo entre visitas</th>
        </tr>

        <c:forEach var="museum" items="${museums}">
            <tr>
                <th>${museum.name}</th>
                <th>${museum.visitorsLimit}</th>
                <th>${museum.getWorkingDaysDisplayNames()}</th>
                <th>${museum.opensAt} às ${museum.closesAt}</th>
                <th>${museum.minutesBetweenVisits} minutos</th>
            </tr>
        </c:forEach>
    </table>

    <h4 class="mt-5 mb-3">Dados para agendamento</h4>
    <hr class="mb-5"/>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
                ${errorMessage.message}
        </div>
    </c:if>

    <form class="row g-3" action="scheduler" method="post">
        <input type="hidden" name="logic" value="SelectMuseumLogic"/>

        <div class="col-md-3">
            <label for="choiceMuseum" class="form-label">Museu</label>
            <select class="form-select" name="museum_id" id="choiceMuseum" required>
                <c:if test="${not empty schedule}">
                    <option disabled value="">Escolha...</option>
                </c:if>

                <c:if test="${empty schedule}">
                    <option selected disabled value="">Escolha...</option>
                </c:if>

                <c:forEach var="museum" items="${ museums}">
                    <c:if test="${museum.id == schedule.museum.id}">
                        <option selected value="${museum.id}">${museum.name}</option>
                    </c:if>

                    <c:if test="${museum.id != schedule.museum.id}">
                        <option value="${museum.id}">${museum.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-3">
            <label for="inputEmail" class="form-label">E-mail</label>
            <input type="text" name="email" class="form-control" id="inputEmail" value="${schedule.schedulerEmail}"
                   required>
        </div>

        <div class="col-md-3">
            <label for="inputDate" class="form-label">Data</label>
            <input type="text" name="date" class="form-control" id="inputDate"
                   value="${schedule.date.format(formatterUtils.brazilianDateFormatter)}" required>
        </div>

        <div class="col-md-3">
            <label for="inputTime" class="form-label">Horário</label>
            <input type="text" name="time" class="form-control" id="inputTime" value="${schedule.hours}" required>
        </div>

        <div class="col-md-3">
            <label for="inputVisitorsCount" class="form-label">Visitantes</label>
            <input type="number" name="visitors" class="form-control" id="inputVisitorsCount" min="1"
                   value="${schedule.visitorsCount}" required>
        </div>

        <div class="col-12 pb-3">
            <button class="btn btn-outline-custom" type="submit">Agendar</button>
        </div>
    </form>
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
