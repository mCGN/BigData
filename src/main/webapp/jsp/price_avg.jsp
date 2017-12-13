<%@ page language="java" import="java.util.*" pageEncoding="utf-8"  contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script src="${pageContext.request.contextPath }/js/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
    <title>price avg</title>
  </head>
  
  <body>
  	<div style="float:top">
   	</div>
	<div id="main" style="width:500px;height:600px;float:left">
    </div>

    <script type="text/javascript">
    	$(document).ready(function(){
    		var jsonObj  ; 
			var myChart ;
			var nameValue =[];
			var value=[];
			myChart = echarts.init(document.getElementById("main"));
			myChart.showLoading();
			$.ajax({
				url:"${pageContext.request.contextPath }/avg",
				success:function(data){
					jsonObj =JSON.parse(data);
					for(var i = 0 ;i<jsonObj.length ; i ++){
						nameValue.push(jsonObj[i].key);
						value.push(jsonObj[i].value);
						myChart.hideLoading();
						myChart.setOption({
							title: {
								text: '全国各省均价'
							},
							tooltip: {},
							legend: {
								data:['均价']
							},
							xAxis: {
							},
							yAxis:{
								data: nameValue
							},
							series: [{
						            // 根据名字对应到相应的系列
						            name: '均价',
						            type: 'bar',
						            data: value
						        }]
						    });
						
					}
				}
			})
    	})
    </script>
	
  </body>
</html>
