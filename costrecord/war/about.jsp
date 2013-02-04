<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>About &amp; Help</title>
</head>
<body>
<%request.setAttribute("checkLogin", false); %>
<%@ include file="/header.jsp"%>

<h1>About &amp; Help</h1>


This is a simple web application based on Google&reg; App Engine.
<br>
You can use this web site to record and count the public cost. The
usage is simple:
<br>

<ul>
<li>Create a house if not yet. A house is the basic unit public cost counting.</li>
<li>Login using a house account.</li>
<li>Create roles for each roommate.</li>
<li>Create a cost record for each cost.</li>
<li>Archive cost record.</li>
</ul>
<%@ include file="/foot.jsp" %>
</body>
</html>