<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/dc1776693c.js" crossorigin="anonymous"></script>
    <title>Registration Form</title>
    <style type="text/css">
        <%@ include file="/pages/styles/registerStyle.css" %>
        <%@ include file="/pages/styles/errorStyle.css" %>
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
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/" style="height: fit-content; margin-left: 9vw">
    <button type="submit" id="home" name="home" class="btn btn-success btn-group">Home</button>
</form>
<div class="main-block" id="main-block">
    <form:form modelAttribute="userDto" class="content" action="registerProcess" id="main-form" method="POST">
        <div>
            <h3>Registration Form</h3>
        </div>

        <div class="form-group field">
            <form:label path="name" class="col-md-4 control-label">Name:</form:label>
            <form:input path="name" id="name" name="name" placeholder="name" type="text" class="form-control"
                        onkeypress="return isAlphabetKey(event)" required="required"/>
            <form:errors path="name" cssClass="error"/>
        </div>

        <div class="form-group field">
            <form:label path="family" class="col-md-4 control-label">Family:</form:label>
            <form:input path="family" id="family" name="family" placeholder="Family" class="form-control"
                        onkeypress="return isAlphabetKey(event)" required="required"/>
            <form:errors path="family" cssClass="error"/>
        </div>

        <div class="form-group field">
            <form:label path="email" class="col-md-4 control-label">Email:</form:label>
            <form:input path="email" placeholder="email" id="email"
                        pattern="^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$"
                        title="simple@example.com" class="form-control" required="required"/>
            <form:errors path="email" cssClass="error"/>
        </div>
        <div class="form-group field">
            <form:label path="password" class="col-md-4 control-label">Password:</form:label>
            <form:password path="password" id="password" name="password" class="form-control"
                           placeholder="Enter Password" required="required"/>
            <i class="far fa-eye btn-show-hide-pwd" data-for="password"></i>
            <form:errors path="password" cssClass="error" style="margin-left: 1.6vw"/>
        </div>
        <div class="form-group field">
                <%--<form:label path="confirmPassword" class="col-md-4 control-label">Confirm Password:</form:label>--%>
            <form:password path="confirmPassword" placeholder="Re-Enter Password"
                           id="confirmPassword" class="form-control" required="required" cssStyle="margin-left: 5vw"/>
            <i class="far fa-eye btn-show-hide-pwd" data-for="confirmPassword"></i>
            <form:errors path="confirmPassword" cssClass="error" style="margin-left: 1.6vw"/>
        </div>

        <div class="form-group field">
            <form:label path="role" class="col-md-4 control-label">Role:</form:label>
            <form:select path="role" id="role" required="required" class="dropdown">
                <option value="">--</option>
                <c:forEach items="${roles}" var="role">
                    <option value="${role}">${role}</option>
                </c:forEach>
            </form:select>
            <form:errors path="role" cssClass="error"/>
        </div>
        <div class="span2">
            <form:button id="register" name="register" class="btn btn-primary btn-block button">Register</form:button>
        </div>
    </form:form>
</div>
</body>
<script>

    function isNumberKey(evt) {
        const charCode = (evt.which) ? evt.which : evt.keyCode;
        const isNumeric = (charCode > 47 && charCode < 58);
        if (!isNumeric) {
            window.alert("Just Digits Are Allowed");
            return isNumeric;
        }
    }

    function isAlphabetKey(evt) {
        const charCode = evt.keyCode;
        const isAlphabet = ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122) || charCode === 8 || charCode === 32);
        if (!isAlphabet) {
            window.alert("Just Alphabets Are Allowed");
            return false;
        }
    }

    function isAllowedCharacter(evt) {
        const charCode = (evt.which) ? evt.which : evt.keyCode;
        const isValidKey = ((charCode > 64 && charCode < 91) ||
            (charCode > 97 && charCode < 123) ||
            (charCode > 47 && charCode < 58));
        if (!isValidKey) {
            window.alert("Just Alphabets And Numbers Are Allowed");
            return false;
        }
    }

    function removeRequired(form) {
        $.each(form, function (key, value) {
            if (value.hasAttribute("required")) {
                value.removeAttribute("required");
            }
        });
    }

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