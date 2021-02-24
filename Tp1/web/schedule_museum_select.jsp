<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agendamento de visitas</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>

<c:import url="header.jsp"/>
<jsp:useBean id="museumDao" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.dao.MuseumDAO"/>
<jsp:useBean id="localDateBean" class="br.edu.ifsudestemg.barbacena.daw.museumschedule.model.LocalDateBean"/>
<c:set var="museums" value="${museumDao.fetchAll()}"/>
<c:set var="message" value="${requestScope.message}"/>
<c:set var="schedule" value="${requestScope.schedule}"/>

<div class="container pt-3">
    <h2>Novo agendamento</h2>
    <hr class="mb-5"/>

    <table class="table align-content-center table-striped" style="vertical-align: middle">
        <tr>
            <th>Museu</th>
            <th>Visitantes simultâneos</th>
            <th>Funcionamento</th>
            <th>Hórario de funcionamento</th>
            <th>Intervalo entre visitas</th>
        </tr>

        <c:forEach var="museum" items="${museums}">
            <tr>
                <th>${museum.name}</th>
                <th>${museum.visitorsLimit}</th>
                <th>${museum.getWorkingDaysDisplayNames()}</th>
                <th>${museum.opensAt} às ${museum.closesAt}</th>
                <th>${museum.minutesBetweenVisits} minutos</th>
            </tr>
        </c:forEach>
    </table>

    <h4 class="mt-5 mb-3">Dados para agendamento</h4>
    <hr class="mb-5"/>

    <c:if test="${not empty message}">
        <div class="alert alert-${message.type.toString().toLowerCase()}" role="alert">
                ${message.message}
        </div>
    </c:if>

    <form class="row g-3" action="scheduler" method="post">
        <input type="hidden" name="logic" value="SelectMuseumLogic"/>

        <div class="col-md-3">
            <label for="choiceMuseum" class="form-label">Museu</label>
            <select class="form-select" name="museum_id" id="choiceMuseum" required>
                <c:if test="${not empty schedule}">
                    <option disabled value="">Escolha...</option>
                </c:if>

                <c:if test="${empty schedule}">
                    <option selected disabled value="">Escolha...</option>
                </c:if>

                <c:forEach var="museum" items="${ museums}">
                    <c:if test="${museum.id == schedule.museum.id}">
                        <option selected value="${museum.id}">${museum.name}</option>
                    </c:if>

                    <c:if test="${museum.id != schedule.museum.id}">
                        <option value="${museum.id}">${museum.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-3">
            <label for="inputEmail" class="form-label">E-mail</label>
            <input type="text" name="email" class="form-control" id="inputEmail" value="${schedule.schedulerEmail}"
                   required>
        </div>

        <div class="col-md-3">
            <label for="inputDate" class="form-label">Data</label>
            <input type="date" name="date" class="form-control" id="inputDate"
                   value="${schedule.date}" min="${localDateBean.now}" required>
        </div>

        <div class="col-md-3">
            <label for="inputTime" class="form-label">Horário</label>
            <input type="time" name="time" class="form-control" id="inputTime" value="${schedule.hours}" required>
        </div>

        <div class="col-md-3">
            <label for="inputVisitorsCount" class="form-label">Visitantes</label>
            <input type="number" name="visitors" class="form-control" id="inputVisitorsCount" min="1"
                   value="${schedule.visitorsCount}" required>
        </div>

        <h4>Termos e condições</h4>
        <div class="terms">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris varius condimentum pellentesque. Nam laoreet
            nulla ex, sed ullamcorper nisi vulputate eu. Integer euismod dolor sapien. Sed dictum condimentum lacus sit
            amet placerat. Vestibulum sapien est, gravida et rutrum nec, tristique ut est. Nam volutpat ex nec nisl
            rutrum, sit amet malesuada leo dictum. In hac habitasse platea dictumst.

            Nam eget purus eget sapien commodo vulputate. Interdum et malesuada fames ac ante ipsum primis in faucibus.
            Interdum et malesuada fames ac ante ipsum primis in faucibus. Nunc feugiat nisi tempus, tristique risus
            sodales, dictum quam. Aenean at varius massa. Cras suscipit ante sed ipsum interdum, ut malesuada erat
            consequat. Donec mattis ligula in dolor posuere suscipit. Sed at molestie diam, eget lacinia dui. Quisque
            consequat, tortor in feugiat blandit, arcu eros tincidunt leo, at finibus lorem libero convallis massa.
            Aliquam felis neque, pretium non semper vel, placerat vel elit. In eget pellentesque arcu. Pellentesque
            scelerisque porttitor venenatis.

            Fusce sit amet dictum justo. In volutpat, leo at ullamcorper egestas, sapien felis porta nulla, eu sodales
            purus odio sit amet erat. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent a vehicula
            ligula. In id neque ac mauris porttitor fermentum tincidunt quis nibh. Sed eu vulputate libero. Morbi
            faucibus quis magna sed posuere. Integer commodo augue non magna eleifend consequat. Proin fringilla arcu a
            mollis luctus. Donec quis lobortis mauris, nec molestie urna.

            Cras turpis libero, hendrerit ut suscipit non, laoreet a leo. Aenean non dapibus metus. Aenean et libero
            elementum dui consectetur ultricies. Maecenas condimentum risus vel leo imperdiet, hendrerit venenatis ex
            hendrerit. Pellentesque bibendum turpis sed euismod scelerisque. Nunc ut lectus vitae dolor congue fringilla
            id et purus. Suspendisse sed metus laoreet, pellentesque purus a, facilisis libero. Mauris sit amet lorem
            non erat hendrerit viverra. Maecenas eget lacinia nisl. Suspendisse quis enim rhoncus, tincidunt magna et,
            ornare ex. Mauris a nibh dignissim, laoreet nulla non, tristique augue.

            Aenean volutpat tristique velit, at interdum erat gravida sit amet. In quam lorem, hendrerit at massa ac,
            commodo vulputate enim. Morbi pretium justo metus. Quisque dictum, nulla nec tristique tincidunt, mi nunc
            semper purus, quis tempus diam orci sit amet ante. Morbi at ex consequat, feugiat felis vitae, aliquet
            tellus. Nulla ultricies, ipsum ut pulvinar blandit, justo tellus sodales orci, non scelerisque dui enim a
            metus. Vestibulum dignissim, metus eu luctus finibus, mi libero convallis odio, eu pulvinar risus massa in
            elit. Integer euismod purus a nulla faucibus dapibus. Ut facilisis luctus viverra. Cras convallis ornare
            nulla, eget accumsan ante. Duis vitae augue sit amet lorem tempor placerat. Duis fermentum risus vel neque
            convallis vulputate.
        </div>

        <div class="col-md-3">
            <label for="inputAcceptTerms" class="form-label">Aceito os termos e condicoes </label>
            <c:if test="${schedule.termsAcceptanceDate == null}">
                <input class="form-check-input" type="checkbox" name="termsAccepted" id="inputAcceptTerms" required/>
            </c:if>
            <c:if test="${schedule.termsAcceptanceDate != null}">
                <input class="form-check-input" type="checkbox" name="termsAccepted" checked id="inputAcceptTerms"
                       required/>
            </c:if>

        </div>

        <div class="col-12 pb-3">
            <button class="btn btn-outline-custom" type="submit">Agendar</button>
        </div>
    </form>
</div>

<c:import url="footer.jsp"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#inputVisitorCpf').mask('000.000.000-00');
        // $('#inputTime').mask('00:00');
    });
</script>
</body>
</html>
