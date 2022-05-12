<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Update Student</title>
    </head>
    <body>

        <c:url var="update_student_url" value="/student/updateStudent/{id}"/>
        <form:form action="${update_student_url}" method="post" modelAttribute="student">
            <form:label path="id"></form:label> <form:input type="hidden" path="id"/>
            <form:label path="firstName">Update First Name: </form:label> <form:input type="text" path="firstName"/>
            <form:label path="lastName">Update Last Name: </form:label> <form:input type="text" path="lastName"/>
            <form:label path="contactNum">Update Parent/Guardian contact number: </form:label> <form:input path="contactNum"/>
            <input type="submit" value="submit"/>
        </form:form>
    </body>
</html>