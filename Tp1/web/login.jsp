<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">

    <title>Museum Schedule - Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <style type="text/css"> @import url("css/main.css"); </style>
</head>

<style>
    body {
        display: -ms-flexbox;
        display: flex;
        -ms-flex-align: center;
        align-items: center;
        padding-top: 40px;
        padding-bottom: 40px;
    }
</style>
<body>
<div class="container">
    <form class="g-3 col-md-3 m-auto" style="vertical-align: middle" method="post" action="scheduler">
        <input type="hidden" name="logic" value="Login"/>

        <div class="col">
            <img class="mx-auto d-block mb-2" src="images/logo_.png" alt="" width="72px" height="72px">
        </div>

        <h1 class="h3 mb-3 font-weight-normal text-center">Login</h1>

        <div class="col mb-2">
            <label for="inputUsername" class="form-label">Usuário</label>
            <input type="text" name="username" class="form-control" id="inputUsername" required>
        </div>

        <div class="col mb-3">
            <label for="inputPassword" class="form-label">Senha</label>
            <input type="password" name="password" class="form-control" id="inputPassword" required>
        </div>

        <%@include file="message.jsp"%>

        <div class="col">
            <button class="btn btn-lg btn-custom d-block w-100" type="submit">Login</button>
        </div>
    </form>
</div>
</body>
</html>


