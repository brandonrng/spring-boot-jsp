<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>View Students</title>
<link href="<c:url value="/css/common.css"/>" rel="stylesheet"
	type="text/css">
</head>
<body>
	<h1>
		<jsp:include page="hello.jsp"></jsp:include>
	</h1>
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>first name</th>
				<th>last name</th>
				<th>parent/guardian contact number</th>
				<th>edit</th>
				<th>delete</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${student}" var="student">
				<tr>
					<td>${student.id}</td>
					<td>${student.firstName}</td>
					<td>${student.lastName}</td>
					<td>${student.contactNum}</td>

					<td><a
						href="http://localhost:8080/student/updateStudent/${student.id}">update</a></td>
					<td><a
						href="http://localhost:8080/student/deleteStudent/${student.id}">delete</a></td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<form action="/student/addStudent">
    	<input type="submit" value="add students" />
	</form>
</body>
</html>