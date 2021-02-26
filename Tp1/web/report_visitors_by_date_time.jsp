<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="auth.jsp" %>

<jsp:useBean id="reportDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.ScheduleReportDao"/>

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

<div class="container">
    <h2 class="mt-5 mb-3">Relatório de visitantes por dia/hora</h2>
    <hr class="mb-5"/>

    <c:if test="${sessionScope.user.systemAdmin}">
        <div class="alert alert-info" role="alert">
                Ao clicar em gerar relatório, sera gerado o relatório de todos os museus registrados.
        </div>
    </c:if>

    <form class="row g-3" action="scheduler" method="post">
        <input type="hidden" name="logic" value="ReportByDateTime"/>

        <div class="col-md-3">
            <label for="inputDate" class="form-label">Data</label>
            <input type="date" name="date" class="form-control" id="inputDate" value="${requestScope.date}" required>
        </div>

        <div class="col-md-3">
            <label for="inputTime" class="form-label">Horário</label>
            <input type="time" name="time" class="form-control" id="inputTime" value="${requestScope.time}" required>
        </div>

        <div class="col-12 pb-3">
            <button class="btn btn-outline-custom" type="submit">Gerar relatório</button>
        </div>
    </form>

    <c:if test="${not empty requestScope.date}">
        <c:set var="entries"
               value="${reportDao.generateReportByMuseumInDayAndTime(requestScope.date, requestScope.time, requestScope.museum_id)}"/>

        <div class="pt-3">
            <h3>Vistantes (${entries.size()})</h3>
            <hr class="mb-4"/>
        </div>


        <c:if test="${not empty entries}">

            <table class="table table-striped" style="vertical-align: middle">
                <tr>
                    <th>Agendamento</th>
                    <th>Museu</th>
                    <th>Email do agendador</th>
                    <th>Cpf do visitante</th>
                    <th>Visitante</th>
                    <th>Presença confirmada</th>
                </tr>
                <c:forEach var="entry" items="${entries}">
                    <tr>
                        <td>${entry.confirmationCode}</td>
                        <td>${entry.museumName}</td>
                        <td>${entry.schedulerEmail}</td>
                        <td>${entry.visitor.cpf.numeroFormatado}</td>
                        <td>${entry.visitor.name}</td>
                        <td>
                            <c:if test="${entry.visitor.attended}">
                                <label for="iptChkAttended"></label>
                                <input class="form-check-input" type="checkbox" disabled id="iptChkAttended" checked
                                       name="chAttended"/>
                            </c:if>

                            <c:if test="${!entry.visitor.attended}">
                                <label for="iptChkAttended"></label>
                                <input class="form-check-input" type="checkbox" disabled id="iptChkAttended"
                                       name="chAttended"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty entries}">
            <div class="mt-2 mb-3"><h5>Nenhuma entrada encontrada...</h5></div>
        </c:if>
    </c:if>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
