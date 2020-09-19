<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">

    <title>result</title>
    <style type="text/css">
        <%@include file="styles/errorStyle.css"%>
    </style>
</head>
<body>
<form:form method="Post" class="messageForm" modelAttribute="userDto">
    <div class="formcontainer" id="result_message">
        <h1 id="message">${message}</h1>
        <hr/>
        <form:input path="email" value="${userDto.email}" hidden="hidden"/>
        <form:button id="resendbtn" class="btn btn-success btn-block"
                     formaction="/resendProcess" disabled="${resendStatus}">Didn't get email?</form:button>
        <form:button class="btn btn-primary btn-block" formaction="/">Home</form:button>
    </div>
</form:form>
</body>
</html>