<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>create exam</title>
    <style type="text/css">
        <%@include file="styles/createExamStyle.css"%>
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/teacher">
    <button type="submit" class="btn btn-success btn-group" style="margin: 2vh 2vw">Dashboard</button>
</form>
<div class="container">
    <div style="color: red"><strong>${message}</strong></div>
    <h4>Create New Exam Form</h4>
    <hr>
    <form:form modelAttribute="newExam">
        <form:hidden path="creatorDto.email"/>
        <div>
            <label for="title">Title:</label>
            <input id="title" value="${newExam.title}" name="title" readonly required/>

            <label for="duration" style="width: 5vw;">Duration:</label>
            <input type="number" id="duration" value="${newExam.duration}" name="duration" required/>
        </div>
        <div>
            <label for="startDate">Start Date:</label>
            <input type="date" id="startDate" value="${newExam.startDate}" name="startDate" required/>

            <label for="endDate" style="width: 5vw">End Date:</label>
            <input type="date" id="endDate" value="${newExam.endDate}" name="endDate" required/>
        </div>
        <div style="display: flex">
            <label for="description">Description:</label>
            <textarea id="description" name="description" class="textarea"></textarea>
        </div>
        <div style="text-align: center">
            <button type="submit" formaction="/teacher/courses/${courseTitle}/new-exam/save"
                    class="btn btn-primary btn-group save-btn">Save Exam
            </button>
        </div>
    </form:form>
</div>
</body>
</html>