<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>search course</title>
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
    <button type="submit" id="dashboard" name="dashboard" class="btn btn-success btn-group"
            style="margin: 2vh 2vw">Dashboard
    </button>
</form>
<form action="${pageContext.request.contextPath}/admin/course/searchProcess/1" method="get" class="searchform">
    <h1>Search Course</h1>
    <div class="formcontainer">
        <hr>
        <div class="container">
            <label for="category"><strong>Category:</strong></label>
            <select id="category" class="dropdown" name="category">
                <option value="${courseDto.category}">--</option>
                <c:forEach items="${categories}" var="category">
                    <option value="${category.name}">${category.name}</option>
                </c:forEach>
            </select>

            <label for="title"><strong>Title:</strong></label>
            <input id="title" value="${courseDto.title}" name="title"/>

            <label for="duration"><strong>Duration:</strong></label>
            <input type="number" id="duration" value="${courseDto.duration}" name="duration"/>

            <label for="capacity"><strong>Capacity:</strong></label>
            <input type="number" id="capacity" value="${courseDto.capacity}" name="capacity"/>

            <label for="startDate"><strong>Start Date:</strong></label>
            <input type="date" id="startDate" value="${courseDto.startDate}" name="startDate"/>

            <label for="endDate"><strong>End Date:</strong></label>
            <input type="date" id="endDate" value="${courseDto.endDate}" name="endDate"/>
            <button formaction="/admin/course/searchProcess/1">Search</button>
        </div>
    </div>
    <div style="text-align: center">
        <button id="previous" class="btn btn-primary btn-group"
                formaction="/admin/course/searchProcess/${pageNumber-1}">Previous
        </button>
        <button id="next" class="btn btn-primary btn-group" formaction="/admin/course/searchProcess/${pageNumber+1}">
            Next
        </button>
    </div>
</form>
<div class="resultTable">
    <table class="table table-hover table table-bordered">
        <thead>
        <tr>
            <th>Category</th>
            <th>Title</th>
            <th>Duration</th>
            <th>Capacity</th>
            <th>Start Date</th>
            <th>End Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${courses}" var="exam" varStatus="i">
            <tr>
                <td><input id="category${i}" class="readOnly" value="${exam.category}" readonly/></td>
                <td><input id="title${i}" class="readOnly" value="${exam.title}" readonly/></td>
                <td><input type="number" id="duration${i}" value="${exam.duration}"/></td>
                <td><input type="number" id="capacity${i}" value="${exam.capacity}"/></td>
                <td><input type="date" id="startDate${i}" value="${exam.startDate}"/></td>
                <td><input type="date" id="endDate${i}" value="${exam.endDate}"/></td>
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
        const pagesCount = parseInt(${totalPages});
        if (pageNumber >= pagesCount)
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
        request.open("POST", "/admin/course/saveChanges", true);
        request.setRequestHeader("Content-type", "application/json");
        request.dataType = "json";
        request.responseType = "text";
        let courseDtoJson = createJsonObject(i);
        console.log(courseDtoJson);
        let courseDtoJsonString = JSON.stringify(courseDtoJson);
        console.log(courseDtoJsonString);
        request.send(courseDtoJsonString);
    }

    function createJsonObject(i) {
        return {
            "category": document.getElementById("category" + i).value,
            "title": document.getElementById("title" + i).value,
            "duration": document.getElementById("duration" + i).value,
            "capacity": document.getElementById("capacity" + i).value,
            "startDate": document.getElementById("startDate" + i).value,
            "endDate": document.getElementById("endDate" + i).value
        };
    }
</script>
</html>