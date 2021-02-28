<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ta" uri="tourism-agency-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="WEB-INF/fragments/navbar.jspf" %>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h2 class="display-5"><fmt:message key="registration.header"/></h2>
        </div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/app/registration" method="post">
                <div class="form-row">
                    <div class="col-md-3 mb-3">
                        <label>
                            <fmt:message key="registration.username.label"/>
                            <input type="text"
                                   name="username"
                                   value="${requestScope.user.username}"
                                   placeholder="<fmt:message key="registration.username.placeholder"/>"
                                   class="form-control"
                                   required
                            />
                            <span class="text-danger">
                            <c:forEach items="${requestScope.errors.usernameErrors}" var="error">
                                ${error}<br>
                            </c:forEach>
                            </span>
                            ${requestScope.usernameUniqueError}
                        </label>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label>
                            <fmt:message key="registration.password.label"/>
                            <input type="password"
                                   name="password"
                                   value="${requestScope.user.password}"
                                   placeholder="<fmt:message key="registration.password.placeholder"/>"
                                   class="form-control"
                                   required
                            />
                            <span class="text-danger">
                            <c:forEach items="${requestScope.errors.passwordErrors}" var="error">
                                ${error}<br>
                            </c:forEach>
                            </span>
                        </label>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-3 mb-3">
                        <label>
                            <fmt:message key="registration.full_name.label"/>
                            <input type="text"
                                   name="fullName"
                                   value="${requestScope.user.fullName}"
                                   placeholder="<fmt:message key="registration.full_name.placeholder"/>"
                                   class="form-control"
                                   required
                            />
                            <span class="text-danger">
                            <c:forEach items="${requestScope.errors.fullNameErrors}" var="error">
                                ${error}<br>
                            </c:forEach>
                        </span>
                        </label>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label>
                            <fmt:message key="registration.email.label"/>
                            <input type="text"
                                   name="email"
                                   value="${requestScope.user.email}"
                                   placeholder="<fmt:message key="registration.email.placeholder"/>"
                                   class="form-control"
                                   required
                            />
                            <span class="text-danger">
                            <c:forEach items="${requestScope.errors.emailErrors}" var="error">
                                ${error}<br>
                            </c:forEach>
                            </span>
                        </label>
                    </div>
                    <div class="form-group col-md-12">
                        <label>
                            <fmt:message key="registration.bio.label"/>
                            <input type="text"
                                   name="bio"
                                   value="${requestScope.user.bio}"
                                   placeholder="<fmt:message key="registration.bio.placeholder"/>"
                                   class="form-control"
                            />
                            <span class="text-danger">
                            <c:forEach items="${requestScope.errors.bioErrors}" var="error">
                                ${error}<br>
                            </c:forEach>
                            </span>
                        </label>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">
                    <fmt:message key="button.sign_up"/>
                </button>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>