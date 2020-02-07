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
        <form method="POST" action="/signin">
            <input type="text" name="username" placeholder="username"/><br/>
            <input type="password" name="password" placeholder="password"/><br/>
            <input type="submit" name="signin" value="Sign In"/><br/>
            <a href="<c:url value="/login"/>">Log In</a>
        </form>
    </body>
</html>
