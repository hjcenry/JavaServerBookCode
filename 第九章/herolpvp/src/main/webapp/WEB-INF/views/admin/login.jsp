<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>GM管理系统</title>
<style type="text/css">
.login {
	width: 100%;
	text-align: center;
	padding-top: 200px;
}

.loginlayer {
	display: inline-block;
	width: 300px;
}

.loginlayer ul {
	list-style-type: none;
	padding-left: 0px;
}

.loginlayer ul li {
	margin: 20px;
	border-style: double;
	border-color: lightblue;
}

#loginBtn {
	width: 100%;
	height: 50px;
	line-height: 50px;
	background-color: lightblue;
	opacity: 1.0;
}

#loginBtn:hover {
	opacity: 0.8;
}

.textbox {
	border: 0px;
}
</style>
<script type="text/javascript"
	src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/admin/login.js"></script>
</head>
<body class="bodystyle">
	<div class="login">
		<div class="loginlayer">
			<ul>
				<li><h2>管理系统</h2></li>
				<li>用户名&nbsp;&nbsp;<input class="textbox" type="text"
					name="name" id="loginname" /></li>
				<li>密&nbsp;码&nbsp;&nbsp;<input class="textbox" type="password"
					name="pwd" id="password" /></li>
				<li><div id="loginBtn" onclick="login()">登录</div></li>
			</ul>
		</div>
	</div>
</body>
</html>
