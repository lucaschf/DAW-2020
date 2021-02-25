<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${sessionScope.user == null}">
    <jsp:forward page="login.jsp" />
</c:if>
</body>
</html>