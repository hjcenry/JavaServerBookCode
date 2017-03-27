<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>补偿奖励</title>
<script type="text/javascript"
	src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/activity/sn.js"></script>
<style type="text/css">
body {
	font-family: "微软雅黑";
}

#sn {
	text-align: center;
}

#sn ul {
	list-style-type: none;
}

#sn li {
	margin-top: 50px;
}

#sn textarea {
	display: inline-block;
	width: 40%;
	height: 150px;
}

div.operate, div.operateuser {
	width: 40%;
	margin: 0 auto;
}

div.operate div {
	width: 100%;
}

div.operateuser div {
	display: inline-block;
	width: 49%;
}

div.operate div, div.operateuser div {
	background-color: lightblue;
	height: 50px;
	line-height: 50px;
}

#edituser {
	float: left;
}

#alluser {
	float: right;
}

div.operate div:hover, div.operateuser div:hover, .addBtn {
	opacity: 0.8;
}

#postSn {
	background-color: lightgreen;
}

h3 {
	margin-bottom: 10px;
}

.addBtn {
	display: inline-block;
	height: 50px;
	width: 60px;
	background-color: lightblue;
}

.addUser {
	float: left;
}

.addUserBtn {
	float: left;
}

#sn ul li ol{
	list-style-type: 
}
</style>
<script type="text/javascript">
	$("#postSn").click(function() {
		var sn = $('#sns').val();
		if (sn == '' || sn == undefined) {
			alert('请输入兑换码平台');
			return;
		}
		var datas = {
			"sn_channels" : sn
		}
		// 发送奖励
		$.ajax({
			type : "post",
			dataType : "text",
			url : "../sn/addSn",
			data : datas,
			async : true,
			success : function(data) {
				if (data == 'success') {
					alert('修改兑换码平台成功');
					skip("../sn/snlist");
				} else {
					alert('修改兑换码平台失败');
				}
			}
		});
	});
</script>
</head>

<body>
	<div id="sn">
		<h1>兑换码平台</h1>
		<ul>
			<li>
				<h3>平台id</h3> <textarea id="sns" placeholder="请输入兑换码平台，多个奖励以英文逗号隔开">${sn }</textarea>
			</li>
			<li>
				<div class="operate">
					<div id="postSn">修改兑换码平台</div>
				</div>
			</li>
		</ul>
	</div>
</body>

</html>