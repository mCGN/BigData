<%@ page language="java" import="java.util.*" pageEncoding="utf-8"
	contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript"
	src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdn.bootcss.com/uikit/2.25.0/js/uikit.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.bootcss.com/uikit/2.25.0/css/uikit.min.css">
<link rel="stylesheet" type="text/css"
	href="https://cdn.bootcss.com/uikit/2.25.0/css/uikit.gradient.min.css">
<script src="${pageContext.request.contextPath }/js/echarts.min.js"></script>
<title>price avg</title>
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
		<div id="main" style="width: 500px; height: 600px;">
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var jsonObj;
			var myChart;
			var nameValue = [];
			var value = [];
			myChart = echarts.init(document.getElementById("main"));
			myChart.showLoading();
			$.ajax({
				url : "${pageContext.request.contextPath }/data/avg",
				success : function(data) {
					jsonObj = JSON.parse(data);
					for (var i = 0; i < jsonObj.length; i++) {
						nameValue.push(jsonObj[i].key);
						value.push(jsonObj[i].value);
						myChart.hideLoading();
						myChart.setOption({
							title : {
								text : '全国各省均价'
							},
							tooltip : {},
							legend : {
								data : [ '均价' ]
							},
							xAxis : {},
							yAxis : {
								data : nameValue
							},
							series : [ {
								// 根据名字对应到相应的系列
								name : '均价',
								type : 'bar',
								data : value
							} ]
						});

					}
				}
			})
		})
	</script>

</body>
</html>
