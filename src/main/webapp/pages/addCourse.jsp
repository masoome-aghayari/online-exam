<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/dc1776693c.js" crossorigin="anonymous"></script>
    <title>add course form</title>
    <style type="text/css">
        <%@include file="styles/addCourseStyle.css" %>
        label {
            margin-left: 0;
        }
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/admin">
    <button type="submit" id="dashboard" name="dashboard" class="btn btn-success btn-group"
            style="margin: 2vh 2vw">Dashboard
    </button>
</form>
<div class="main-block">
    <div>
        <p>${message}</p>
    </div>
    <form:form modelAttribute="courseDto" action="/admin/course/add" cssClass="content" method="POST">
        <div class="header">
            <h3>Add Course Form</h3>
        </div>
        <div class="form-group">
            <form:label path="category">Category:</form:label>
            <form:select path="category" cssClass="dropdown form-control" required="required" cssStyle="width: 12vw">
                <option value="">--</option>
                <c:forEach items="${categories}" var="category">
                    <option value="${category.name}">${category.name}</option>
                </c:forEach>
            </form:select>
        </div>
        <div class="form-group">
            <form:label path="duration">Duration:</form:label>
            <form:input path="duration" type="number" id="duration" class="form-control" required="required"/>
        </div>
        <div class="form-group">
            <form:label path="capacity">Capacity:</form:label>
            <form:input path="capacity" type="number" id="capacity" class="form-control" required="required"/>
        </div>
        <div class="form-group">
            <form:label path="startDate">Start Date:</form:label>
            <form:input path="startDate" type="date" id="startDate" cssClass="form-control" required="required"/>
        </div>
        <hr>
        <div class="span2">
            <form:button class="btn btn-primary btn-block">Add</form:button>
        </div>
    </form:form>
</div>
</body>
</html>