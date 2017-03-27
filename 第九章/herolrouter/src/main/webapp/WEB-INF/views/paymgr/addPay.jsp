<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加支付记录</title>
<style type="text/css">
.btn {
	width: 30%;
	height: 50px;
	line-height: 50px;
	background-color: lightblue;
}

.btn:hover {
	opacity: 0.8;
}

.addPay {
	text-align: center;
}
</style>
<script type="text/javascript">
	function addPay() {
		var accId = $("#accId").val();
		var userID = $("#userId").val();
		var orderId = $("#orderId").val();
		var amount = $("#amount").val();
		var channel = $("#channel").val();
		var gamename = $("#gamename").val();
		var goodname = $("#goodname").val();
		var payDate = $("#payDate").val();
		if (accId == '') {
			alert('请输入账号id');
			return;
		}
		if (userID == '') {
			alert('请输入平台账号id');
			return;
		}
		if (orderId == '') {
			alert('请输入厂商订单号');
			return;
		}
		if (amount == '') {
			alert('请输入充值金额');
			return;
		}
		if (channel == '') {
			alert('请输入渠道名');
			return;
		}
		if (gamename == '') {
			alert('请输入游戏名');
			return;
		}
		if (goodname == '') {
			alert('请输入商品名');
			return;
		}
		if (payDate == '') {
			alert('请输入充值日期');
			return;
		}
		var datas = {
			"accId" : accId,
			"userID" : userID,
			"orderId" : orderId,
			"amount" : amount,
			"channel" : channel,
			"gamename" : gamename,
			"goodname" : goodname,
			"payDate" : payDate
		}
		$.ajax({
			url : "../paymgr/addPayHandle",
			dataType : "text",
			type : "post",
			data : datas,
			success : function(data) {
				if (data == "success") {
					alert("添加支付记录成功");
					$("#right").load("../paymgr/paylist");
				} else {
					alert("添加支付记录失败");
				}
			}
		});
	}
</script>
</head>
<body>
	<div class="addPay">
		<center>
			<table>
				<tr>
					<td>玩家userID</td>
					<td><input type="number" id="accId" placeHolder="请输入账号userID"></td>
				</tr>
				<tr>
					<td>平台账号id</td>
					<td><input type="text" id="userId" placeHolder="请输入第三方账号id"
						value="test_uid"></td>
				</tr>
				<tr>
					<td>厂商订单号</td>
					<td><input type="text" id="orderId" placeHolder="请输入厂商订单号"
						value="test_order_id"></td>
				</tr>
				<tr>
					<td>充值金额（元）</td>
					<td><input type="number" id="amount" placeHolder="请输入充值金额"></td>
				</tr>
				<tr>
					<td>渠道</td>
					<td><select name="channel" id="channel">
							<option value="0"
								<c:if test="${channel==0 }">selected="selected"</c:if>>无渠道</option>
							<option value="android-xiaomi"
								<c:if test="${channel=='android-xiaomi' }">selected="selected"</c:if>>小米</option>
							<option value="android-360"
								<c:if test="${channel=='android-360' }">selected="selected"</c:if>>360</option>
							<option value="android-4399"
								<c:if test="${channel=='android-4399' }">selected="selected"</c:if>>4399</option>
							<option value="android-le8"
								<c:if test="${channel=='android-le8' }">selected="selected"</c:if>>乐8</option>
							<option value="android-paojiao"
								<c:if test="${channel=='android-paojiao' }">selected="selected"</c:if>>泡椒</option>
							<option value="android-baidu"
								<c:if test="${channel=='android-baidu' }">selected="selected"</c:if>>百度</option>
							<option value="android-tecent"
								<c:if test="${channel=='android-tecent' }">selected="selected"</c:if>>腾讯</option>
							<option value="ios-xy"
								<c:if test="${channel=='ios-xy' }">selected="selected"</c:if>>XY助手</option>
							<option value="ios-aisi"
								<c:if test="${channel=='ios-aisi' }">selected="selected"</c:if>>爱思</option>
							<option value="ios-haima"
								<c:if test="${channel=='ios-haima' }">selected="selected"</c:if>>海马安卓</option>
							<option value="android-haima"
								<c:if test="${channel=='android-haima' }">selected="selected"</c:if>>海马iOS</option>
							<option value="ios-kuaiyong"
								<c:if test="${channel=='ios-kuaiyong' }">selected="selected"</c:if>>快用</option>
							<option value="ios"
								<c:if test="${channel=='ios' }">selected="selected"</c:if>>iOS正版</option>
							<option value="android-sandayunying"
								<c:if test="${channel=='android-sandayunying' }">selected="selected"</c:if>>三大运营商</option>
							<option value="android-uc"
								<c:if test="${channel=='android-uc' }">selected="selected"</c:if>>UC</option>
							<option value="android-jinli"
								<c:if test="${channel=='android-jinli' }">selected="selected"</c:if>>金立</option>
							<option value="android-lenovo"
								<c:if test="${channel=='android-lenovo' }">selected="selected"</c:if>>联想</option>
							<option value="android-vivo"
								<c:if test="${channel=='android-vivo' }">selected="selected"</c:if>>vivo</option>
							<option value="android-huawei"
								<c:if test="${channel=='android-huawei' }">selected="selected"</c:if>>华为</option>
							<option value="android-oppo"
								<c:if test="${channel=='android-oppo' }">selected="selected"</c:if>>oppo</option>
							<option value="android-dangle"
								<c:if test="${channel=='android-dangle' }">selected="selected"</c:if>>当乐</option>
							<option value="android-leshi"
								<c:if test="${channel=='android-leshi' }">selected="selected"</c:if>>乐视</option>
							<option value="android-muzhiwan"
								<c:if test="${channel=='android-muzhiwan' }">selected="selected"</c:if>>拇指玩</option>
							<option value="android-wandoujia"
								<c:if test="${channel=='android-wandoujia' }">selected="selected"</c:if>>豌豆荚</option>
							<option value="android-yingyonghui"
								<c:if test="${channel=='android-yingyonghui' }">selected="selected"</c:if>>应用汇</option>
							<option value="android-shouyoutianxia"
								<c:if test="${channel=='android-shouyoutianxia' }">selected="selected"</c:if>>手游天下</option>
							<option value="android-mumayi"
								<c:if test="${channel=='android-mumayi' }">selected="selected"</c:if>>木蚂蚁</option>
							<option value="android-youyoucun"
								<c:if test="${channel=='android-youyoucun' }">selected="selected"</c:if>>悠悠村</option>
							<option value="android-jidi"
								<c:if test="${channel=='android-jidi' }">selected="selected"</c:if>>基地</option>
							<option value="android-liantong"
								<c:if test="${channel=='android-jidi' }">selected="selected"</c:if>>联通</option>
					</select></td>
				</tr>
				<tr>
					<td>游戏名</td>
					<td><input type="text" id="gamename" placeHolder="请输入游戏名"
						value="lasthero"></td>
				</tr>
				<tr>
					<td>商品名</td>
					<td><input type="text" id="goodname" placeHolder="请输入商品名"
						value="模拟商品数据"></td>
				</tr>
				<tr>
					<td>支付日期</td>
					<td><input type="text" id="payDate" placeHolder="2015-01-01"></td>
				</tr>
			</table>
			<div class="btn" onclick="addPay()">添加</div>
		</center>
	</div>
</body>
</html>