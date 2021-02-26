<%@ page contentType="text/html;charset=UTF-8" %>
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
                        <h2> Ooops!</h2>

                        <p>
                            <c:if test="${not empty message}">
                                ${message}
                            </c:if>
                            <c:if test="${ empty message}">
                                Something went wrong...
                            </c:if>
                        </p>
                        <a href="index.jsp" class="btn btn-outline-custom">Home</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>