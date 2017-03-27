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
<title>管理服务器后台</title>
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
<script type="text/javascript"
	src="http://echarts.baidu.com/build/dist/echarts.js"></script>
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
			<h2>管理服务器后台</h2>
		</center>
	</div>
	<div class="main">
		<div class="left">
			<ul class="page-sidebar-menu">
				<li><a href="#"><i class="fa fa-folder-open"></i><span>服务器管理</span><span
						class="arrow"></span></a>
					<ul class="sub-menu">
						<li><a href="#"
							onclick="javascript:skip('../server/serverlist')">查看服务器</a></li>
						<li><a href="#"
							onclick="javascript:skip('../server/addserver?id=-1')">添加服务器</a></li>
					</ul></li>
				<li><a href="#"><i class="fa fa-folder-open"></i><span>兑换码管理</span><span
						class="arrow"></span></a>
					<ul class="sub-menu">
						<li><a href="#" onclick="javascript:skip('../sn/snlist')">兑换码</a></li>
					</ul></li>
				<li><a href="#"><i class="fa fa-table"></i><span>支付管理</span><span
						class="arrow"></span></a>
					<ul class="sub-menu">
						<li><a href="#"
							onclick="javascript:skip('../paymgr/paylist')">支付查询</a></li>
						<li><a href="#" onclick="javascript:skip('../paymgr/addPay')">添加支付</a></li>
						<li><a href="#"
							onclick="javascript:skip('../paymgr/payChart')">支付统计</a></li>
						<li><a href="#"
							onclick="javascript:skip('../paymgr/payConfig')">支付配置</a></li>
					</ul></li>
				<li><a href="#"><i class="fa fa-table"></i><span>账号管理</span><span
						class="arrow"></span></a>
					<ul class="sub-menu">
						<li><a href="#"
							onclick="javascript:skip('../accountmgr/accountlist')">账号查询</a></li>
						<li><a href="#"
							onclick="javascript:skip('../accountmgr/accountChart')">账号统计</a></li>
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
				<li><a href="#"><i class="fa fa-table"></i><span>后台管理</span><span
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