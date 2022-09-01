<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/dc1776693c.js" crossorigin="anonymous"></script>
    <title>delete course</title>
    <style type="text/css">
        <%@include file="styles/addCourseStyle.css" %>
        label{
            margin-left: 0;
            width: 4vw;
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
        <p style="width: max-content">${message}</p>
    </div>
    <form action="${pageContext.request.contextPath}/admin/course/delete" class="content" method="POST">
        <div class="header">
            <h3>Delete Course Form</h3>
        </div>
        <div class="form-group">
            <label for="title">Title:</label>
            <select id="title" class="dropdown form-control" name="title" required="required">
                <option value="">--</option>
                <c:forEach items="${titleList}" var="title">
                    <option value="${title}">${title}</option>
                </c:forEach>
            </select>
        </div>
        <hr>
        <div class="span2">
            <button type="submit" class="btn btn-primary btn-block">Delete</button>
        </div>
    </form>
</div>
</body>
</html>