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
    <title>pending users List</title>
    <style type="text/css">
        <%@include file="styles/tableStyle.css"%>
        <%@include file="styles/showPendingStyle.css"%>
    </style>
</head>
<body onload="checkPageNumber()">
<form action="/admin" style="margin: 0">
    <button type="submit" id="dashboard" class="btn btn-success btn-group"
            style="margin: 2vh 2vw">Dashboard
    </button>
</form>
<div class="next-prev">
    <form class="btnNav" style="display: inline" method="get">
        <button id="previous" name="previous" class="btn btn-primary btn-group"
                formaction="/admin/pending-list/${pageNumber-1}">Previous
        </button>
        <button id="next" name="next" class="btn btn-primary btn-group"
                formaction="/admin/pending-list/${pageNumber+1}">Next
        </button>
    </form>
</div>
<div class="resultTable">
    <table class="table table-hover table table-striped table-bordered">
        <thead>
        <tr>
            <th>Firstname</th>
            <th>LastName</th>
            <th>Email</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <form:form action="${pageContext.request.contextPath}/admin/confirm-user/${pageNumber}"
                       modelAttribute="userDto" method="Get" class="content">
                <tr>
                    <td><form:input path="name" value="${user.name}"/></td>
                    <td><form:input path="family" value="${user.family}"/></td>
                    <td><form:input path="email" cssClass="readOnly" value="${user.email}" readonly="true"/></td>
                    <td><form:input path="role" value="${user.role}"/></td>
                    <td>
                        <button class="btn btn-btn btn-success btn-block confirm">Confirm</button>
                    </td>
                </tr>
            </form:form>
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
</script>
</html>