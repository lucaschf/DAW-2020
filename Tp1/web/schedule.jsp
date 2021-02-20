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

<div class="container pt-3">
    <h2>Lançamento de agendamentos</h2>
    <hr class="mb-5"/>
</div>


<div class="container">
    <form class="row g-2" action="scheduler" method="post">
        <input type="hidden" name="logic" value="AddScheduleLogic"/>

        <div class="col-md-3">
            <label for="inputEmail" class="form-label">E-mail</label>
            <input type="text" name="email" class="form-control" id="inputEmail" required>
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
            <input type="number" name="visitors" class="form-control" id="inputVisitorsCount" required>
        </div>

        <div class="col-md-3">
            <label for="choiceMuseum" class="form-label">Museu</label>
            <select class="form-select" name="museum_id" id="choiceMuseum" required>
                <option selected disabled value="">Escolha...</option>
                <c:forEach var="museum" items="${ museumDao.fetchAll()}" varStatus="id">
                    <option value="${museum.id}">${museum.name}</option>
                </c:forEach>
            </select>
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
        $('#inputTime').mask('00:00');
    });
</script>
</body>
</html>
