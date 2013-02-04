<%@ page language="java" import="picturefarm.image.jdo.*,java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Welcome to Picture Farm</title>
</head>

<body>
<h1>Welcome to "Refresh and Change" Picture Farm!</h1>
<ul>
	<li>
	<h3>Uploaded "Refresh and Change" images</h3>
	</li>
	<%
	    String serverBase = "http://" + request.getServerName() + ":"
	            + request.getServerPort();
	    String[] groupNames = ImageManager.getAllImageNames();
	    for (int i = 0; i < groupNames.length; i++) {
	%>
	<a href="<%=serverBase%>/changingimages/<%=groupNames[i]%>.jpeg"><%=groupNames[i]%></a>
	<br>
	<%
	    }
	%>
	<li>
	<h3>Create a new "Refresh and Change" image (NOTE: Please do NOT
	upload your private pictures!)</h3>
	</li>
	<form name="UploadForm" enctype="multipart/form-data" method="post"
		action="/saveimagegroup">Image name:<br>
	<input type="text" name="groupName" value="imagenameis"></input><br>
	Upload image files (jpeg only):<br>
	<input type="file" name="File1" size="20" maxlength="20"> <br>
	<input type="file" name="File2" size="20" maxlength="20"> <br>
	<input type="file" name="File3" size="20" maxlength="20"> <br>
	<input type="file" name="File4" size="20" maxlength="20"> <br>
	<input type="file" name="File5" size="20" maxlength="20"> <br>
	<input type="submit" value="Upload and create">
</ul>
</form>
</body>
<%@ include file="/foot.jsp"%>
</html>
