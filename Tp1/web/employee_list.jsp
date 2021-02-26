<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="auth_as_admin.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee registration</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<c:import url="header.jsp"/>
<jsp:useBean id="employeDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.EmployeeDao"/>

<div class="container pt-3">
    <h2>Funcionários</h2>
    <hr class="mb-5"/>
</div>

<div class="container">
    <c:set var="employees" value="${employeDao.fetchAll()}"/>
    <c:if test="${not empty employees}">
        <table class="table table-striped" style="vertical-align: middle">
            <tr>
                <th>Ação</th>
                <th>Cpf</th>
                <th>Nome</th>
                <th>Museu</th>
            </tr>
            <c:forEach var="employee" items="${employees}">
                <tr>
                    <td class="d-flex">
                        <form action="scheduler" class="mt-auto mb-auto" method="post">
                            <input type="hidden" name="logic" value="RemoveEmployee"/>
                            <input type="hidden" name="employee_id" value="${employee.id}"/>
                            <button class="btn btn-danger" type="submit">Remover</button>
                        </form>

                        <form style="margin-left: 10px" class="mt-auto mb-auto" action="scheduler" method="post">
                            <input type="hidden" name="logic" value="SearchEmployee"/>
                            <input type="hidden" name="cpf" value="${employee.cpf}"/>
                            <input type="hidden" name="museum_id" value="${employee.museum.id}"/>
                            <input type="hidden" name="employee_id" value="${employee.id}"/>
                            <button class="btn btn-outline-custom" type="submit">Criar usuario</button>
                        </form>
                    </td>
                    <td>${employee.cpf}</td>
                    <td>${employee.name}</td>
                    <td>${employee.museum.name}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty employees}">
        <div class="mt-2 mb-3"><h5>Nenhum funcionario encontrado...</h5></div>
    </c:if>
</div>

<c:import url="footer.jsp"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#inputCpf').mask('000.000.000-00');
    });
</script>
</body>
</html>