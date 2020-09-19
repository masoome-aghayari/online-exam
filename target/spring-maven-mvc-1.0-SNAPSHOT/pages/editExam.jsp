<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/dc1776693c.js" crossorigin="anonymous"></script>
    <title>edit exam</title>
    <style type="text/css">
        <%@include file="styles/homeStyle.css" %>
    </style>
</head>
<body>
<form class="content" method="Get">
    <button type="submit" class="btn btn-success btn-group" style="margin: -3vh -37vw;"
            formaction="${pageContext.request.contextPath}/teacher">Dashboard
    </button>
</form>
<div class="main-block">
    <form:form modelAttribute="examDto">
        <form:hidden path="creatorDto.email"/>
        <form:hidden path="title"/>
    <div class="span2">
        <button type="submit" formaction="/teacher/exams/edit/add-question" class="btn btn-primary btn-group">Add
            Question
        </button>
        <button type="submit" formaction="/teacher/exams/edit/show-questions" class="btn btn-primary btn-group">Show
            Questions
        </button>
        <button type="submit" formaction="/teacher/exams/edit/delete" class="btn btn-primary btn-group">Delete Exam
        </button>
        <button type="submit" formaction="/teacher/exams/edit/stop" class="btn btn-primary btn-group">Stop Exam</button>
        <button type="submit" formaction="/teacher/exams/edit/activate" class="btn btn-primary btn-group">Activate
            Exam
        </button>
    </div>
    </form:form>
</body>
</html>
