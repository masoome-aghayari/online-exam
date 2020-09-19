<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>teacher dashboard</title>
    <style type="text/css">
        <%@include file="/pages/styles/homeStyle.css"%>
    </style>
</head>
<body>

<div class="main-block">
    <form method="Get">
        <div class="span2">
            <button formaction="/teacher/courses/1" class="btn btn-primary btn-block" type="submit">Courses
            </button>
        </div>
    </form>
</div>
</body>
</html>
<%-- <form:hidden path="email"/>--%>
<%-- <button formaction="/teacher/searchProcess/1" id="search" name="search" class="btn btn-primary btn-block"
                     type="submit">Search
             </button>
             <button formaction="/admin/course" id="course" name="course" class="btn btn-primary btn-block"
                     type="submit">Course
             </button>
             <button formaction="/admin/category" id="category" name="category" class="btn btn-primary btn-block"
                     type="submit">Category
             </button>--%>