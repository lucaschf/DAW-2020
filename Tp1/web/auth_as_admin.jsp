<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${sessionScope.user == null || !sessionScope.user.role.systemAdmin}">
    <jsp:forward page="login.jsp" />
</c:if>
</body>
</html>