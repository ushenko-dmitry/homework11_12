<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users Page</title>
    </head>
    <body>
        <h1>Users Page</h1>
        
        <table border="1">
            <tr>
                <td>id</td>
                <td>username</td>
                <td>created by</td>
                <td>role name</td>
                <td>role description</td>                    
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.createdBy}</td>
                    <td>${user.roleName}</td>
                    <td>${user.roleDescription}</td>                    
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
