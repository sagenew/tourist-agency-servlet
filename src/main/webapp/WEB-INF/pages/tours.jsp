<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ta" uri="tourism-agency-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All tours</title>
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
                    <c:if test="${!requestScope.tours.isEmpty()}">
                        <h1 class="display-5"><fmt:message key="tours.header"/></h1>
                    </c:if>
                    <c:if test="${requestScope.tours.isEmpty()}">
                        <h1 class="display-5"><fmt:message key="tours.empty"/></h1>
                    </c:if>
                </div>
                <div class="card-body">
                    <ta:hasAuthority authority="ADMIN">
                        <div class="row">
                            <div class="col-md-6 mt-1">
                                <a href="${pageContext.request.contextPath}/app/tours/add"
                                   class="btn btn-primary" type="submit">
                                    <fmt:message key="tours.button.add"/>
                                </a>
                            </div>
                        </div>
                    </ta:hasAuthority>
                    <c:if test="${!requestScope.tours.isEmpty()}">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>
                                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'id'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                                        <fmt:message key="tours.id"/>
                                    </a>
                                </th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'name'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                                        <fmt:message key="tours.name"/>
                                    </a>
                                </th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'type'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                                        <fmt:message key="tours.tour_type"/>
                                    </a>
                                </th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'price'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                                        <fmt:message key="tours.price"/>
                                    </a>
                                </th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'group_size'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                                        <fmt:message key="tours.group_size"/>
                                    </a>
                                </th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'hotel'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                                        <fmt:message key="tours.hotel_type"/>
                                    </a>
                                </th>
                                <th>
                                    <a href="${pageContext.request.contextPath}/app/tours?page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${'hot'}&sortDir=${(requestScope.sortDir != 'ASC') ? 'ASC' : 'DESC'}">
                                        <fmt:message key="tours.is_hot"/>
                                    </a>
                                </th>
                                <ta:hasAuthority authority="ADMIN">
                                    <th>
                                        <fmt:message key="tours.make_hot.label"/>
                                    </th>
                                </ta:hasAuthority>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.tours}" var="tour">
                                <tr>
                                    <td>${tour.id}</td>
                                    <td>
                                        <a href="#tourDetailsModal"
                                           data-toggle="modal"
                                           data-target="#tourDetailsModal${tour.id}">
                                                ${tour.name}
                                        </a>
                                    </td>
                                    <!--Start modal window-->
                                    <div aria-hidden="true" aria-labelledby="tourDetailsModalTitle" class="modal fade"
                                         id="tourDetailsModal${tour.id}" role="dialog" tabindex="-1">
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
                                                                <span>${tour.name}</span>
                                                            </h3>
                                                        </div>
                                                        <div class="card-body">
                                                            <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="tours.modal.type"/></span>
                                                                <span>${tour.type.toString()}</span>
                                                            </div>
                                                            <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="tours.modal.price"/></span>
                                                                <span>${tour.price}</span>
                                                            </div>
                                                            <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="tours.modal.group_size"/></span>
                                                                <span>${tour.groupSize}</span>
                                                            </div>
                                                            <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="tours.modal.hotel"/></span>
                                                                <span>${tour.hotel.toString()}</span>
                                                            </div>
                                                            <div class="card-text">
                                                                <span class="text-primary"><fmt:message
                                                                        key="tours.modal.description"/></span>
                                                                <span>${tour.description}</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <div class="btn-group">
                                                        <c:if test="${sessionScope.authUser != null}">
                                                            <a href="${pageContext.request.contextPath}/app/tours/orders/add?id=${tour.id}&page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}"
                                                               class="btn btn-primary">
                                                                <fmt:message key="tours.modal.button.order_tour"/>
                                                            </a>
                                                        </c:if>
                                                        <ta:hasAuthority authority="ADMIN">
                                                            <a href="${pageContext.request.contextPath}/app/tours/update?id=${tour.id}"
                                                               class="btn btn-warning">
                                                                <fmt:message key="tours.modal.button.update_tour"/>
                                                            </a>
                                                            <a href="${pageContext.request.contextPath}/app/tours/delete?id=${tour.id}&page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}"
                                                               class="btn btn-danger">
                                                                <fmt:message key="tours.modal.button.delete_tour"/>
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
                                    <!--End modal window-->
                                    <td>${tour.type.toString()}</td>
                                    <td>${tour.price}</td>
                                    <td>${tour.groupSize}</td>
                                    <td>${tour.hotel.toString()}</td>
                                    <td>
                                        <c:if test="${tour.hot == true}">
                                            <img src="${pageContext.request.contextPath}/images/hot_icon_true.png"
                                                 width="30"
                                                 height="30" alt="hot_icon_true">
                                        </c:if>
                                        <c:if test="${tour.hot == false}">
                                            <img src="${pageContext.request.contextPath}/images/hot_icon_false.png"
                                                 width="30"
                                                 height="30" alt="hot_icon_true">
                                        </c:if>
                                    </td>
                                    <ta:hasAuthority authority="ADMIN">
                                        <td>
                                            <a href="${pageContext.request.contextPath}/app/tours/mark-hot?id=${tour.id}&page=${requestScope.currentPage}&size=${requestScope.pageSize}&sortCol=${requestScope.sortCol}&sortDir=${requestScope.sortDir}"
                                               class="btn btn-primary">
                                                <fmt:message key="tours.make_hot.button"/>
                                            </a>
                                        </td>
                                    </ta:hasAuthority>

                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
                <div class="card-footer">
                    <%@include file="/WEB-INF/fragments/tours-paginator.jspf" %>
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
