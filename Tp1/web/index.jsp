<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="attractionDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.AttractionDao"/>
<jsp:useBean id="formatter" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.util.FormatterUtils"/>
<html>
<head>
    <title>Atrações</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<c:import url="header.jsp"/>

<div class="container pt-3 mb-3">
    <h2>Atrações</h2>
    <hr class="mb-5"/>

    <div class="row mb-5">
        <c:forEach var="attraction" items="${attractionDao.fetchAll()}">
            <div class="col col-md-3">
                <a class="card text-dark text-start text-decoration-none" href="attraction_details.jsp?id=${attraction.id}">
                    <img src="${attraction.coverUrl}" class="card-img-top" alt="">
                    <div class="card-body">
                        <h5 class="card-title">${attraction.title}</h5>
                        <p class="card-text over"
                           style="max-lines: 3;overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
                                ${attraction.details}
                        </p>
                        <span class="fw-bold text-sm-start"
                              style="font-size: smaller">${attraction.museum.name}</span>
                        <hr/>
                        <span class="fw-bold text-sm-start" style="font-size: smaller">
                            ${attraction.beginningExhibition.format(formatter.brazilianDateFormatter)} a
                            ${attraction.endExhibition.format(formatter.brazilianDateFormatter)}
                        </span>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>