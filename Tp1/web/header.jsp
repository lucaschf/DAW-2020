<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Museum Schedule</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
    <script src="script/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <img src="images/logo_.png" alt="" class="logo">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="index.jsp">Home</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="scheduleDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        Agendamentos
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="scheduleDropdown">
                        <li><a class="dropdown-item" href="schedules-list.jsp">Consultar</a></li>
                        <li><a class="dropdown-item" href="schedule_museum_select.jsp">Novo Agendamento</a></li>
                        <li><a class="dropdown-item" href="search_schedule.jsp">Editar</a></li>
                    </ul>
                </li>
                <c:if test="${sessionScope.user != null}">
                    <c:if test="${sessionScope.user.role.is_system_admin}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="employeeDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Funcionários
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="employeeDropdown">
                                <li><a class="dropdown-item" href="employee-list.jsp">Listagem</a></li>
                                <li><a class="dropdown-item" href="employee-registration.jsp">Cadastro</a></li>
                            </ul>
                        </li>
                    </c:if>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="reportDropdown" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            Relatórios
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="reportDropdown">
                            <li><a class="dropdown-item" href="employee-list.jsp">Visitantes por dia</a></li>
                            <li><a class="dropdown-item" href="employee-registration.jsp">Visitantes por data</a></li>
                        </ul>
                    </li>

                    <li class="nav-item me-auto dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="loggedUserDropdown" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="https://mdbootstrap.com/img/Photos/Avatars/img (31).jpg"
                                 class="rounded-circle"
                                 height="22"
                                 alt=""/>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="loggedUserDropdown">
                            <li class="nav-item p-2"> Logado como ${sessionScope.user.username}</li>
                            <li><a class="nav-link" aria-current="page" href="scheduler?logic=Logout">Logout</a></li>
                        </ul>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="login.jsp">Login</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
