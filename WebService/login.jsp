<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>

<body>
<h1>Login Page</h1>
<form action="/TaskCommander/login" method="post" enctype="application/x-www-form-urlencoded" name="LoginForm">
	<p>
	  Username:<input name="username" type="text">
  Try 'alex'</p>
	<p>
	  Password:<input name="password" type="password">
	Try 'gaming'</p>
    <p>
    	<input name="login" type="submit" value="Login">
        <input name="register" type="submit" value="Register">
        <input name="reset" type="reset" value="Reset">
  </p>
</form>
</body>
</html>