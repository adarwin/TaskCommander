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

<table width="400" border="1">
      <tr>
        <th scope="col">Task Name</th>
        <th scope="col">Task Due Date</th>
        <th scope="col">Control</th>
      </tr>
      <form action="/TaskCommander/taskmanagement" method="get" name="FunctionForm">
      <tr>
      	<td><input name="newTaskName" type="text"></td>
        <td><input name="newTaskDueDate" type="text"></td>
        <td><input name="addTask" type="submit" value="Add Task"></td>
      </tr>
    </form>
      <adarwin:makeTaskRows user="${user}" taskNameKey="TASK_NAME" taskDueDateKey="TASK_DUE_DATE">
      <tr>
        <td>TASK_NAME</td>
        <td>TASK_DUE_DATE</td>
        <td>
          <form action="/TaskCommander/taskmanagement" method="get" name="deleteForm" onSubmit="eventstext">
          	<input name="taskName" type="hidden" value="TASK_NAME">
            <input name="deleteTask" type="submit" value="Delete">
          </form>
        </td>
      </adarwin:makeTaskRows>
    </table>
    
<form action="/TaskCommander/logout" method="post" name="LogoutForm">
        <input name="logout" type="submit" value="Logout">
    </form>
</body>
</html>