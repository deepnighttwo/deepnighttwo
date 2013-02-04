
<%@ page language="java" import="costrecord.jdo.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<h1>Cost Record System</h1>

<table>
	<tr>
		<td>|&nbsp;<a href="/">Index</a>&nbsp;</td>
		<td>|&nbsp;<a href="/house.jsp">House Management</a>&nbsp;</td>
		<td>|&nbsp;<a href="/roleOperation">Role Management</a> &nbsp;</td>
		<td>|&nbsp;<a href="/costOperation">Cost Record	Management</a> &nbsp;</td>
		<td>|&nbsp;<a href="/about.jsp">About &amp; Help</a>&nbsp;|</td>
		<%
			House house = (House) session.getAttribute("house");
			if (house != null) {
		%>
		<td>&nbsp;Logined as <b><%=house.getHouseName()%></b>&nbsp;</td>
		<%
			}
		%>
	</tr>
</table>
<%
	String message = (String) session.getAttribute("message");
	if (message != null && message.trim().length() > 0) {
%>
<h2>Message Board</h2>
<div style="background-color: #EEEEEE;"><%=message%></div>
<%
	session.removeAttribute("message");
	}
%>

<%
boolean checkLogin = Boolean.TRUE.equals(request.getAttribute("checkLogin"));
if (house == null && checkLogin) {
%>
<h2>Login with a house account</h2>
<form action="/houseOperation" method="post">
<table>

	<tr>
		<td>House Name:</td>
		<td><input type="text" name="houseName"></input></td>
	</tr>
	<tr>
		<td>Password:</td>
		<td><input type="password" name="password"></input><input
			type="hidden" name="operation" value="login"></input></td>

	</tr>
	<tr>
		<td><input type="submit" value="Login" /></td>
		<td>or click <a href="/house.jsp">here</a> to create a house</td>
	</tr>

</table>
</form>
<%@ include file="/foot.jsp" %> 
<%
		return;
	}
%>