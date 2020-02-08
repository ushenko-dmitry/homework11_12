<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Roles Page</title>
    </head>
    <body>
        <h1>Roles Page</h1>

        <table border="1">
            <tr>
                <td>id</td>
                <td>name</td>
                <td>description</td>                    
            </tr>
            <c:forEach var="role" items="${roles}">
                <tr>
                    <td>${role.id}</td>
                    <td>${role.name}</td>
                    <td>${role.description}</td>                    
                </tr>
            </c:forEach>
        </table>
        <form method="POST" action="/homework11_12/logout">
            <input type="submit" value="log out"/>
        </form>
    </body>
</html>
