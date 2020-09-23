<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>teacher courses</title>
    <style>
        <%@include file="styles/tableStyle.css"%>
        <%@include file="styles/showPendingStyle.css"%>
        .table-td {
            vertical-align: middle;
        }

        .save-btn, .new-btn {
            margin-top: 0;
            background: orange;
            border: none;
        }

        .new-btn {
            background: #ff006a;
        }

        .content {
            margin: 5vh auto;
            width: max-content;
        }
    </style>
</head>
<body>
<form class="content" method="Get">
    <button type="submit" class="btn btn-success btn-group" style="margin: -3vh -37vw;"
            formaction="${pageContext.request.contextPath}/teacher">Dashboard
    </button>
    <div class="resultTable">
        <table class="table table-hover table table-bordered">
            <thead>
            <tr>
                <th>Course Title</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${courses}" var="course" varStatus="i">
                <tr>
                    <td id="${course.title}" class="table-td" readonly>${course.title}</td>
                    <td>
                        <button class="btn btn-btn btn-primary btn-block save-btn"
                                formaction="/teacher/courses/${course.title}/exams/1">Exams
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-btn btn-primary btn-block new-btn"
                                formaction="/teacher/courses/${course.title}/new-exam">New Exam
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>
</body>
</html>