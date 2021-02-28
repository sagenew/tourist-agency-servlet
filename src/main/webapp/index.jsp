<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ta" uri="tourism-agency-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Tourist Agency Inc.</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="/WEB-INF/fragments/navbar.jspf" %>

<main role="main">
    <div class="jumbotron">
        <div class="container">
            <h1 class="display-2">
                <fmt:message key="index.main.name"/>
            </h1>
            <p class="lead">
                <fmt:message key="index.main.desc"/>
            </p>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <h3>
                    <fmt:message key="index.secondary.column1.name"/>
                </h3>
                <p class="text-muted">
                    <fmt:message key="index.secondary.column1.desc"/>
                </p>
            </div>
            <div class="col-md-4">
                <h3>
                    <fmt:message key="index.secondary.column2.name"/>
                </h3>
                <p class="text-muted">
                    <fmt:message key="index.secondary.column2.desc"/>
                </p>
            </div>
            <div class="col-md-4">
                <h3>
                    <fmt:message key="index.secondary.column3.name"/>
                </h3>
                <p class="text-muted">
                    <fmt:message key="index.secondary.column3.desc"/>
                </p>
            </div>
        </div>
        <hr>
    </div>
</main>

<%@include file="/WEB-INF/fragments/footer.jspf" %>

</body>
</html>