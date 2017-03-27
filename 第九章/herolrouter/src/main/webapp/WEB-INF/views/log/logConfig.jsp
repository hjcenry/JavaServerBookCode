<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志管理</title>
<style type="text/css">
.logConfig {
	text-align: center;
}

.logConfigLayer {
	
}

.logConfigLayer ul {
	list-style-type: none;
	display: inline-block;
	width: 20%;
}

.logConfigLayer ul li {
	margin: 20px;
	height: 40px;
	line-height: 40px;
}

.logConfigLayer .btn {
	width: 100%;
	height: 50px;
	line-height: 50px;
	background-color: lightblue;
}

.logConfigLayer .btn:hover {
	opacity: 0.8;
}
</style>
<script type="text/javascript"
	src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript">
	function update() {
		var datas = {
			sdk : $("input[name='sdk']:checked").val(),
			msg : $("input[name='msg']:checked").val(),
			client : $("input[name='client']:checked").val()
		}
		$.ajax({
			url : "../log/logConfigHandle",
			type : "post",
			data : datas,
			success : function(data) {
				alert("修改日志配置成功");
				$("#right").load("../log/logConfig");
			}
		});
	}
</script>
</head>
<body>
	<div class="logConfig">
		<div class="logConfigLayer">
			<ul>
				<li><h2>管理系统</h2></li>
				<li>SDK 日志&nbsp;&nbsp;&nbsp;&nbsp;<input class="textbox"
					type="radio" name="sdk" value="true"
					<c:if test="${sdk==true }">checked="checked"</c:if> />&nbsp;开&nbsp;&nbsp;<input
					class="textbox" type="radio" name="sdk" value="false"
					<c:if test="${sdk==false }">checked="checked"</c:if> />&nbsp;关&nbsp;&nbsp;
				</li>
				<li>消息日志&nbsp;&nbsp;&nbsp;&nbsp;<input class="textbox"
					type="radio" name="msg" value="true"
					<c:if test="${msg==true }">checked="checked"</c:if> />&nbsp;开&nbsp;&nbsp;<input
					class="textbox" type="radio" name="msg" value="false"
					<c:if test="${msg==false }">checked="checked"</c:if> />&nbsp;关&nbsp;&nbsp;
				</li>
				<li>客户端日志&nbsp;&nbsp;&nbsp;<input class="textbox" type="radio"
					name="client" value="true"
					<c:if test="${client==true }">checked="checked"</c:if> />&nbsp;开&nbsp;&nbsp;<input
					class="textbox" type="radio" name="client" value="false"
					<c:if test="${client==false }">checked="checked"</c:if> />&nbsp;关&nbsp;&nbsp;
				</li>
				<li><div class="btn" onclick="update()">修改</div></li>
			</ul>
		</div>
	</div>
</body>
</html>