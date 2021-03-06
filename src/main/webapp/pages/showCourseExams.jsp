<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>${exams.get(0).courseDto.title} exams</title>
    <style type="text/css">
        <%@include file="styles/showPendingStyle.css"%>
        th, tr, td {
            text-align: center;
            overflow: auto;
            width: fit-content;
        }

        th {
            background: #007bff;
            color: #ffffff;
        }

        td {
            padding: 1vh 0;
            margin: 0;
            vertical-align: middle;
        }

        .table thead th {
            vertical-align: middle;
        }

        .resultTable {
            margin: 5vh auto;
            width: max-content;
            height: border-box;
            text-align: center;
        }

        .edit-btn {
            margin-top: 0;
            background: orange;
            border: none;
        }

        input {
            background: none;
            border: none;
            text-align: center;
            padding: 0;
            margin: 0;
            overflow: visible;
            height: 4vh;
        }

        .table td {
            vertical-align: middle;
        }

        .duration {
            width: 5vw;
        }
    </style>
</head>
<body>
<form method="Get" action="${pageContext.request.contextPath}/teacher">
    <button type="submit" class="btn btn-success btn-group" style="margin: 2vh 2vw">Dashboard</button>
</form>
<div class="resultTable">
    <table class="table table-hover table table-bordered">
        <thead>
        <tr>
            <th>Course</th>
            <th>Title</th>
            <th>Description</th>
            <th>Creator</th>
            <th>Duration</th>
            <th>StartDate</th>
            <th>EndDate</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${exams}" var="exam">
            <form:form modelAttribute="examDto" action="/teacher/exams/edit" method="post">
                <tr>
                    <td hidden><form:input path="courseDto.category" value="${exam.courseDto.category}"/></td>
                    <td><form:input path="courseDto.title" value="${exam.courseDto.title}" readonly="true"/></td>
                    <td><form:input path="title" value="${exam.title}" readonly="true"/></td>
                    <td><form:input path="description" value="${exam.description}" readonly="true"/></td>
                    <td><input style="width: 12vw" name="creator"
                               value="${exam.creatorDto.name} ${exam.creatorDto.family}" readonly/></td>
                    <td><form:input cssClass="duration" path="duration" value="${exam.duration}" readonly="true"/></td>
                    <td><form:input path="startDate" value="${exam.startDate}" readonly="true"/></td>
                    <td><form:input path="endDate" value="${exam.endDate}" readonly="true"/></td>
                    <td hidden><form:hidden path="status" value="${exam.status}"/></td>
                    <td hidden><form:hidden path="creatorDto.email" value="${exam.creatorDto.email}"/></td>
                    <td>
                        <button class="btn btn-btn btn-primary btn-block edit-btn">Edit</button>
                    </td>
                </tr>
            </form:form>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>

</html>