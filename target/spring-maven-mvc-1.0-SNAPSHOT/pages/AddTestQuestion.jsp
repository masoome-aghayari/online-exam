<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Codebanoo
  Date: 9/18/2020
  Time: 8:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test question</title>
</head>
<body>
<form:form modelAttribute="creatorDto" action="${pageContext.request.contextPath}/teacher">
    <form:hidden path="email"/>
    <button type="submit" class="btn btn-success btn-group"
            style="margin: 2vh 2vw">Dashboard
    </button>
</form:form>
<div class="container">
    <div style="color: red"><strong>${message}</strong></div>
    <h4>Create New Exam Form</h4>
    <hr>
<form:form modelAttribute="newExam">
    <form:hidden path="creatorDto.email"/>
    <div>
    <label for="title">Title:</label>
    <input id="title" value="${questionDto.title}" name="title" required/>

    <label for="text" style="width: 5vw;">Text:</label>
    <input id="text" value="${questionDto.text}" name="duration"/>
    </div>
    <div>
    <label for="numberOfOptions" style="width: 5vw;">Duration:</label>
    <input type="number" id="numberOfOptions" value="${newExam.duration}" name="duration"/>
    </div>
</body>
</html>
