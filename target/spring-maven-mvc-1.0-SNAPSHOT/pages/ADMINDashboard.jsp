<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>admin dashboard</title>
    <style type="text/css">
        <%@include file="/pages/styles/homeStyle.css"%>
    </style>
</head>
<body>
<div class="main-block">
    <form method="get">
        <div class="span2">
            <button formaction="/admin/pending-list/1" class="btn btn-primary btn-block" type="submit">
                Pending Users
            </button>
            <button formaction="/admin/searchProcess/1" class="btn btn-primary btn-block" type="submit">Search
            </button>
            <button formaction="/admin/course" class="btn btn-primary btn-block" type="submit">Course
            </button>
            <button formaction="/admin/category" class="btn btn-primary btn-block" type="submit">Category
            </button>
        </div>
    </form>
</div>
</body>
</html>