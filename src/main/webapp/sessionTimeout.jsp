<%-- 
    Document   : sessionTimeout
    Created on : 2016-7-13, 15:29:25
    Author     : Jayang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World! <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /></h1>
    </body>
</html>
