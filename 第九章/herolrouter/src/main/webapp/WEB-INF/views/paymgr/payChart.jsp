<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title>ECharts</title>
<style type="text/css">
#summary {
	font-family: "微软雅黑";
	padding-left: 80px;
	padding-top: 30px;
	padding-bottom: 50px;
}

#summary ul li {
	margin: 10px;
}

#main {
	padding-top: 50px
}
</style>
</head>
<body>
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<div id="main" style="width: 95%; height: 80%"></div>
	<div id="summary">
		<h3>支付统计情况</h3>
		<ul>
			<li><h4>
					总金额（元）：
					<fmt:formatNumber type="number" value="${sum }"
						maxFractionDigits="0" />
				</h4></li>
			<c:forEach items="${channelValMap }" var="pay">
				<li><span class="channel">${pay.key }</span>: <span><fmt:formatNumber
							type="number" value="${pay.value }" maxFractionDigits="0" /></span></li>
			</c:forEach>
		</ul>
	</div>
	<!-- ECharts单文件引入 -->
	<script type="text/javascript">
		$('span[class=channel]').each(function() {
			$(this).html(replaceChannelName($(this).html()));
		});
		function replaceChannelName(name) {
			if (name == 'ios-haima') {
				name = '海马玩（iOS）';
			} else if (name == 'android-haima') {
				name = '海马玩（安卓）';
			} else if (name == 'android-xiaomi') {
				name = '小米（安卓）';
			} else if (name == 'ios-xy') {
				name = 'xy（iOS）';
			} else if (name == 'ios-kuaiyong') {
				name = '快用（iOS）';
			} else if (name == 'android-360') {
				name = '360（安卓）';
			} else if (name == 'android-tecent') {
				name = '腾讯';
			} else if (name == 'android-uc') {
				name = 'UC';
			} else if (name == 'android-liantong') {
				name = '联通';
			} else if (name == 'ios-aisi') {
				name = '爱思';
			} else if (name == 'ios-le8') {
				name = '乐8';
			} else if (name == 'android-jidi') {
				name = '基地';
			}
			return name;
		}
		$
				.ajax({
					type : "GET",
					dataType : "text",
					url : "../paymgr/payChartHandle",
					success : function(data) {
						var ret = JSON.parse(data);
						// 替换渠道名
						for ( var tmp in ret.channelCharts) {
							ret.channelCharts[tmp] = replaceChannelName(ret.channelCharts[tmp]);
						}
						for ( var tmp in ret.payChartDatas) {
							ret.payChartDatas[tmp].name = replaceChannelName(ret.payChartDatas[tmp].name);
						}
						// 路径配置
						require.config({
							paths : {
								echarts : 'http://echarts.baidu.com/build/dist'
							}
						});
						// 使用
						require(
								[ 'echarts', 'echarts/chart/bar',
										'echarts/chart/line',
										'echarts/component/dataZoom' // 使用柱状图就加载bar模块，按需加载
								],
								function(ec) {
									// 基于准备好的dom，初始化echarts图表
									var myChart = ec.init(document
											.getElementById('main'));
									var option = {
										tooltip : {
											trigger : 'item'
										},
										toolbox : {
											show : true,
											feature : {
												mark : {
													show : true
												},
												dataView : {
													show : true,
													readOnly : false
												},
												dataZoom : {
													show : true,
												},
												magicType : {
													show : true,
													type : [ 'line', 'bar',
															'stack', 'tiled' ]
												},
												restore : {
													show : true
												},
												saveAsImage : {
													show : true
												}
											}
										},
										calculable : true,
										grid : {
											top : '12%',
											left : '1%',
											right : '10%',
											containLabel : true
										},
										legend : {
											data : ret.channelCharts
										},
										xAxis : [ {
											name : '支付日期',
											type : 'category',
											data : ret.dateCharts
										} ],
										yAxis : [ {
											name : '金额(元)',
											type : 'value'
										} ],
										dataZoom : {
											type : 'inside',
											show : true,
											realtime : true,
											y : 36,
											height : 20,
											backgroundColor : 'rgba(221,160,221,0.5)',
											dataBackgroundColor : 'rgba(138,43,226,0.5)',
											fillerColor : 'rgba(38,143,26,0.6)',
											handleColor : 'rgba(128,43,16,0.8)',
											start : 20,
											end : 80
										},
										series : ret.payChartDatas
									};
									// 为echarts对象加载数据 
									myChart.setOption(option);
								});
					},
				});
	</script>
</body>