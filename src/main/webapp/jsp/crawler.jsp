<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<title>anjuke crawler</title>
</head>
<body>
	<div>
		<h1>anjuke crawler</h1>
		<button id="start" >start</button><br>
		<button id="stop" >stop</button><br>
		<div id="tip"></div>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$('#start').click(function () {
				$.ajax({
					url:"${pageContext.request.contextPath }/crawler/start",
					success:function(data){
						if(data=='ok'){
							$("#tip").text('crawler is running!')
						}else{
							$("#tip").text('error!')
						}
					}
				})
			})
			
			$('#stop').click(function () {
				$.ajax({
					url:"${pageContext.request.contextPath }/crawler/stop",
					success:function(data){
						if(data=='ok'){
							$("#tip").text('crawler is stop!')
						}else{
							$("#tip").text('error!')
						}
					}
				})
			})
			
		})
	</script>

</body>
</html>