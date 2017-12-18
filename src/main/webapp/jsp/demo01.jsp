<%@ page language="java" import="java.util.*" pageEncoding="utf-8"  contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script src="${pageContext.request.contextPath }/js/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/myJs.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.min.js"></script>
    <!-- <script type="text/javascript" src="${pageContext.request.contextPath }/myJs.js"></script>-->
    <title>demo01 page</title>
  </head>
  
  <body>
  	<div style="float:top">
   	<input type="button" value="显示条形图" onClick="getData()">
   	<input type="button" value="显示饼图" onClick="getPie()">
   	</div>
	<div id="main" style="width:500px;height:400px;float:left">
    </div>
	<div id="main2" style="width:450px;height:400px;float:left">
    </div>
   	
   	<script>
   		test();
   	</script>
	<script type="text/javascript">
		var jsonObj  ; 
		var myChart ;
		myChart = echarts.init(document.getElementById("main"));
		myChart.showLoading();
		function getData(){
			var nameValue =[];
			var value=[];
    		var xmlhttp = getXmlHttpRequest();
    		xmlhttp.onreadystatechange = function(){
    			if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
    				jsonObj =JSON.parse(xmlhttp.responseText);
    				alert(xmlhttp.responseText);
    				for(var i = 0 ;i<jsonObj.length ; i ++){
    					console.log('key='+jsonObj[i].key+'----value='+jsonObj[i].value);
				    	nameValue.push(jsonObj[i].key);
				    	value.push(jsonObj[i].value);
				    	myChart.hideLoading();
				    	 myChart.setOption({
				    	 		title: {
					                text: '树状图'
					            },
					            tooltip: {},
					            legend: {
					                data:['占有率']
					            },
						        xAxis: {
						        },
						        yAxis:{
						            data: nameValue
						        },
						        series: [{
						            // 根据名字对应到相应的系列
						            name: '占有率',
						            type: 'bar',
						            data: value
						        }]
						    });
						
			   			}
					};
					
    		};
    		xmlhttp.open("GET","${pageContext.request.contextPath }/avg");
    		xmlhttp.send(null);
    	};
    </script>
    
	<script type="text/javascript">
		var jsonObj  ; 
		var data =[];
		var myChart2 ;
		myChart2 = echarts.init(document.getElementById("main2"));
		myChart2.showLoading();
		function getPie(){
    		var xmlhttp = getXmlHttpRequest();
    		xmlhttp.onreadystatechange = function(){
    			if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
    				jsonObj = eval("("+xmlhttp.responseText+")");
    				for(var i = 0 ;i<jsonObj.length ; i ++){
				    	//name.push(jsonObj[i].name);
				    	//value.push(jsonObj[i].percent);
				    	data.push({value:jsonObj[i].percent,name:jsonObj[i].name});
				    	alert(data);
				    	myChart2.hideLoading();
				   		myChart2.setOption({
								series : [
						        	{
						            name: '访问来源',
						            type: 'pie',
						            radius: '55%',
						            data:data
						        	}
						    	]
						});	
			   		}
			   			
					};
					
    		};
    		xmlhttp.open("GET","${pageContext.request.contextPath}/demo01");
    		xmlhttp.send(null);
    	};

	
	</script>
	

  </body>
</html>
