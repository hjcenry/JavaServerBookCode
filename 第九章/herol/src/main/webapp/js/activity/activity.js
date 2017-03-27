
$(function() {
	$('#file').uploadify(
			{
				'auto' : true,// 是否自动上传，默认true
				'swf' : '../uploadify/uploadify.swf',// swf文件
				'uploader' : '../manager/uploadimg',// 上传的后台脚本
				'cancelImg' : '../js/uploadify/uploadify-cancel.png',
				'buttonText' : '选择活动图片',// 按钮文字
				'width' : 80,// 按钮宽、高
				'height' : 20,
				'rollover' : true,// 翻转效果
				'multi' : false,// 是否支持多选
				'fileSizeLimit' : '10MB',// 上传文件大小限制
				'queueSizeLimit' : 1,// 上传文件个数限制
				'fileTypeExts' : '*.jpg;*.png;*.gif',// 上传文件格式限制
				'fromData' : {
					'id' : $('#adminId').val()
				},
				'onSelectError': function(file, errorCode, errorMsg) { // 选择错误提示
		            switch (errorCode) {
		                case -100:
		                    $.DIC.dialog({
		                        content: "上传的文件数量已经超出系统限制的" + $('#file').uploadify('settings', 'queueSizeLimit') + "个文件！",
		                        autoClose: true
		                    });
		                    break;
		                case -110:
		                    $.DIC.dialog({
		                        content: "文件 [" + file.name + "] 大小超出系统限制的" + $('#file').uploadify('settings', 'fileSizeLimit') + "大小！",
		                        autoClose: true
		                    });
		                    break;
		                case -120:
		                    $.DIC.dialog({
		                        content: "文件 [" + file.name + "] 大小异常！",
		                        autoClose: true
		                    });
		                    break;
		                case -130:
		                    $.DIC.dialog({
		                        content: "文件 [" + file.name + "] 类型不正确！",
		                        autoClose: true
		                    });
		                    break;
		            }
		        },
		        'onFallback': function() { // 检测FLASH失败调用
		            $.DIC.dialog({
		                content: "您未安装FLASH控件，无法上传文件！请安装FLASH控件后再试。",
		                autoClose: true
		            });
		        },
				'onUploadSuccess' : function(file, data, response) {// 上传成功后预览图片
					$("#imgurl").attr("src", ".." + data);
					var imgshow = document.getElementById("imgurl");
					if (imgshow.style.display == 'block') {
						imgshow.style.display = 'none';
					} else {
						imgshow.style.display = 'block';
					}
				}
			});
});

function submit() {
	var coupon_name = $("#coupon_name").val();
	if (coupon_name.length == 0) {
		$.DIC.dialog({
            content: "请填写标题",
            autoClose: true
        });
		return false;
	}
	var imgurl = $('#imgurl').attr('src').replace('..', '');
	if (imgurl.length == 0) {
		$.DIC.dialog({
            content: "请上传活动图片",
            autoClose: true
        });
		return false;
	}
	var startTime = $("#startTime").val();
	if (startTime.length == 0) {
		$.DIC.dialog({
            content: "请选择活动开始时间",
            autoClose: true
        });
		return false;
	}
	var endTime = $("#endTime").val();
	if (endTime.length == 0) {
		$.DIC.dialog({
            content: "请选择活动结束时间",
            autoClose: true
        });
		return false;
	}
	var permission = $("#permission").val();
	if (permission.length == 0) {
		$.DIC.dialog({
            content: "请填写使用权限",
            autoClose: true
        });
		return false;
	}
	var coupondesc = UE.getEditor('editor').getContentTxt();
	if (coupondesc.length == 0) {
		$.DIC.dialog({
            content: "请填写活动内容",
            autoClose: true
        });
		return false;
	}
	var number = $("#number").val();
	if (number.length == 0) {
		$.DIC.dialog({
            content: "请填写优惠券数量",
            autoClose: true
        });
		return false;
	}
	var price = $("#price").val();
	if (price.length == 0) {
		$.DIC.dialog({
            content: "请填写价格",
            autoClose: true
        });
		return false;
	}
	var coupon_type = $("#coupon_type").val();
	if (coupon_type.length == 0) {
		$.DIC.dialog({
            content: "请选择支付类型",
            autoClose: true
        });
		return false;
	}
	var status = "0";
	var datas = {
		'imgurl' : imgurl,
		'coupon_name' : coupon_name,
		'starttime' : startTime,
		'endtime' : endTime,
		'usedesc' : permission,
		'coupon_count' : parseInt(number),
		'coupon_type' : parseInt(coupon_type),
		'coupon_price' : parseInt(price),
		'status' : parseInt(status),
		'coupondesc' : coupondesc
	};
	$('#saveBtn').prop("disabled",true);
	$.ajax({
		async : true, // 使用同步的Ajax请求
		type : "POST",
		url : "../activity/activityPublish",
		data : datas,
		success : function(result) {
			$.DIC.dialog({
                content: '提交成功！',
                autoClose: true
            });
			$('#right').load("../coupon/couponlist");
		},
		error : function(result){
			$('#saveBtn').prop("disabled",false);
		}
	});

}
