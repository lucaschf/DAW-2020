<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <style type="text/css"> @import url("css/main.css"); </style>
    <title></title>
</head>
<body>
<c:set var="message" value="${requestScope.message}" scope="request"/>
<c:if test="${not empty message}">
    <div class="alert alert-${message.type.toString().toLowerCase()}" role="alert">
            ${message.message}
    </div>
</c:if>
</body>
</html>
