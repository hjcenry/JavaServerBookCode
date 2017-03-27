<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/server/server.js"></script>
<title>保存服务器配置信息</title>
</head>
<body>
	<div align="center">
	<table align="center" class="tb1 fm80p">
		<tr>
			<td>id</td>
			<td><input id="id" type="number" value="${server.id }"></td>
		</tr>
		<tr>
			<td>服务器名字</td>
			<td><input id="name" type="text" value="${server.name }"></td>
		</tr>
		<tr>
			<td>ip</td>
			<td><input id="ip" type="text" value="${server.ip }"></td>
		</tr>
		<tr>
			<td>端口</td>
			<td><input id="port" type="number" value="${server.port }"></td>
		</tr>
		<tr>
			<td>状态</td>
			<td>
				<select id="state">
					<option value="0" <c:if test="${server.state==0 }">selected="selected"</c:if>>新服</option>
					<option value="1" <c:if test="${server.state==1 }">selected="selected"</c:if>>空闲</option>
					<option value="2" <c:if test="${server.state==2 }">selected="selected"</c:if>>繁忙</option>
					<option value="3" <c:if test="${server.state==3 }">selected="selected"</c:if>>爆满</option>
					<option value="4" <c:if test="${server.state==4 }">selected="selected"</c:if>>维护</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>备注</td>
			<td><input id="remark" type="text" value="${server.remark }"></td>
		</tr>
		<tr>
			<td colspan="2"><button id="saveBtn">保存</button></td>
		</tr>
	</table>
	</div>
</body>
</html>