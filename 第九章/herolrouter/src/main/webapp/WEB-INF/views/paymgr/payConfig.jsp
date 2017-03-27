<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付配置</title>
<style type="text/css">
.payConfig {
	text-align: center;
}

.payConfigLayer {
	
}

.payConfigLayer ul {
	list-style-type: none;
	display: inline-block;
	width: 20%;
}

.payConfigLayer ul li {
	margin: 20px;
	height: 40px;
	line-height: 40px;
}

.payConfigLayer .btn {
	width: 100%;
	height: 50px;
	line-height: 50px;
	background-color: lightblue;
}

.payConfigLayer .btn:hover {
	opacity: 0.8;
}
</style>
<script type="text/javascript"
	src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript">
	function update() {
		var datas = {
			isValidate : $("input[name='isValidate']:checked").val(),
		}
		$.ajax({
			url : "../paymgr/payConfigHandle",
			type : "post",
			data : datas,
			success : function(data) {
				if(data='success'){
					alert("修改支付配置成功");
					$("#right").load("../pay/payConfig");
				}
			}
		});
	}
</script>
</head>
<body>
	<div class="payConfig">
		<div class="payConfigLayer">
			<ul>
				<li>iOS正版支付验证&nbsp;&nbsp;&nbsp;&nbsp;<input class="textbox"
					type="radio" name="isValidate" value="true"
					<c:if test="${isValidate==true }">checked="checked"</c:if> />&nbsp;开启&nbsp;&nbsp;<input
					class="textbox" type="radio" name="isValidate" value="false"
					<c:if test="${isValidate==false }">checked="checked"</c:if> />&nbsp;关闭&nbsp;&nbsp;
				</li>
				<li><div class="btn" onclick="update()">修改</div></li>
			</ul>
		</div>
	</div>
</body>
</html>