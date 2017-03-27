<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询</title>
<style type="text/css">
.queryLayer {
	width: 100%;
	text-align: center;
	padding-top: 200px;
}

#loginBtn {
	width: 10%;
	height: 50px;
	line-height: 50px;
	background-color: lightblue;
	opacity: 1.0;
	display: inline-block;
}

#loginBtn:hover {
	opacity: 0.8;
}

.textbox {
	border: 0px;
}
</style>
<script type="text/javascript">
	function queryPlayer() {
		var userID = $('#userID').val();
		if (userID == '') {
			alert('请输入userid');
			return;
		}
		$.ajax({
			type : "get",
			dataType : "text",
			data : {
				"userID" : userID
			},
			url : "../activity/queryHandle",
			success : function(data) {
				if (data == "success") {
					skip("../activity/awardlist?userID=" + userID);
				} else {
					alert("不存在UID为" + userID + "的玩家信息");
					return;
				}
			}
		});
	}
</script>
</head>
<body>
	<div class="queryLayer">
		<center>
			玩家UID:&nbsp;&nbsp;&nbsp;&nbsp;<input id="userID"
				placeHolder="请输入玩家的UID" />&nbsp;&nbsp;&nbsp;&nbsp;
			<div id="loginBtn" onclick="queryPlayer()">查询</div>
		</center>
	</div>
</body>
</html>