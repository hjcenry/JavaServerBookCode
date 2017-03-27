<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<title>后台管理页面</title>
	<link type="text/css" rel="stylesheet" href="../css/admin/reset_pc.css" />
	<link type="text/css" rel="stylesheet" href="../css/admin/admin.css" />
	<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
</head>
<body>
	<header class="header">
		<img src="../images/admin/logo.png" />
		<a href="logout" target="_top"><img src="../images/admin/exit.png" />退出登录</a>
		<div>
			<c:choose>
				<c:when test="${user.img!='' && not empty user.img}">
					<img src="../images/icons/${user.img}" />
				</c:when>
       			<c:otherwise>
       				<img src="../images/icons/avatar.png" />
				</c:otherwise>
			</c:choose>
			<strong>${user.nickname}</strong>
		</div>
	</header>
</body>
</html>