<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Error</title>
    <style type="text/css">
        <%@include file="/pages/styles/errorStyle.css"%>
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/" method="GET" class="messageForm">
    <div class="formcontainer">
        <h1>Error: ${errorMessage}</h1>
        <hr/>
        <button type="submit">Home</button>
    </div>
</form>
</body>
</html>