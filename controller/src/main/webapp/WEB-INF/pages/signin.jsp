<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign in Page</title>
    </head>
    <body>
        <h1>Sign In</h1>
        
        <form method="POST" action="/homework11_12/signin">
            <input type="text" name="username" placeholder="username"/><p>${errors["usernameLength"]}</p><p>${errors["usernameSymbols"]}</p><br/>
            <input type="password" name="password" placeholder="password"/><p>${errors["passwordLength"]}</p><br/>
            <select name="roleId">
                <c:forEach var="role" items="${roles}">
                    <option value="${role.id}">${role.name}</option>
                </c:forEach>
            </select>
            <p>${errors["role"]}</p>
            <br/>
            <input type="submit" name="signin" value="Sign In"/><br/>
            <a href="<c:url value="/login"/>"><input type="button" value="Log In"/></a>
        </form>
    </body>
</html>
