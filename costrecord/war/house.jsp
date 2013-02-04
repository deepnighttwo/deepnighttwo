<%@ page language="java" import="costrecord.jdo.*,java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Cost Record System - House Management</title>
</head>
<body>
<%request.setAttribute("checkLogin", false); %>
<%@ include file="/header.jsp"%>

<h2>House Management</h2>
<ul>
<li><h3>Create House</h3></li>

<form action="/houseOperation" method="post">
<table>

	<tr>
		<td>House Name:</td>
		<td><input type="text" name="houseName"></input></td>
	</tr>
	<tr>
		<td>Password:</td>
		<td><input type="password" name="password"></input><input
			type="hidden" name="operation" value="addHouse"></input></td>

	</tr>
	<tr>
		<td>
		<div><input type="submit" value="Create House" /></div>
		</td>
	</tr>

</table>
</form>
</ul>
<%@ include file="/foot.jsp" %> 
</body>
</html>