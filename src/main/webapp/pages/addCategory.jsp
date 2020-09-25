<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>add category</title>
    <style type="text/css">
        <%@include file="styles/addCourseStyle.css" %>
        label {
            margin-left: 0;
            width: 4vw;
        }
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/admin">
    <button type="submit" id="dashboard" name="dashboard" class="btn btn-success btn-group" style="margin: 2vh 2vw">
        Dashboard
    </button>
</form>
<div><p><strong>${message}</strong></p></div>
<div class="main-block" id="main-block">
    <div><h4 style="width: max-content; margin-bottom: 5vh">Add Category Form</h4></div>
    <form:form modelAttribute="category" action="/admin/category/add" class="content" id="main-form" method="POST">
        <div class="form-group">
            <form:label path="name">Name:</form:label>
            <form:input path="name" placeholder="name" type="text" class="form-control"
                        onkeypress="return isAlphabetKey(event)" required="required"/>
        </div>
        <hr>
        <div class="span2">
            <form:button id="add" name="add" class="btn btn-primary btn-block">Add</form:button>
        </div>
    </form:form>
</div>
</body>
<script>
    function isAlphabetKey(evt) {
        const charCode = evt.keyCode;
        const isAlphabet = ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122) || charCode === 8 || charCode === 32);
        if (!isAlphabet) {
            window.alert("Just Alphabets Are Allowed");
            return false;
        }
    }
</script>
</html>