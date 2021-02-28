<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ta" uri="tourism-agency-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="i18n.messages"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit tour</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<%@include file="/WEB-INF/fragments/navbar.jspf" %>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h2 class="display-5"><fmt:message key="tours.update.title"/></h2>
        </div>
        <div class="card-body">
            <div class="row">
                <div class="col-md-6">
                    <form method="post"
                          action="${pageContext.request.contextPath}/app/tours/update?id=${requestScope.tour.id}">
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label class="col-form-label" for="name"><fmt:message
                                        key="tours.update.tour_name.label"/></label>
                                <input class="form-control" id="name" name="name" value="${requestScope.tour.name}"
                                       placeholder="<fmt:message key="tours.update.tour_name.placeholder"/>" type="text"
                                       required/>
                                <span class="text-danger">
                                    <c:forEach items="${requestScope.errors.nameErrors}" var="error">${error}
                                        <br></c:forEach>
                                </span>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-form-label" for="price"><fmt:message
                                        key="tours.update.tour_price.label"/></label>
                                <input class="form-control" id="price" name="price" value="${requestScope.tour.price}"
                                       placeholder="<fmt:message key="tours.update.tour_price.placeholder"/>"
                                       type="number" required/>
                                <span class="text-danger">
                                    <c:forEach items="${requestScope.errors.priceErrors}" var="error">${error}
                                        <br></c:forEach>
                                </span>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-form-label" for="groupSize"><fmt:message
                                        key="tours.update.tour_group_size.label"/></label>
                                <input class="form-control" id="groupSize" name="groupSize"
                                       value="${requestScope.tour.groupSize}"
                                       placeholder="<fmt:message key="tours.update.tour_group_size.placeholder"/>"
                                       type="number" required/>
                                <span class="text-danger">
                                    <c:forEach items="${requestScope.errors.groupSizeErrors}" var="error">${error}
                                        <br></c:forEach>
                                </span>
                            </div>
                            <div class="row col-md-12">
                                <div class="form-group col-md-6">
                                    <label class="col-form-label" for="type"><fmt:message
                                            key="tours.update.tour_type.label"/></label>
                                    <select name="type" class="form-control" id="type">
                                        <c:forEach items="${requestScope.tourTypes}" var="type">
                                            <option value="${type.name()}">${type.toString()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="col-form-label" for="hotel"><fmt:message
                                            key="tours.update.hotel_type.label"/></label>
                                    <select name="hotel" class="form-control" id="hotel">
                                        <c:forEach items="${requestScope.hotelTypes}" var="hotel">
                                            <option value="${hotel.name()}">${hotel.toString()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-md-12">
                                <label class="col-form-label" for="description"><fmt:message
                                        key="tours.update.description.label"/></label>
                                <textarea id="description" rows="4" name="description"
                                          placeholder="<fmt:message key="tours.update.description.placeholder"/>"
                                          class="form-control">${requestScope.tour.description}</textarea>
                                <span class="text-danger">
                                    <c:forEach items="${requestScope.errors.descriptionErrors}" var="error">${error}
                                        <br></c:forEach>
                                </span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mt-1">
                                <input class="btn btn-primary" value="<fmt:message key="tours.update.button.submit"/>"
                                       type="submit">
                            </div>
                        </div>
                    </form>
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