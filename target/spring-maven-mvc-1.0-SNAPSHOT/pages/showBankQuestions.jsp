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
    <title>bank questions</title>
    <style>
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
    </style>
</head>
<body>
<form class="content" method="Get">
    <button type="submit" class="btn btn-success btn-group" style="margin: 2vh 2vw;"
            formaction="${pageContext.request.contextPath}/teacher">Dashboard
    </button>
</form>
<div class="resultTable">
    <table class="table table-hover table table-bordered">
        <thead>
        <tr>
            <th>Title</th>
            <th>Text</th>
            <th>Type</th>
            <th>Options</th>
            <th>Enter Mark</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${questions}" var="question">
            <form:form modelAttribute="questionDto" action="/teacher/exam/add-question/bank/process" method="post">
                <tr>
                    <td class="table-td"><form:input path="title" cssStyle="width: 10vw;" value="${question.title}"
                                                     readonly="true"/></td>
                    <td class="table-td"><form:input path="text" cssStyle="border: none; background: none; width: 25vw"
                                                     value="${question.text}" readonly="true"/></td>
                    <td class="table-td"><form:input path="type" cssStyle="width: 5vw;" value="${question.type}"
                                                     readonly="true"/></td>
                    <td class="table-td" style="text-align: left; width: 25vw">
                        <c:if test="${question.options != null}">
                            <c:forEach items="${question.options}" var="option">
                                ${option}
                                <br>
                            </c:forEach>
                        </c:if>
                    </td>
                    <td id="mark" class="table-td">
                        <input type="number" name="mark"
                               style="border: 1px solid black; border-radius: 5px; width: 5vw;" required>
                    </td>
                    <td>
                        <button class="btn btn-btn btn-primary btn-block edit-btn">Add</button>
                    </td>
                </tr>
            </form:form>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>