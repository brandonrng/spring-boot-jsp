<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>View Books</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Parent/Guardian Contact Number</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${student}" var="student">
                    <tr>
                    	<td>${student.id}</td>
                        <td>${student.firstName}</td>
                        <td>${student.lastName}</td>
                        <td>${student.contactNum}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>