<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Codebanoo
  Date: 9/18/2020
  Time: 7:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>choose question type</title>
    <style type="text/css">
        <%@include file="/pages/styles/homeStyle.css"%>
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/teacher">
    <button type="submit" class="btn btn-success btn-group" style="margin: 2vh 2vw">Dashboard</button>
</form>
<div class="main-block">
    <form:form modelAttribute="examDto" method="get">
        <form:hidden path="creatorDto.email"/>
        <form:hidden path="title"/>
        <div class="span2">
            <button formaction="/teacher/exam/add-question/new/test" class="btn btn-primary btn-block"
                    type="submit">Add Question From Bank
            </button>
            <button formaction="/teacher/exam/add-question/new/essay" class="btn btn-success btn-block"
                    type="submit">Define New Question
            </button>
        </div>
    </form:form>
</div>
</body>
</html>
