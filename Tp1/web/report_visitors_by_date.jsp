<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="auth.jsp" %>

<jsp:useBean id="reportDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleReportDao"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Visitantes que compareceram por data</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>

<c:import url="header.jsp"/>

<div class="container">
    <h2 class="mt-5 mb-3">Relatório de visitantes por dia</h2>
    <hr class="mb-5"/>

    <c:if test="${sessionScope.user.systemAdmin}">
        <div class="alert alert-info" role="alert">
            Ao clicar em gerar relatório, sera gerado o relatório de todos os museus registrados.
        </div>
    </c:if>

    <form class="row g-3" action="scheduler" method="post">
        <input type="hidden" name="logic" value="ReportOffAttendedVisitors"/>

        <div class="col-md-3">
            <label for="inputDate" class="form-label">Data</label>
            <input type="date" name="date" class="form-control" id="inputDate" value="${requestScope.date}" required>
        </div>

        <div class="col-12 pb-3">
            <button class="btn btn-outline-custom" type="submit">Gerar relatório</button>
        </div>
    </form>

    <c:if test="${not empty requestScope.date}">
        <c:set var="entries"
               value="${reportDao.reportOfAttendedVisitorsByMuseumInDay(requestScope.date, requestScope.museum_id)}"/>

        <%@include file="visitors_report_entries_table.jsp" %>
    </c:if>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
