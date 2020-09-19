<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <title>userList</title>
    <style>
        .content {
            margin: 15vh auto;
            width: max-content;
            height: border-box;

        }

        .resultTable {
            margin-top: 5vh;
        }

        th, tr, td {
            text-align: center;
            overflow: scroll;
            width: max-content;
        }

        th {
            background: #007bff;
            color: #ffffff;
        }

        .span2 form button {
            width: max-content;
            margin-top: 5vh;
        }

    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/allUsers" method="get" class="content">
    <div class="resultTable">
        <table class="table table-hover table table-striped">
            <thead>
            <tr>
                <th>Firstname</th>
                <th>LastName</th>
                <th>UserNumber</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.family}</td>
                    <td>${user.userNumber}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="span2">
        <button id="home" name="home" formaction="/" class="btn btn-success btn-block">Home</button>
    </div>
</form>
</body>
</html>