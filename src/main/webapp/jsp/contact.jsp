<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>for us</title>
	<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdn.bootcss.com/uikit/2.25.0/js/uikit.min.js"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.bootcss.com/uikit/2.25.0/css/uikit.min.css">
	<link rel="stylesheet" type="text/css" href="https://cdn.bootcss.com/uikit/2.25.0/css/uikit.gradient.min.css">
</head>
<body style="margin: 5px 10px 5px 10px">

	<nav class="uk-navbar" style="margin-bottom: 5px">
		<ul class="uk-navbar-nav">
			<li><a href="${pageContext.request.contextPath }/index">index</a></li>
			<li><a href="${pageContext.request.contextPath }/list">list</a></li>
			<li><a href="${pageContext.request.contextPath }/contact">contact us</a></li>
		</ul>
	</nav>

	<div class="uk-panel-box">
		<div class="uk-panel-title" >contact us</div>
		<div style="margin-bottom: 10px">
			e-mail:ac233@163.com
		</div>
		<div style="margin-bottom: 10px">
			phone:1234567890
		</div>
		<div style="margin-bottom: 10px">
			address:xx市xx路xxxx号
		</div>
	</div>

</body>
</html>