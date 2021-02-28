<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ta" uri="tourism-agency-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="/WEB-INF/fragments/navbar.jspf" %>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h2 class="display-5">
                <span><fmt:message key="users.profile.welcome"/></span>
                <span>${requestScope.user.username}</span>
            </h2>
        </div>
        <div class="card-body">
            <div class="card-text">
                <span class="text-info"><fmt:message key="users.profile.username"/></span>
                <span>${requestScope.user.username}</span>
            </div>
            <div class="card-text">
                <span class="text-info"><fmt:message key="users.profile.full_name"/></span>
                <span>${requestScope.user.fullName}</span>
            </div>
            <div class="card-text">
                <span class="text-info"><fmt:message key="users.profile.email"/></span>
                <span>${requestScope.user.email}</span>
            </div>
            <div class="card-text">
                <span class="text-info"><fmt:message key="users.profile.bio"/></span>
                <span>${requestScope.user.bio}</span>
            </div>
        </div>
    </div>
    <div class="card">
        <div class="card-header">
            <c:if test="${!requestScope.user.orders.isEmpty()}">
                <h4 class="display-5"><fmt:message key="users.profile.orders.header"/></h4>
            </c:if>
            <c:if test="${requestScope.user.orders.isEmpty()}">
                <h4 class="display-5"><fmt:message key="users.profile.orders.empty"/></h4>
            </c:if>
        </div>
        <c:if test="${!requestScope.user.orders.isEmpty()}">
        <div class="card-body">
            <div class="card-text">
                <span class="text-info"><fmt:message key="users.profile.orders.price_total"/></span>
                <span>${requestScope.user.getTotalOrdersPrice()}</span>
            </div>
            <div class="card-text">
                <span class="text-info"><fmt:message key="users.profile.orders.discount_current"/></span>
                <span>${requestScope.user.discount}</span>
            </div>
            <hr>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><fmt:message key="users.profile.orders.id"/></th>
                    <th><fmt:message key="users.profile.orders.tour"/></th>
                    <th><fmt:message key="users.profile.orders.price"/></th>
                    <th><fmt:message key="users.profile.orders.discount"/></th>
                    <th><fmt:message key="users.profile.orders.price_fixed"/></th>
                    <th class="text-center"><fmt:message key="users.profile.orders.status"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.user.orders}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td>
                            <a href="#tourDetailsModal"
                               data-toggle="modal"
                               data-target="#tourDetailsModal${order.tour.id}">
                                    ${order.tour.name}
                            </a>
                        </td>
                        <!--Start modal window-->
                        <div aria-hidden="true" aria-labelledby="tourDetailsModalTitle" class="modal fade"
                             id="tourDetailsModal${order.tour.id}" role="dialog" tabindex="-1">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="tourDetailsModalTitle">
                                            <fmt:message key="tours.modal.title"/>
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
                                                    <span>${order.tour.name}</span>
                                                </h3>
                                            </div>
                                            <div class="card-body">
                                                <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="tours.modal.type"/></span>
                                                    <span>${order.tour.type.toString()}</span>
                                                </div>
                                                <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="tours.modal.price"/></span>
                                                    <span>${order.tour.price}</span>
                                                </div>
                                                <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="tours.modal.group_size"/></span>
                                                    <span>${order.tour.groupSize}</span>
                                                </div>
                                                <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="tours.modal.hotel"/></span>
                                                    <span>${order.tour.hotel.toString()}</span>
                                                </div>
                                                <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="tours.modal.description"/></span>
                                                    <span>${order.tour.description}</span>
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
                        <!--End modal window-->
                        <td>${order.price}</td>
                        <td>${order.discount}</td>
                        <td>${order.fixedPrice}</td>
                        <td class="text-center">
                            <c:if test="${order.pending}">
                            <button class="btn btn-info active btn-block">
                                <fmt:message key="users.profile.orders.status.pending"/>
                            </button>
                            </c:if>
                            <c:if test="${order.paid}">
                            <button class="btn btn-success active btn-block">
                                <fmt:message key="users.profile.orders.status.paid"/>
                            </button>
                            </c:if>
                            <c:if test="${order.denied}">
                            <button class="btn btn-danger active btn-block">
                                <fmt:message key="users.profile.orders.status.denied"/>
                            </button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        </c:if>
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