<%@ page language="java" import="com.googlecode.flickr2twitter.utils.*"%>
<%@ page isErrorPage="true" %>   

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="stylesheets/site.css" rel="stylesheet" type="text/css" />
<link href="stylesheets/style.css" rel="stylesheet" type="text/css" />
<link href="stylesheets/content.css" rel="stylesheet" type="text/css" />
<title>SocialHub</title>
</head>

<body>
<div id="container">
	<%@ include file="header.jsp"%>
	<div id="content">
		<div id="left">
		<p><img src="/images/error.png" alt="center" ></img></p>
		<h1>Oops...</h1>
		<p>Got the following error:</p>
		<p>
		<%= exception.getMessage() %>
		</p>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</div>
</body>
</html>