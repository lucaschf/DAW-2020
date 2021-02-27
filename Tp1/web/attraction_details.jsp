<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="attractionDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.AttractionDao"/>
<jsp:useBean id="formatter" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.util.FormatterUtils"/>
<html>
<head>
    <title>Museum Schedule</title>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body style="font-family: 'proxima-nova', sans-serif">
<c:import url="header.jsp"/>
<c:set var="attraction" value="${attractionDao.fetchById(param.id)}"/>

<div class="container pt-3 mb-3">
    <h2 class="mb-3">${attraction.title}</h2>

    <span class="fw-bold mb-2">
        Em exibição de ${attraction.beginningExhibition.format(formatter.brazilianDateFormatter)}
        a
        ${attraction.endExhibition.format(formatter.brazilianDateFormatter)} no
        ${attraction.museum.name}
    </span>

    <div class="row mt-3">
        <div class="col-md-8">
            ${attraction.details}
        </div>

        <div class="col-md-4 mt-2">
            <img class="w-100" src="${attraction.coverUrl}">
        </div>
    </div>
</div>

</body>
</html>
