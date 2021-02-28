<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ta" uri="tourism-agency-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All orders</title>
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
                    <c:if test="${!requestScope.orders.isEmpty()}">
                        <h1 class="display-5"><fmt:message key="orders.header"/></h1>
                    </c:if>
                    <c:if test="${requestScope.orders.isEmpty()}">
                        <h1 class="display-5"><fmt:message key="orders.empty"/></h1>
                    </c:if>
                </div>
                <div class="card-body">
                    <form class="form-inline"
                            action="${pageContext.request.contextPath}/app/tours/orders/set-discount" method="post">
                        <div class="form-group mb-1">
                            <label for="step"><fmt:message key="orders.discount.step"/></label>
                            <input type="text" id="step" name="step" value="${requestScope.discount.step}"
                                   class="form-control mx-2"
                                   required/>
                            <span class="text-danger">
                            <c:forEach items="${requestScope.errors.stepErrors}" var="error">
                                ${error}<br>
                            </c:forEach>
                            </span>
                        </div>
                        <div class="form-group mx-sm-3 mb-1">
                            <label for="threshold">
                                <fmt:message key="orders.discount.threshold"/></label>
                            <input type="text" id="threshold" name="threshold"
                                   value="${requestScope.discount.threshold}"
                                   class="form-control mx-2"
                                   required/>
                            <span class="text-danger">
                            <c:forEach items="${requestScope.errors.thresholdErrors}" var="error">
                                ${error}<br>
                            </c:forEach>
                            </span>
                        </div>
                        <input class="btn btn-primary mb-1" value="<fmt:message key="orders.discount.button.submit"/>"
                               type="submit">
                    </form>
                    <c:if test="${!requestScope.orders.isEmpty()}">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>
                                    <a href="${pageContext.request.contextPath}/app/tours/orders?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'orders.id'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                                        <fmt:message key="orders.id"/>
                                    </a>
                                </th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/app/tours/orders?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'tours.name'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                                        <fmt:message key="orders.tour"/>
                                    </a>
                                </th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/app/tours/orders?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'users.username'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                                        <fmt:message key="orders.user"/>
                                    </a>
                                </th>
                                <th>
                                    <fmt:message key="orders.price"/>
                                </th>
                                <th>
                                    <fmt:message key="orders.discount"/>
                                </th>
                                <th>
                                    <fmt:message key="orders.price_fixed"/>
                                </th>
                                <th class="text-center">
                                    <a href="${pageContext.request.contextPath}/app/tours/orders?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'orders.status'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                                        <fmt:message key="orders.status"/>
                                    </a>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.orders}" var="order">
                                <tr>
                                    <td style="width: 15%">${order.id}</td>
                                    <td style="width: 25%">
                                        <a href="#tourDetailsModal"
                                           data-toggle="modal"
                                           data-target="#tourDetailsModal${order.tour.id}">
                                                ${order.tour.name}
                                        </a>
                                    </td>
                                    <!--Start tour modal window-->
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
                                                                <span><fmt:formatNumber type="number"
                                                                                        maxFractionDigits="2"
                                                                                        value="${order.tour.price}"/></span>
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
                                                        <ta:hasAuthority authority="MANAGER">
                                                            <a href="${pageContext.request.contextPath}/app/tours/orders/delete?id=${order.id}&page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}"
                                                               class="btn btn-danger">
                                                                <fmt:message
                                                                        key="orders.tour.modal.status.button.delete_order"/>
                                                            </a>
                                                        </ta:hasAuthority>
                                                        <button class="btn btn-secondary" data-dismiss="modal"
                                                                type="button">
                                                            <fmt:message key="tours.modal.button.close"/>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--End tour modal window-->
                                    <td style="width: 25%">
                                        <a href="#userDetailsModal"
                                           data-toggle="modal"
                                           data-target="#userDetailsModal${order.user.id}">
                                                ${order.user.username}
                                        </a>
                                    </td>
                                    <!--Start user modal window-->
                                    <div aria-hidden="true" aria-labelledby="userDetailsModalTitle" class="modal fade"
                                         id="userDetailsModal${order.user.id}" role="dialog" tabindex="-1">
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
                                                                <span>${order.user.username}</span>
                                                            </h3>
                                                        </div>
                                                        <div class="card-body">
                                                            <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="users.profile.full_name"/></span>
                                                                <span>${order.user.fullName}</span>
                                                            </div>
                                                            <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="users.profile.email"/></span>
                                                                <span>${order.user.email}</span>
                                                            </div>
                                                            <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="users.profile.bio"/></span>
                                                                <span>${order.user.bio}</span>
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
                                    <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                                          value="${order.price}"/></td>
                                    <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                                          value="${order.discount}"/></td>
                                    <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                                          value="${order.fixedPrice}"/></td>
                                    <td>
                                        <c:if test="${order.pending}">
                                            <div class="btn-group btn-block">
                                                <a class="btn btn-outline-success"
                                                   href="${pageContext.request.contextPath}/app/tours/orders/mark-paid?id=${order.id}&page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                                    <fmt:message key="orders.status.button.mark_paid"/>
                                                </a>
                                                <a class="btn btn-outline-danger"
                                                   href="${pageContext.request.contextPath}/app/tours/orders/mark-denied?id=${order.id}&page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}">
                                                    <fmt:message key="orders.status.button.mark_denied"/>
                                                </a>
                                            </div>
                                        </c:if>
                                        <c:if test="${order.paid}">
                                            <button class="btn btn-success btn-block">
                                                <fmt:message key="orders.status.button.mark_paid"/>
                                            </button>
                                        </c:if>
                                        <c:if test="${order.denied}">
                                            <button class="btn btn-danger btn-block">
                                                <fmt:message key="orders.status.button.mark_denied"/>
                                            </button>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
                <div class="card-footer">
                    <%@include file="/WEB-INF/fragments/orders-paginator.jspf" %>
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