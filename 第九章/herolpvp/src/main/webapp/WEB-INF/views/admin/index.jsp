<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>逻辑服务器后台</title>
<script>
	//整体返回
	function parentonlick() {
		location.href = "login";
	}
	function skip(path) {
		$('#right').load(path);
	}
</script>
<link rel="stylesheet" href="../css/admin/style.css">
<link type="text/css" rel="stylesheet" href="../css/admin/admin.css" />
<!-- 图标CSS 然后css调用fonts目录下的文件 -->
<link type="text/css" rel="stylesheet"
	href="../css/font-awesome.min.css" />

<link type="text/css" rel="stylesheet" href="../css/tablestyle.css" />
<link type="text/css" rel="stylesheet" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/tips.css" />
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../js/admin/menu.js"></script>
<style type="text/css">
body {
	font-family: "微软雅黑";
}
</style>
</head>
<body>
	<div class="top">
		<!-- <div class="logo"></div>
		<div class="title"></div> -->
		<br />
		<center>
			<h2>逻辑服务器后台</h2>
		</center>
	</div>
	<div class="main">
		<div class="left">
			<ul class="page-sidebar-menu">
				<li><a href="#"><i class="fa fa-table"></i><span>活动管理</span><span
						class="arrow"></span></a>
					<ul class="sub-menu">
						<li><a href="#"
							onclick="javascript:skip('../activity/award')">发送邮件</a></li>
						<li><a href="#"
							onclick="javascript:skip('../activity/query')">邮件查询</a></li>
					</ul></li>
				<li><a href="#"><i class="fa fa-table"></i><span>数据管理</span><span
						class="arrow"></span></a>
					<ul class="sub-menu">
						<li><a href="#" onclick="javascript:skip('../datas/query')">玩家数据</a></li>
						<li><a href="#" onclick="javascript:skip('../datas/pkrank')">竞技场排行榜</a></li>
					</ul>
				<li><a href="#"><i class="fa fa-table"></i><span>游戏数值</span><span
						class="arrow"></span></a>
					<ul class="sub-menu">
						<li><a href="#" onclick="javascript:skip('../csv/csvlist')">CSV数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/bagcardsp')">卡牌碎片合成数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/bagequipsp')">装备碎片合成数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/hczsbj')">皇城征收暴击数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/starproperty')">星级成功率数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/qualityproperty')">品质成功率数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/upgradestar')">卡牌升星消耗数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/trainopen')">训练队列开放条件数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/traincondition')">训练数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/workeropen')">工人队列开放数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/dailyactiveaward')">每日活跃度奖励数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/decomposejianghun')">卡牌分解将魂数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/decomposegailv')">卡牌分解概率数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/decomposeitem')">卡牌分解道具包数值</a></li>
						<li><a href="#"
							onclick="javascript:skip('../gamedata/decomposeaward')">卡牌分解次数奖励数值</a></li>
					</ul></li>
				<li><a href="#"><i class="fa fa-table"></i><span>日志管理</span><span
						class="arrow"></span></a>
					<ul class="sub-menu">
						<li><a href="#" onclick="javascript:skip('../log/logConfig')">日志配置</a></li>
					</ul></li>
				<li><a href="#"><i class="fa fa-table"></i><span>服务器</span><span
						class="arrow"></span></a>
					<ul class="sub-menu">
						<li><a href="http://123.59.139.220/herouter/admin/index"
							target="_blank">管理服务器</a></li>
						<li><a href="http://123.59.110.201:8093/file/file/hotfix"
							target="_blank">文件服务器</a></li>
					</ul></li>
				<li><a href="#"><i class="fa fa-table"></i><span>权限管理</span><span
						class="arrow"></span></a>
					<ul class="sub-menu">
						<li><a href="../admin/logout">退出</a></li>
					</ul></li>
			</ul>
		</div>
		<div class="right" id="right"></div>
	</div>
	<div class="foot"></div>
</body>
</html>