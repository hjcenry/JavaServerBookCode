function login(){
	if($("#loginname").val() == null || $("#loginname").val() == "" ){
		alert('用户名不能为空');
		return;
	}
	if($("#password").val() == null || $("#password").val() == "" ){
		alert('密码不能为空');
		return;
	}
	var datas={
		name:$("#loginname").val(),
		pwd:$("#password").val()
	}
	$.ajax({
		data : datas,
		type : "POST",
		dataType : "text",
		url : "../admin/loginHandle",
		success : function(data) {
			console.log(data);
			if (data == "success") {
				location.href="../admin/index ";
			} else {
				alert("登录失败");
			}
		},
		error : function(result) {
			alert("登录失败");
		}
	});
}

//enter键触发登录功能
document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
     if(e && e.keyCode==13){ // enter 键
    	 login();
    }
}; 