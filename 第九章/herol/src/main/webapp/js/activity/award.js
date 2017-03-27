$(document).ready(function() {
	var userScope = 0; // 0-未选择，1-编辑用户，2-全部用户
	var uids = '';
	var awards = ''; // 奖励信息
	var title = '';
	var content = '';
	var senderName = '';
	$("#saveTitle").click(function() {
		// 保存标题
		title = $("#title").val();
		alert('保存成功');
	});
	$("#saveSenderName").click(function() {
		// 保存发送人
		senderName = $("#senderName").val();
		alert('保存成功');
	});
	$("#saveContent").click(function() {
		// 保存内容
		content = $("#content").val();
		alert('保存成功');
	});
	$("#edituser").click(function() {
		// 编辑用户使用
		userScope = 1;
		uids = $("#uids").val();
		alert('保存成功');
	});
	$("#alluser").click(function() {
		// 全部用户使用
		userScope = 2;
		alert('保存成功');
	});
	$("#saveAward").click(function() {
		// 保存奖励
		awards = $("#awards").val();
		alert('保存成功');
	});
	$("#postAward").click(function() {
		if (title == '' || title == undefined) {
			alert('请输入公告标题');
			return;
		}
		if (content == '' || content == undefined) {
			alert('请输入公告内容');
			return;
		}
		if (senderName == '' || senderName == undefined) {
			alert('请输入发送人');
			return;
		}
		if (userScope == 0) {
			alert('请输入接收玩家id或选择全部玩家');
			return;
		}
		if (userScope == 1) {
			if (uids == '') {
				alert('请输入useid');
				return;
			}
		}
		if (awards == ''||awards==undefined) {
			var flag = confirm("邮件确定不添加附件吗？");
			if(!flag){
				return;
			}
		}
		var datas = {
			content : content,
			title : title,
			senderName : senderName,
			uids : uids,
			awards : awards
		}
		// 发送奖励
		$.ajax({
			type : "post",
			dataType : "text",
			url : "../activity/giveAward",
			data : datas,
			async : true,
			success : function(data) {
				if (data == 'success') {
					alert('发送邮件成功');
					skip("../activity/awardlist?userID=" + userID);
				} else {
					alert('发送邮件失败');
				}
			}
		});
	});
});