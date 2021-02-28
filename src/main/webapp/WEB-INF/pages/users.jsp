<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ta" uri="tourism-agency-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All users</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="/WEB-INF/fragments/navbar.jspf" %>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <c:if test="${!requestScope.users.isEmpty()}">
                        <h1 class="display-5"><fmt:message key="users.header"/></h1>
                    </c:if>
                    <c:if test="${requestScope.users.isEmpty()}">
                        <h1 class="display-5"><fmt:message key="users.empty"/></h1>
                    </c:if>
                </div>
                <div class="card-body">
                    <c:if test="${!requestScope.users.isEmpty()}">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th><fmt:message key="users.id"/></th>
                            <th><fmt:message key="users.username"/></th>
                            <th><fmt:message key="users.roles"/></th>
                            <th><fmt:message key="users.status"/></th>
                            <th><fmt:message key="users.edit"/></th>
                            <th><fmt:message key="users.delete"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.users}" var="user">
                            <tr>
                                <td>${user.id}</td>
                                <td>
                                    <a href="#userDetailsModal"
                                       data-toggle="modal"
                                       data-target="#userDetailsModal${user.id}">
                                            ${user.username}
                                    </a>
                                </td>
                                <!--Start user modal window-->
                                <div aria-hidden="true" aria-labelledby="userDetailsModalTitle" class="modal fade"
                                     id="userDetailsModal${user.id}" role="dialog" tabindex="-1">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="userDetailsModalTitle">
                                                    <fmt:message key="users.modal.title"/>
                                                </h5>
                                                <button aria-label="Close" class="close" data-dismiss="modal"
                                                        type="button">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="card">
                                                    <div class="card-header">
                                                        <h3>
                                                            <span>${user.username}</span>
                                                        </h3>
                                                    </div>
                                                    <div class="card-body">
                                                        <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="users.profile.full_name"/></span>
                                                            <span>${user.fullName}</span>
                                                        </div>
                                                        <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="users.profile.email"/></span>
                                                            <span>${user.email}</span>
                                                        </div>
                                                        <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="users.profile.bio"/></span>
                                                            <span>${user.bio}</span>
                                                        </div>
                                                        <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="users.profile.orders.discount"/></span>
                                                            <span>${user.discount}</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <div class="btn-group">
                                                    <button class="btn btn-secondary" data-dismiss="modal"
                                                            type="button">
                                                        <fmt:message key="tours.modal.button.close"/>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--End user modal window-->
                                <td class="text-center">
                                    <c:if test="${user.isEnabled()}">
                                        <a class="btn btn-danger btn-block"
                                           href="${pageContext.request.contextPath}/app/users/ban?id=${user.id}">
                                            <fmt:message key="users.status.ban"/></a>
                                    </c:if>
                                    <c:if test="${!user.isEnabled()}">
                                        <a  class="btn btn-success btn-block"
                                            href="${pageContext.request.contextPath}/app/users/unban?id=${user.id}">
                                            <fmt:message key="users.status.unban"/></a>
                                    </c:if>
                                </td>
                                <td>${user.authorities}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/app/users/update?id=${user.id}"
                                       class="btn btn-primary">
                                        <img src="${pageContext.request.contextPath}/images/outline-edit.png"
                                             alt="edit user">
                                    </a>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/app/users/delete?id=${user.id}"
                                       class="btn btn-primary">
                                        <img src="${pageContext.request.contextPath}/images/outline-delete.png"
                                             alt="delete user">
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    </c:if>
                </div>
                <div class="card-footer">
                    <%@include file="/WEB-INF/fragments/users-paginator.jspf" %>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/fragments/footer.jspf" %>

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