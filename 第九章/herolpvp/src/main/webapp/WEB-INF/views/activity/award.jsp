<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>补偿奖励</title>
<script type="text/javascript"
	src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="../js/activity/award.js"></script>
<style type="text/css">
body {
	font-family: "微软雅黑";
}

#award {
	text-align: center;
}

#award ul {
	list-style-type: none;
}

#award li {
	margin-top: 50px;
}

#award textarea {
	display: inline-block;
	width: 40%;
	height: 150px;
}

#award input {
	display: inline-block;
	width: 40%;
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

#postAward {
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
</style>
</head>

<body>
	<div id="award">
		<h1>补偿奖励</h1>
		<ul>
			<li>
				<h3>邮件标题</h3> <input id="title" type="text" placeholder="请输入邮件标题" />
				<div class="operate">
					<div id="saveTitle">保存</div>
				</div>
			</li>
			<li>
				<h3>邮件内容</h3> <textarea id="content" placeholder="请输入邮件内容"></textarea>
				<div class="operate">
					<div id="saveContent">保存</div>
				</div>
			</li>
			<li>
				<h3>发送人</h3> <input id="senderName" type="text" placeholder="请输入发件人"
				value="系统" />
				<div class="operate">
					<div id="saveSenderName">保存</div>
				</div>
			</li>
			<li>
				<h3>接收玩家id</h3> <textarea id="uids"
					placeholder="请输入用户userid，多个用户以英文逗号隔开"></textarea>
				<div class="operateuser">
					<div id="edituser">使用编辑用户</div>
					<div id="alluser">全部用户使用</div>
				</div>
				<div style="clear: both;"></div>
			</li>
			<li>
				<h3>
					邮件附件 <a
						href="http://123.59.110.201:8000/showdoc/index.php/5?page_id=28">附件格式</a>
				</h3> <textarea id="awards"
					placeholder="格式：物品类型#物品id#数量，多个奖励以英文逗号隔开
物品类型查看(除卡牌，装备，碎片，消耗品外，其他物品id输入0)
例：1#200001#1,3#200001#1,6#0#100000
代表吕布卡牌1个，吕布卡牌碎片1个，金币100000"></textarea>
				<div class="operate">
					<div id="saveAward">保存附件</div>
				</div>
			</li>
			<li>
				<div class="operate">
					<div id="postAward">发放奖励</div>
				</div>
			</li>
		</ul>
	</div>
</body>

</html>