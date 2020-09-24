<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>search user</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style type="text/css">
        <%@include file="styles/searchStyle.css"%>
        .save-btn {
            background-color: #ffbb3a;
            border: none;
            margin-top: 0;
        }

        input {
            overflow: visible;
            height: 4vh;
            border-radius: 4px;
            width: 12vw;
            border: 1px solid skyblue;
        }
    </style>
</head>
<body onload="checkPageNumber()">
<form action="${pageContext.request.contextPath}/admin">
    <button type="submit" id="dashboard" class="btn btn-success btn-group"
            style="margin: 2vh 2vw">Dashboard
    </button>
</form>
<form method="get" class="searchform">
    <h1>Search User</h1>
    <div class="formcontainer">
        <hr>
        <div class="container">
            <label for="name"><strong>FirstName:</strong></label>
            <input id="name" value="${userDto.name}" name="name"/>

            <label for="family"><strong>LastName:</strong></label>
            <input id="family" value="${userDto.family}" name="family"/>

            <label for="role"><strong>Role:</strong></label>
            <select id="role" class="dropdown" name="role">
                <option value="${userDto.role}">--</option>
                <c:forEach items="${roles}" var="role">
                    <option value="${role}">${role}</option>
                </c:forEach>
            </select>

            <label for="email"><strong>Email:</strong></label>
            <input id="email" value="${userDto.email}" name="email" style="width: 25vw"/>
            <button formaction="/admin/searchProcess/1">Search</button>
        </div>
    </div>
    <div style="text-align: center">
        <button id="previous" class="btn btn-primary btn-group" formaction="/admin/searchProcess/${pageNumber-1}">
            Previous
        </button>
        <button id="next" class="btn btn-primary btn-group" formaction="/admin/searchProcess/${pageNumber+1}">Next
        </button>
    </div>
</form>
<div class="resultTable">
    <table class="table table-hover table table-bordered">
        <thead>
        <tr>
            <th>Firstname</th>
            <th>LastName</th>
            <th>Email</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user" varStatus="i">
            <tr>
                <td><input id="name${i}" value="${user.name}"/></td>
                <td><input id="family${i}" value="${user.family}"/></td>
                <td><input id="email${i}" class="readOnly" value="${user.email}" readonly/></td>
                <td><input id="role${i}" value="${user.role}"/></td>
                <td>
                    <button class="btn btn-btn btn-success btn-block save-btn" onclick="saveChanges('${i}')">
                        Save Changes
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script>
    function checkPageNumber() {
        const pageNumber = parseInt(${pageNumber});
        const totalPages = parseInt(${totalPages});
        if (pageNumber >= totalPages)
            document.getElementById("next").disabled = true;
        if (pageNumber === 1)
            document.getElementById("previous").disabled = true;
    }

    function saveChanges(i) {
        const request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                window.alert(this.response);
            }
        };
        request.open("POST", "/admin/saveChanges", true);
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
            "role": document.getElementById("role" + i).value
        };
    }

    function gotoDesiredPage(pageNumber, tagId) {
        document.getElementById(tagId).href = "/admin/course/addParticipant/find-userList/" + document.getElementById("role").value +
            "/" + document.getElementById("category").value + "/" + document.getElementById("title").value + "/" + pageNumber;
    }

</script>
</html>