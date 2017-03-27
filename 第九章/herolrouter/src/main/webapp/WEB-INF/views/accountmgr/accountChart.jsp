<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<h3>账号统计情况</h3>
		<ul>
			<li><h4>总人数：${sum }</h4></li>
			<c:forEach items="${channelValMap }" var="pay">
				<li><span class="channel">${pay.key }</span>: <span>${pay.value }</span></li>
			</c:forEach>
		</ul>
	</div>
	<!-- ECharts单文件引入 -->
	<script type="text/javascript">
		$('span[class=channel]').each(function() {
			$(this).html(replaceChannelName($(this).html()));
		});
		function replaceChannelName(name) {
			if (name == 0) {
				name = '无渠道';
			} else if (name == 1) {
				name = '小米';
			} else if (name == 2) {
				name = '360';
			} else if (name == 3) {
				name = '4399';
			} else if (name == 4) {
				name = '乐8';
			} else if (name == 5) {
				name = '泡椒）';
			} else if (name == 6) {
				name = '百度';
			} else if (name == 7) {
				name = '腾讯';
			} else if (name == 8) {
				name = 'XY助手';
			} else if (name == 9) {
				name = '海马';
			} else if (name == 10) {
				name = '快用';
			} else if (name == 11) {
				name = 'iOS正版';
			} else if (name == 12) {
				name = '三大运营商';
			} else if (name == 13) {
				name = 'uc';
			} else if (name == 14) {
				name = '金立';
			} else if (name == 15) {
				name = '联想';
			} else if (name == 16) {
				name = 'vivo';
			} else if (name == 17) {
				name = '华为';
			} else if (name == 18) {
				name = 'oppo';
			} else if (name == 19) {
				name = '当乐';
			} else if (name == 20) {
				name = '乐视';
			} else if (name == 21) {
				name = '拇指玩';
			} else if (name == 22) {
				name = '豌豆荚';
			} else if (name == 23) {
				name = '应用汇';
			} else if (name == 24) {
				name = '手游天下';
			} else if (name == 25) {
				name = '木蚂蚁';
			} else if (name == 26) {
				name = '悠悠村';
			}
			return name;
		}
		$
				.ajax({
					type : "GET",
					dataType : "text",
					url : "../accountmgr/accountChartHandle",
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
											name : '注册日期',
											type : 'category',
											data : ret.dateCharts
										} ],
										yAxis : [ {
											name : '人数',
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
										series : ret.accountChartDatas
									};
									// 为echarts对象加载数据 
									myChart.setOption(option);
								});
					},
				});
	</script>
</body>