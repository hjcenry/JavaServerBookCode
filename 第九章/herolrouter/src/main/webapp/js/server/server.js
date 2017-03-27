$(document).ready(function() {
	// 文档加载完毕
	$('#saveBtn').click(function() {
		saveServer();
	});
});

function startServer(ip){
	var host = ip;
	var flag = confirm("确定要开服吗？");
	if(flag){
		var datas = {
			"host" : host
		};
		$.ajax({
			data : datas,
			dataType : "text",
			type : "GET",
			url : "../server/operateStart",
			success : function(result) {
				if (result == "success") {
					alert("开服成功");
					$('#right').load("../server/serverlist");
				} else if(result == "started"){
					alert("服务器已开启");
				} else {
					alert("开服失败");
				}
			},
			error : function(result) {
				alert("网络连接失败");
			}
		});
	}
}

function shutServer(ip){
	var host = ip;
	var flag = confirm("确定要关服吗？");
	if(flag){
		var datas = {
			"host" : host
		};
		$.ajax({
			data : datas,
			dataType : "text",
			type : "GET",
			url : "../server/operateShut",
			success : function(result) {
				if (result == "success") {
					alert("关服成功");
					$('#right').load("../server/serverlist");
				} else if(result == "shutted"){
					alert("服务器已关闭");
				} else {
					alert("关服失败");
				}
			},
			error : function(result) {
				alert("网络连接失败");
			}
		});
	}
}

function delServer(serverid) {
	var id = serverid;
	var flag = confirm("确定要删除服务器配置信息吗？");
	if(flag){
		var datas = {
			"id" : id
		};
		$.ajax({
			data : datas,
			type : "POST",
			url : "../server/deleteserver",
			success : function(result) {
				if (result == "SUCCESS") {
					alert("删除服务器成功");
					$('#right').load("../server/serverlist");
				} else {
					alert("删除服务器失败");
				}
			},
			error : function(result) {
				alert("删除服务器失败");
			}
		});
	}
}

function saveServer() {
	var id = $('#id').val();
	var name = $('#name').val();
	var ip = $('#ip').val();
	var port = $('#port').val();
	var state = $('#state').val();
	var max = $('#max').val();
	var remark = $('#remark').val();
	if (id == "") {
		alert("请填写服务器id");
		return false;
	}
	if (name == "") {
		alert("请填写服务器名字");
		return false;
	}
	if (ip == "") {
		alert("请填写服务器ip");
		return false;
	}
	if (port == "") {
		alert("请填写服务器端口");
		return false;
	}
	var datas = {
		"id" : id,
		"name" : name,
		"ip" : ip,
		"port" : port,
		"state" : state,
		"remark" : remark
	};
	$.ajax({
		data : datas,
		type : "POST",
		url : "../server/saveserver",
		success : function(result) {
			if (result == "SUCCESS") {
				alert("保存服务器成功");
				$('#right').load("../server/serverlist");
			} else {
				alert("保存服务器失败");
			}
		},
		error : function(result) {
			alert("保存服务器失败");
		}
	});
}