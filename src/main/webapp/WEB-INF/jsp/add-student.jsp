<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add Student</title>
    </head>
    <body>
        <c:if test="${addStudentSuccess}">
            <div>Successfully added Student with ID: ${savedStudent.id}</div>
        </c:if>

        <c:url var="add_Student_url" value="/Student/addStudent"/>
        <form:form action="${add_student_url}" method="post" modelAttribute="student">
            <form:label path="firstName">First Name: </form:label> <form:input type="text" path="firstName"/>
            <form:label path="lastName">Last Name: </form:label> <form:input type="text" path="lastName"/>
            <form:label path="contactNum">Parent/Guardian contact number: </form:label> <form:input path="contactNum"/>
            <input type="submit" value="submit"/>
        </form:form>
    </body>
</html>