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
        <%@include file="styles/paginationStyle.css"%>
    </style>
</head>
<body>
<form action="/admin" style="margin: 0">
    <button type="submit" id="dashboard" class="btn btn-success btn-group" style="margin: 2vh 2vw">Dashboard</button>
</form>
<div class="main-block" style="margin-top: 10vh; text-align: center;">
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
                        <td><form:input path="name" value="${user.name}" pattern="^[a-zA-Z ]*$"
                                        title="just alphabets are allowed" required="required"/></td>
                        <td><form:input path="family" value="${user.family}" pattern="^[a-zA-Z ]*$"
                                        title="just alphabets are allowed" required="required"/></td>
                        <td><form:input path="email" value="${user.email}" cssClass="readOnly" readonly="true"/></td>
                        <td>
                            <form:select path="role" id="role" name="role" class="dropdowns" required="required">
                                <form:option value="${user.role}">${user.role}</form:option>
                                <c:forEach items="${roles}" var="role">
                                    <option value="${role}">${role}</option>
                                </c:forEach>
                            </form:select>
                        </td>
                        <td>
                            <button class="btn btn-btn btn-success btn-block confirm">Confirm</button>
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
    function gotoDesiredPage(pageNumber, tagId) {
        document.getElementById(tagId).href = "/admin/pending-list/" + pageNumber;
    }

    const pageNumber = parseInt(${pageNumber});
    const totalPages = parseInt(${totalPages});
    const notnull = Boolean(${users!=null});
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