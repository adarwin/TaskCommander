<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
<meta http-equiv="refresh" content="2; URL=/TaskCommander/login">
</head>

<body background="/TaskCommander/img/WaterColorPaperBackground.jpg">
<h1>Thank you for registering, <%= request.getParameter("username") %></h1>
<p>If your browser does not automatically redirect you, please head on <a href="/TaskCommander" tabindex="1">home</a>.</p>
</body>
</html>