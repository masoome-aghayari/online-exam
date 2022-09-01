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
    <title>delete category</title>
    <style type="text/css">
        <%@include file="styles/addCourseStyle.css" %>
        select {
            border-radius: 4px;
        }

        label {
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
<div><p><strong>${message}</strong></p></div>
<div class="main-block">
    <div class="header"><h4 style="width: max-content; margin-bottom: 5vh">Delete Category Form</h4></div>
    <form action="${pageContext.request.contextPath}/admin/category/delete" class="content" method="POST">
        <div class="form-group">
            <label for="name">Name:</label>
            <select id="name" class="dropdown form-control" name="name" required="required">
                <option value="">--</option>
                <c:forEach items="${nameList}" var="name">
                    <option value="${name}">${name}</option>
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