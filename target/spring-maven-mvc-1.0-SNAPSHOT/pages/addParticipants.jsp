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
        a {
            font-size: large;
        }

        a:active {
            color: blue;
        }

        a:link {
            color: red;
        }

        a:visited {

            color: #7f0e89;
        }

        a:hover {
            color: green;
        }

    </style>
</head>
<body>
<form style="margin: 0" action="${pageContext.request.contextPath}/admin">
    <button type="submit" id="dashboard" name="dashboard" class="btn btn-success btn-group"
            style="margin: 2vh 2vw">Dashboard
    </button>
</form>
<div class="main-block">
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
                <select id="role" name="role" class="dropdowns" required="required" onchange="checkRole()">
                    <option value="${roleName}">${roleName}</option>
                    <c:forEach items="${roles}" var="role">
                        <option value="${role}">${role}</option>
                    </c:forEach>
                </select>
                <label for="category" style="margin-right: 10px">Category:</label>
                <select id="category" name="category" class="dropdowns" required="required" onchange="findCourses()">
                    <option value="${categoryName}">${categoryName}</option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category}">${category}</option>
                    </c:forEach>
                </select>
                <label for="title" style="width:2vw">Title:</label>
                <select id="title" name="title" class="dropdowns" required="required"
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
                <tr>
                    <td><input id="name${i}" value="${user.name}" class="readOnly" readonly/></td>
                    <td><input id="family${i}" value="${user.family}" class="readOnly" readonly/></td>
                    <td><input id="email${i}" value="${user.email}" class="readOnly" readonly/></td>
                    <td>
                        <button class="btn btn-btn btn-primary btn-block save-btn" onclick="addToCourse('${i}')">
                            Add
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <c:choose>
        <c:when test="${totalPages < 3}">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <a id="page-less-than-three${i}" href=""
                   onclick="gotoDesiredPage(${i},'page-less-than-three${i}')">${i}</a>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <c:forEach var="i" begin="1" end="2">
                <a id="page-more-than-three${i}" href=""
                   onclick="gotoDesiredPage(${i},'page-more-than-three${i}')">${i}</a>
            </c:forEach>
            ...
            <a id="getLatPage" href="" onclick="gotoDesiredPage(${totalPages},'getLastPage')">${totalPages}</a>
        </c:otherwise>
    </c:choose>
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

    function addToCourse(i) {
        const request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                window.alert(this.response);
                getUsersList(${pageNumber});
            }
        };
        request.open("POST", "/admin/course/addParticipant/" + document.getElementById("title").value, true);
        request.setRequestHeader("Content-type", "application/json");
        request.dataType = "json";
        request.responseType = "text";
        let userDtoJson = createJsonObject(i);
        console.log(userDtoJson);
        let userDtoJsonString = JSON.stringify(userDtoJson);
        console.log(userDtoJsonString);
        request.send(userDtoJsonString);
    }

    function createJsonObject(i) {
        return {
            "name": document.getElementById("name" + i).value,
            "family": document.getElementById("family" + i).value,
            "email": document.getElementById("email" + i).value,
        };
    }
</script>
</html>