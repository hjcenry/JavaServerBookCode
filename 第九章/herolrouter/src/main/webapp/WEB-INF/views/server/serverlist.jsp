<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/server/server.js"></script>
<title>服务器列表</title>
<style type="text/css">
</style>
</head>
<body>
	<div align="center">
	<br/>
	<br/>
		<table align="center" class="tb1 fm80p" border="1">
			<tr>
				<th>id</th>
				<th>服务器名字</th>
				<th>ip</th>
				<th>端口</th>
				<th>状态</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
			<c:choose>
				<c:when test="${serverList.size==0 }">
					<tr>
						<td colspan="7">暂无服务器</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="server" items="${list }">
						<tr>
							<td>${server.id }</td>
							<td>${server.name }</td>
							<td>${server.ip }</td>
							<td>${server.port }</td>
							<td><c:choose>
									<c:when test="${server.state==0 }">新服</c:when>
									<c:when test="${server.state==1 }">空闲</c:when>
									<c:when test="${server.state==2 }">繁忙</c:when>
									<c:when test="${server.state==3 }">爆满</c:when>
									<c:when test="${server.state==4 }">维护</c:when>
								</c:choose></td>
							<td>${server.remark }</td>
							<td>
								<table>
									<tr>
										<td><a href="#"
											onclick="javascript:skip('../server/addserver?id=${server.id}')">修改</a></td>
										<td><a href="#" onclick="delServer(${server.id})">删除</a></td>
										<td><a href="http://${server.ip}:8091/36/admin/index"
											target="_blank">管理</a></td>
										<td><c:choose>
												<c:when test="${server.state==4 }">
													<a href="#" onclick="startServer('${server.ip}')">开服</a>
												</c:when>
												<c:otherwise>
													<a href="#" onclick="shutServer('${server.ip}')">关服</a>
												</c:otherwise>
											</c:choose></td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
</body>
</html>