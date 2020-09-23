<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/dc1776693c.js" crossorigin="anonymous"></script>
    <title>login form</title>
    <style type="text/css">
        <%@include file="styles/addCourseStyle.css" %>
        .btn-show-hide-pwd {
            font-size: 18px;
            color: #0c0068;
            cursor: pointer;
            margin-left: -30px;
            margin-top: 10px;
            opacity: 0.5;
        }

        .btn-show-hide-pwd:hover {
            opacity: 1;
        }

        .btn-show-hide-pwd:active {
            transform: scale(0.9);
        }

        .form-control {
            width: 20vw;
        }

        .form label {
            margin-left: 0;
        }
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/">
    <button type="submit" id="home" name="home" class="btn btn-success btn-group"
            style="margin: 2vh 2vw">Home
    </button>
</form>
<div class="main-block">
    <div style="color: red; margin-bottom: 2vh"><strong>${message}</strong></div>
    <form:form modelAttribute="userDto" action="/loginProcess" cssClass="content" method="Post">
        <div class="header">
            <h3 style="text-align: left">Login Form</h3>
        </div>
        <div class="form-group">
            <form:label path="email" cssStyle="margin-left: 0">Email:</form:label>
            <form:input path="email" id="email" class="form-control" required="required"/>
        </div>
        <div class="form-group">
            <form:label path="password" cssStyle="margin-left: 0">Password:</form:label>
            <form:password path="password" id="password" name="password" cssClass="form-control"
                           placeholder="Enter Password" required="required"/>
            <i class="far fa-eye btn-show-hide-pwd" data-for="password"></i>
        </div>
        <div class="span2">
            <form:button class="btn btn-primary btn-block" type="submit" value="Submit">Login</form:button>
        </div>
    </form:form>
</div>
</body>
<script>

    const allBtnSHP = document.querySelectorAll(".btn-show-hide-pwd");
    allBtnSHP.forEach((button) => {
        const forElement = document.getElementById(button.dataset.for);
        if (forElement && forElement instanceof HTMLInputElement) {
            ["mousedown", "touchstart"].forEach((eventName) => {
                button.addEventListener(eventName, () => {
                    forElement.setAttribute("type", "text");
                });
            });
            ["mouseup", "mouseleave", "touchend", "touchcancel"].forEach((eventName) => {
                button.addEventListener(eventName, (e) => {
                    e.preventDefault();
                    forElement.setAttribute("type", "password");
                });
            });
        }
    });
</script>
</html>