<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <title>all courses</title>
    <style type="text/css">
        <%@include file="styles/tableStyle.css"%>
        .next-prev {
            text-align: center;
        }
        .save{
            background-color: #ff3a4d;
            border: none;
            margin-top:0;
        }

        .readOnly {
            border: none;
            background: none;
        }
    </style>
</head>
<body onload="checkPageNumber()">
<button id="dashboard" name="dashboard" class="btn btn-success btn-group"
        formaction="/admin" formmethod="get" style="margin: 2vh 2vw">Dashboard
</button>
<div class="next-prev">
    <form class="btnNav" style="display: inline" method="get">
        <button id="previous" name="previous" class="btn btn-primary btn-group" onclick="gotoPreviousPage()">Previous
        </button>
        <button id="next" name="next" class="btn btn-primary btn-group" onclick="gotoNextPage()">Next</button>
    </form>
</div>
<div class="resultTable">
    <table class="table table-hover table table-striped table-bordered">
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
        <c:forEach items="${courses}" var="exam">
            <form:form action="${pageContext.request.contextPath}/admin/course/saveChanges/${pageNumber}"
                       modelAttribute="course" method="Post" class="content">
                <tr>
                    <td><form:input path="category" value="${exam.category}"/></td>
                    <td><form:input path="title" value="${exam.title}" cssClass="readOnly" readonly="true"/></td>
                    <td><form:input path="duration" value="${exam.duration}"/></td>
                    <td><form:input path="capacity" value="${exam.capacity}"/></td>
                    <td><form:input path="startDate" value="${exam.startDate}"/></td>
                    <td><form:input path="endDate" value="${exam.endDate}"/></td>
                    <td><form:button cssClass="btn btn-btn btn-success btn-block save">Save Changes</form:button></td>
                </tr>
            </form:form>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script>
    function gotoNextPage() {
        const pageNumber = parseInt(${pageNumber});
        const pagesCount = parseInt(${totalPages});
        if (pageNumber < pagesCount) {
            document.forms[0].action = "/admin/course/all/" + (pageNumber + 1);
            document.forms[0].submit();
        } else
            return false;
    }

    function gotoPreviousPage() {
        const pageNumber = parseInt(${pageNumber});
        if (pageNumber > 1) {
            document.forms[0].action = "/admin/course/all/" + (pageNumber - 1);
            document.forms[0].submit();
        } else
            return false;
    }

    function checkPageNumber() {
        const pageNumber = parseInt(${pageNumber});
        const totalPages = parseInt(${totalPages});
        if (pageNumber >= totalPages)
            document.getElementById("next").disabled = true;
        if (pageNumber === 1)
            document.getElementById("previous").disabled = true;
    }
</script>
</html>