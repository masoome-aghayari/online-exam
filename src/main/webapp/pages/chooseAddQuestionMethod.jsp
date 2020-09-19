<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>add question method</title>
    <style type="text/css">
        <%@include file="/pages/styles/homeStyle.css"%>
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/teacher">
    <button type="submit" class="btn btn-success btn-group" style="margin: 2vh 2vw">Dashboard</button>
</form>
<div style="color: red; font-size: 16px"><strong>${message}</strong></div>
<div class="main-block">
    <form:form modelAttribute="examDto" method="get">
        <form:hidden path="creatorDto.email"/>
        <form:hidden path="title"/>
        <div class="span2">
            <button formaction="/teacher/exam/add-question/bank" id="bank" name="bank" class="btn btn-primary btn-block"
                    type="submit">Add Question From Bank
            </button>
            <button formaction="/teacher/exam/add-question/new/chooseType" id="newQuestion" name="newQuestion"
                    class="btn btn-success btn-block" type="submit">Define New Question
            </button>
        </div>
    </form:form>
</div>
</body>
</html>