<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

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
</body>
</html>
