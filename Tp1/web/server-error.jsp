<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Museum Schedule</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<section class="error_page">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12 ">
                <div class="col-sm-offset-1  text-center">
                    <div class="error_bg server-error-bg"></div>

                    <div class="content-box-error">
                        <c:set var="message" value="${requestScope.message}"/>
                        <c:set var="source_page" value="${requestScope.source_page}"/>
                        <c:set var="incoming_data" value="${requestScope.incoming_data}"/>
                        <h2> Ooops!</h2>

                        <p>
                            <c:if test="${not empty message}">
                                ${message}
                            </c:if>
                            <c:if test="${ empty message}">
                                Something went wrong...
                            </c:if>
                        </p>

                        <c:if test="${empty source_page}">
                            <a href="index.jsp" class="btn btn-outline-custom">Home</a>
                        </c:if>
                        <c:if test="${not empty source_page}">
                            <a href="${source_page}" class="btn btn-outline-custom">Voltar</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>