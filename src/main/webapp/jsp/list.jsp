<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>list</title>
<script type="text/javascript"
	src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdn.bootcss.com/uikit/2.25.0/js/uikit.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.bootcss.com/uikit/2.25.0/css/uikit.min.css">
<link rel="stylesheet" type="text/css"
	href="https://cdn.bootcss.com/uikit/2.25.0/css/uikit.gradient.min.css">
</head>
<body style="margin: 5px 10px 5px 10px">

	<nav class="uk-navbar" style="margin-bottom: 5px">
		<ul class="uk-navbar-nav">
			<li><a href="${pageContext.request.contextPath }/index">index</a></li>
			<li><a href="${pageContext.request.contextPath }/list">list</a></li>
			<li><a href="${pageContext.request.contextPath }/contact">contact
					us</a></li>
		</ul>
	</nav>

	<div class="uk-panel-box">
		<div class="uk-panel-title">List</div>
		<div class="uk-grid">

			<c:forEach items="${cityList }" var="city">
				<div class="uk-width-1-6" style="margin: 8px 0px">
					<a href="${pageContext.request.contextPath }/detail?city=${city}">${city }</a>
				</div>

			</c:forEach>



		</div>
	</div>
<!--  
	<script>
		$(document)
				.ready(
						function() {
							$(".uk-grid")
									.append(
											'<div class="uk-width-1-10"><a href="${pageContext.request.contextPath }/detail?city=天津市">天津市</a></div>');

						})
	</script>
-->
</body>
</html>

