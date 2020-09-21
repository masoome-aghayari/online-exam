<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/dc1776693c.js" crossorigin="anonymous"></script>
    <title>edit exam</title>
    <style type="text/css">
        <%@include file="styles/homeStyle.css" %>
    </style>

</head>
<body onload="checkButtonsDisable()">
<form class="content" method="Get">
    <button type="submit" class="btn btn-success btn-group" style="margin: -3vh -37vw;"
            formaction="${pageContext.request.contextPath}/teacher">Dashboard
    </button>
</form>
<div class="main-block">
    <form>

        <div class="span2">
            <button type="submit" id="add-question" formaction="/teacher/exams/edit/add-question"
                    class="btn btn-primary btn-block">Add Question
            </button>
            <button type="submit" formaction="/teacher/exams/edit/show-questions" class="btn btn-primary btn-block">
                Show Questions
            </button>
            <button type="submit" formaction="/teacher/exams/edit/delete" class="btn btn-primary btn-block">
                Delete Exam
            </button>
            <button type="submit" id="stop" formaction="/teacher/exams/edit/stop" class="btn btn-primary btn-block">
                Stop Exam
            </button>
            <button type="submit" id="activate" formaction="/teacher/exams/edit/activate"
                    class="btn btn-primary btn-block">
                Activate Exam
            </button>
        </div>
    </form>
</div>
</body>
<script>
    function checkButtonsDisable() {
        let startDate = Date.parse(${examDto.startDate});
        let endDate = Date.parse(${examDto.endDate});
        const examStatus = "${examDto.status}";
        let now = new Date();
        if (startDate < now && endDate > now) {
            document.getElementById("add-question").disabled = true;
            document.getElementById("activate").disabled = true;
            document.getElementById("delete").disabled = true;
        }
        if (examStatus === "ACTIVE")
            document.getElementById("activate").disabled = true;
        else if (examStatus === "INACTIVE" || examStatus === "PENDING")
            document.getElementById("stop").disabled = true;
    }
</script>
</html>