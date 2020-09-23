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
    <title>test question</title>
    <style type="text/css">
        <%@include file="styles/addCourseStyle.css"%>
        <%@include file="styles/createQuestionStyle.css"%>
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/teacher">
    <button type="submit" class="btn btn-success btn-group" style="margin: 2vh 2vw">Dashboard</button>
</form>
<div class="main-block">
    <div style="color: red"><strong>${message}</strong></div>
    <form:form modelAttribute="questionDto" cssClass="content" action="/teacher/exam/add-question/new/process">
        <form:hidden path="type"/>
        <div class="header">
            <h4>Create New Test Question</h4>
            <hr>
        </div>
        <div class="form-group">
            <form:label path="text" cssClass="odd-labels">Text:</form:label>
            <form:textarea path="text" value="${questionDto.text}" name="text" cssClass="form-control"
                        cssStyle="width: 38.3vw; height: 15vh" required="true"></form:textarea>
        </div>
        <div class="form-group">
            <form:label path="title" cssClass="odd-labels">Title:</form:label>
            <form:input path="title" value="${questionDto.title}" name="title" cssClass="form-control"
                        required="true"></form:input>

            <label for="mark" class="even-labels">Mark:</label>
            <input type="number" id="mark" name="mark" style="width: 5vw" class="form-control" required/>

            <form:label path="rightAnswerIndex" cssClass="even-labels">Right Answer Index:</form:label>
            <form:input type="number" path="rightAnswerIndex" id="rightAnswerIndex" name="rightAnswerIndex"
                        cssClass="form-control" cssStyle="width: 5vw" required="true"></form:input>
        </div>
        <div class="add-option-add-to-bank">
            <button onclick="appendOption()" class="btn btn-primary btn-block op-btn">New Option</button>
            <div class="form-check mb-2" style="margin-top: 2vh">
                <form:checkbox path="addToBank" class="form-check-input filled-in" id="addToBank" name="addToBank"
                              cssStyle="width: 25px; height: 25px" value="${!questionDto.addToBank}"/>
                <label class="form-check-label" for="addToBank">Add Question To Bank</label>
            </div>
        </div>
        <div class="option-container"></div>
        <button type="submit" class="btn btn-primary btn-block add-btn"
                onclick="return inputsValidation()">Add Question
        </button>
    </form:form>
</div>
</body>
<script>
    let count = 0;

    function appendOption() {
        ++count;
        const option = "<div class='options' id ='option" + count + "'><input name='option' class='form-control option-input' required><button id='" + count +
            "' class='btn btn-danger btn-block remove' onclick='removeOption(" + count + ")'>remove</button><div>";
        $(option).appendTo(".option-container");
    }

    function removeOption(id) {
        document.getElementById(id).remove();
        document.getElementById("option" + id).remove();
    }


    function inputsValidation() {
        const rightAnswerIndex = document.getElementById("rightAnswerIndex").value;
        const numberOfOptions = document.getElementsByClassName("options");
        const mark = document.getElementById("mark").value;
        console.log("rightAnswerIndex = " + rightAnswerIndex);
        console.log("numberOfOptions = " + numberOfOptions.length);
        console.log("mark = " + mark);
        if (numberOfOptions.length < 2) {
            window.alert("More than one option is needed for test questions");
            return false;
        }
        if (rightAnswerIndex > numberOfOptions.length) {
            window.alert("right answer index must be in bound of number of options!");
            return false;
        } else if (rightAnswerIndex <= 0) {
            window.alert("right answer index must be positive!");
            return false;
        }
        if (mark <= 0) {
            window.alert("mark must be positive");
            return false;
        }
        let i;
        let j;
        for (i = 0; i < numberOfOptions.length; i++) {
            for (j = i; j < numberOfOptions.length; j++) {
                console.log(numberOfOptions.item(i).getElementsByClassName("option-input").item(0).value);
                console.log(numberOfOptions.item(j).getElementsByClassName("option-input").item(0).value);
                if ((numberOfOptions.item(i).getElementsByClassName("option-input").item(0).value ===
                    numberOfOptions.item(j).getElementsByClassName("option-input").item(0).value) && (i !== j)) {
                    window.alert("duplicate option value");
                    return false;
                }
            }
        }
        return true;
    }
</script>
</html>
