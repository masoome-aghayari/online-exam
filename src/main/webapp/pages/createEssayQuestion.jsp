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
    <form action="${pageContext.request.contextPath}/teacher">
    <button type="submit" class="btn btn-success btn-group" style="margin: 2vh 2vw">Dashboard</button>
    </form>
    <div class="main-block">
    <div style="color: red"><strong>${message}</strong></div>
    <form:form modelAttribute="questionDto" cssClass="content" action="/teacher/exam/add-question/new/test/process">
        <form:hidden path="type"/>
        <div class="header">
            <h4>Create New Test Question</h4>
            <hr>
        </div>
        <div class="form-group">
            <form:label path="text" cssClass="odd-labels">Text:</form:label>
            <form:textarea path="text" value="${questionDto.text}" name="text" cssClass="form-control"
                           cssStyle="width: 38.3vw; height: 15vh" required="true"></form:textarea>
        </div>
        <div class="form-group">
            <form:label path="title" cssClass="odd-labels">Title:</form:label>
            <form:input path="title" value="${questionDto.title}" name="title" cssClass="form-control"
                        required="true"></form:input>

            <label for="mark" class="even-labels">Mark:</label>
            <input type="number" id="mark" name="mark" style="width: 5vw" class="form-control" required/>
        </div>
        <div class="add-option-add-to-bank">
            <button onclick="appendOption()" class="btn btn-primary btn-block op-btn">New Option</button>
            <div class="form-check mb-2" style="margin-top: 2vh">
                <form:checkbox path="addToBank" class="form-check-input filled-in" id="addToBank" name="addToBank"
                               cssStyle="width: 25px; height: 25px" value="${!questionDto.addToBank}"/>
                <label class="form-check-label" for="addToBank">Add Question To Bank</label>
            </div>
        </div>
    </form:form>
    </body>
    </html>
