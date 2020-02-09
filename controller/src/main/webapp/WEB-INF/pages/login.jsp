<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Log In</h1>
        <form method="POST" action="/homework11_12/login">
            <input type="text" name="username" placeholder="username"/><p>${errors["userNotFound"]}</p><br/>
            <input type="password" name="password" placeholder="password"/><br/>
            <input type="submit" name="login" value="Log In"/><br/>
            <a href="<c:url value="/signin"/>"><input type="button" value="Sign In"/></a>
        </form>
    </body>
</html>
