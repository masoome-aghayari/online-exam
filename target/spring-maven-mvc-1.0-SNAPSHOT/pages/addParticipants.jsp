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
    <title>add participants</title>
    <style type="text/css">
        <%@include file="styles/addCourseStyle.css" %>
        <%@include file="styles/tableStyle.css"%>
        <%@include file="styles/paginationStyle.css"%>
        .form-control {
            display: inline;
            width: 12vw;
        }
    </style>
</head>
<body>
<form style="margin: 0" action="${pageContext.request.contextPath}/admin">
    <button type="submit" id="dashboard" name="dashboard" class="btn btn-success btn-group"
            style="margin: 2vh 2vw">Dashboard
    </button>
</form>
<div class="main-block" style="margin-top: 10vh">
    <div>
        <p>${message}</p>
    </div>
    <form class="content" method="Get" id="search">
        <div class="header">
            <h3>Add Participant Form</h3>
        </div>
        <div class="formcontainer">
            <hr>
            <div class="container">
                <label for="role" style="width: 2vw">Role:</label>
                <select id="role" name="role" class="form-control" required="required" onchange="checkRole()">
                    <option value="${roleName}">${roleName}</option>
                    <c:forEach items="${roles}" var="role">
                        <option value="${role}">${role}</option>
                    </c:forEach>
                </select>
                <label for="category" style="margin-right: 10px; width: 4vw">Category:</label>
                <select id="category" name="category" class="form-control" required="required"
                        onchange="findCourses()">
                    <option value="${categoryName}">${categoryName}</option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category}">${category}</option>
                    </c:forEach>
                </select>
                <label for="title" style="width:2vw">Title:</label>
                <select id="title" name="title" class="form-control" required="required"
                        onchange="getUsersList('1')">
                    <option value="${courseTitle}">${courseTitle}</option>
                    <c:forEach items="${courseTitles}" var="title">
                        <option value="${title}">${title}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </form>
    <div class="resultTable">
        <table class="table table-hover table table-bordered">
            <thead>
            <tr>
                <th>Firstname</th>
                <th>LastName</th>
                <th>Email</th>
                <th>Add</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userDtos}" var="user" varStatus="i">
                <form:form modelAttribute="userDto"
                           action="/admin/course/addParticipant/${roleName}/${categoryName}/${courseTitle}/${pageNumber}">
                    <tr>
                        <td><form:input path="name" value="${user.name}" class="readOnly" readonly="true"/></td>
                        <td><form:input path="family" value="${user.family}" class="readOnly" readonly="true"/></td>
                        <td><form:input path="email" value="${user.email}" class="readOnly" readonly="true"/></td>
                        <td>
                            <button type="submit" class="btn btn-btn btn-primary btn-block save-btn">Add</button>
                        </td>
                    </tr>
                </form:form>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="pagination"></div>
</div>
</body>
<script>
    function checkRole() {
        document.forms[0].action = ("/admin/course/addParticipant/checkRole/" + document.getElementById("role").value);
        document.forms[0].submit();
    }

    function findCourses() {
        document.forms[0].action = "/admin/course/addParticipant/find-courses/" + document.getElementById("role").value +
            "/" + document.getElementById("category").value;
        document.forms[0].submit();
    }

    function getUsersList(pageNumber) {
        document.forms[0].action = "/admin/course/addParticipant/find-userList/" + document.getElementById("role").value +
            "/" + document.getElementById("category").value + "/" + document.getElementById("title").value + "/" + pageNumber;
        document.forms[0].submit();
    }

    function gotoDesiredPage(pageNumber, tagId) {
        document.getElementById(tagId).href = "/admin/course/addParticipant/find-userList/" + document.getElementById("role").value +
            "/" + document.getElementById("category").value + "/" + document.getElementById("title").value + "/" + pageNumber;
    }

    const pageNumber = parseInt(${pageNumber});
    const totalPages = parseInt(${totalPages});
    const notnull = Boolean(${userDtos!=null});
    if (notnull) {
        let begin, end;
        if ((totalPages <= 3)) {
            begin = 1;
            end = totalPages;
        } else if (pageNumber === 1) {
            begin = 1;
            end = 3;
        } else if ((pageNumber < totalPages - 1) && (pageNumber > 1)) {
            begin = pageNumber - 1;
            end = pageNumber + 1;
        } else if (pageNumber >= totalPages - 1) {
            begin = totalPages - 2;
            end = totalPages;
        }
        let previousPage = document.createElement('a');
        previousPage.id = "prev";
        let prevLink = document.createTextNode("«");
        previousPage.appendChild(prevLink);
        previousPage.href = "";
        previousPage.title = "«";
        $(previousPage).appendTo(".pagination");
        gotoDesiredPage(pageNumber - 1, previousPage.id);
        for (let i = begin; i <= end; i++) {
            let a = document.createElement('a');
            a.id = "page" + i;
            let link = document.createTextNode(i);
            a.appendChild(link);
            a.href = "";
            a.title = i;
            $(a).appendTo(".pagination");
            gotoDesiredPage(i, a.id);
        }
        let nextPage = document.createElement('a');
        nextPage.id = "next";
        let nextLink = document.createTextNode("»");
        nextPage.appendChild(nextLink);
        nextPage.href = "";
        nextPage.title = "»";
        $(nextPage).appendTo(".pagination");
        gotoDesiredPage(pageNumber + 1, nextPage.id);
    }
</script>
</html>