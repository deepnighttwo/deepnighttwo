<%@ page language="java" import="costrecord.jdo.*,java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />

<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Cost Record System - User Operation</title>
</head>
<body>
<%request.setAttribute("checkLogin", true); %>
<%@ include file="/header.jsp"%>

<h2>Role Management</h2>
<ul>

<%
	Long houseID = HouseManager.checkLogin(request, response);
	if ((new Long("-1")).equals(houseID)) {
		return;
	}
%>

<li><h3>Role List</h3></li>
<table width="300">
	<tr>
		<td style="font-weight: bold;">Role Name</td>
		<td style="font-weight: bold;">Operation</td>
	</tr>
	<%
		List<CostRecordRole> roles = RoleManager.getAllRoles(houseID, true);
		if (roles != null) {
			for (CostRecordRole role : roles) {
				if (!role.isActive()) {
					continue;
				}
				String aroleID = role.getId().toString();
				String aroleName = role.getUserName();
	%>
	<tr>
		<td><%=aroleName%></td>
		<td><a
			href="/roleOperation?operation=disablerole&roleID=<%=aroleID%>&roleName=<%=aroleName%>">Disable
		Role</a></td>
	</tr>
	<%
		}
		}
	%>
</table>
<li><h3>Create Role</h3></li>
<form action="/roleOperation" method="post">
<div>Role Name: <input type="text" name="roleName"></input> <input
	type="hidden" name="operation" value="addrole"></input> <input
	type="submit" value="Add Role" /></div>
</form>
</ul>
<%@ include file="/foot.jsp" %> 
</body>
</html>