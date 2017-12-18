<%@ page language="java" import="java.util.*" pageEncoding="utf-8"
	contentType="text/html; charset=utf-8"%>

<!DOCTYPE html>
<html>
<head>
	<title>detail</title>
	<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdn.bootcss.com/uikit/2.25.0/js/uikit.min.js"></script>
	<script src="${pageContext.request.contextPath }/js/echarts.min.js"></script>
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
		<div class="uk-panel-title">城市：${city}</div>
		<div id="main" style="width: 400px;height: 300px">
			
		</div>
		<div id="second" style="width: 500px;height: 300px">
			
		</div>
	</div>
	<div class="uk-panel-box">
		<div id="max"></div>
		<div id="min"></div>
		<div id="avg"></div>
	</div>

	<script type="text/javascript">
		//环形图，
		function ring(myChart,data1,data2) {
				var option={
					tooltip: {
				        trigger: 'item',
				        formatter: "{a} <br/>{b}: {c} ({d}%)"
				    },
					legend: {
						orient: 'vertical',
						x: 'left',
						data:data2
					},
					series : [
					{
						name:'Tag',
						type:'pie',
						radius: ['50%', '70%'],
						avoidLabelOverlap: false,
						label: {
							normal: {
								show: false,
								position: 'center'
							},
							emphasis: {
								show: true,
								textStyle: {
									fontSize: '20',
									fontWeight: 'bold'
								}
							}
						},
						labelLine: {
							normal: {
								show: false
							}
						},
						data:data1
					}
					]
				};
				myChart.setOption(option);
		}

		function bar(myChart,keyList,valueList) {
			myChart.setOption({
				title : {
					text : 'price grouping'
				},
				tooltip : {},
				legend : {
					data : [ 'price section' ]
				},
				xAxis : {},
				yAxis : {
					data : keyList
				},
				series : [ {
					// 根据名字对应到相应的系列
					name : 'price section',
					type : 'bar',
					data : valueList
				} ]
			});
		}

		function priceGrouping() {
			var myChart = echarts.init(document.getElementById('second'));
			myChart.showLoading();
			$.ajax({
				url:"${pageContext.request.contextPath }/data/pricegrouping?city=${city}",
				success : function(data) {
					var keyList=[];
					var valueList=[];
					var jsonObj = JSON.parse(data);
					for (var i = 0; i < jsonObj.length; i++) {
						keyList.push(jsonObj[i].key);
						valueList.push(jsonObj[i].value);
					}
					bar(myChart,keyList,valueList);
					myChart.hideLoading();
				},
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					alert('请求失败')
					myChart.hideLoading();
				}
			})
		}

		function getTagNum() {
			var tag="轨交房";
			var myChart = echarts.init(document.getElementById('main'));
			myChart.showLoading();
			$.ajax({
				url:"${pageContext.request.contextPath }/data/detail?city=${city}&tag="+tag,
				success:function(data){
					var jsonObj=JSON.parse(data);
					var data1=[];
					data1.push({value:jsonObj.count,name:tag});
					var other=jsonObj.all-jsonObj.count;
					data1.push({value:other,name:'other'});
					var data2=[tag,'other']

					ring(myChart,data1,data2);
					myChart.hideLoading();
				},
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					alert('请求失败')
				}
			})
		}
		
		function maxandmin(){
			$.ajax({
				url:"${pageContext.request.contextPath }/data/maxandmin?city=${city}",
				success:function(data){
					var jsonObj=JSON.parse(data);
					$("#max").text('Max:'+jsonObj.max);
					$("#min").text('Min:'+jsonObj.min);
				}
			})
		}
		
		function getAvg(){
			$.ajax({
				url:"${pageContext.request.contextPath }/data/cityavg?city=${city}",
				success:function(data){
					var jsonObj=JSON.parse(data);
					$("#avg").text('AVG:'+jsonObj.avg);
					
				}
			})
		}

		$(document).ready(function(){
			getTagNum();
			priceGrouping();
			maxandmin();
			getAvg();
		})

	</script>

	

</body>
</html>
	
