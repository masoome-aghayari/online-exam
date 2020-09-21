<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>essay question</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/teacher">
    <button type="submit" class="btn btn-success btn-group" style="margin: 2vh 2vw">Dashboard</button>
</form>
<div class="container">
    <div style="color: red"><strong>${message}</strong></div>
    <h4>Create New Essay Question</h4>
    <hr>
    <form:form modelAttribute="questionDto" action="/teacher/exam/add-question/new/test/process">
    <div>
        <form:label path="title">Title:</form:label>
        <form:input path="title" value="${questionDto.title}" name="title" required="true"/>

        <form:label path="text" style="width: 5vw">Text:</form:label>
        <form:input path="text" value="${questionDto.text}" name="duration" required="true"/>
    </div>
    <button type="submit">Add Question</button>
    </form:form>
</body>
</html>
