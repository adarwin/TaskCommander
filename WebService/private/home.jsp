<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/WEB-INF/tlds/CustomTags.tld" prefix="adarwin" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>

<body>
    <h1>Welcome home, ${user.username}</h1>
    <form action="/TaskCommander/taskmanagement" method="post" name="FunctionForm">
        <input name="addTask" type="submit" value="Add Task">
    </form>
    <form action="/TaskCommander/logout" method="post" name="LogoutForm">
        <input name="logout" type="submit" value="Logout">
    </form>
    <table width="400" border="1">
      <tr>
        <th scope="col">Task Name</th>
        <th scope="col">Task Due Date</th>
      </tr>
      <adarwin:makeTaskRows user="${user}" taskNameKey="TASK_NAME" taskDueDateKey="TASK_DUE_DATE">
      <tr>
        <td>TASK_NAME</td>
        <td>TASK_DUE_DATE</td>
        <td>
          <form action="/TaskCommander/taskmanagement" method="post" name="deleteForm" onSubmit="eventstext">
            <input name="tempname" type="submit" value="Delete">
            </form>
        </td>
      </adarwin:makeTaskRows>
    </table>

</body>
</html>