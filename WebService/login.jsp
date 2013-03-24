<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>

<body background="/TaskCommander/img/WaterColorPaperBackground.jpg">
<div align="center">
<h2>TaskCommander Login</h2>
<form action="/TaskCommander/login" method="post" enctype="application/x-www-form-urlencoded" name="LoginForm">
	<p>
	  Username: <input name="username" type="text" required>
  </p>
	<p>
	  Password: <input name="password" type="password" required>
  </p>
    <p>
    	<input name="login" type="submit" value="Login">
        <input name="register" type="submit" value="Register">
        <input name="reset" type="reset" value="Reset">
  </p>
</form>
</div>
</body>
</html>