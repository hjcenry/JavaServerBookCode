<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付列表</title>
<link rel="stylesheet" type="text/css" href="../css/page.css" />
<script type="text/javascript">

	function getWhere(){
		var where="";
		var orderId = $('#orderId').val();
		var accId = $('#accId').val();
		var channel = $('#channel').val();
		var gamename = $('#gamename').val();
		var goodname = $('#goodname').val();
		var payDateMin = $('#payDateMin').val();
		var payDateMax = $('#payDateMax').val();
		var amountMin = $('#amountMin').val();
		var amountMax = $('#amountMax').val();
		if(orderId!=''){
			where+="&orderId="+orderId+"";
		}
		if(accId!=''){
			where+="&accId="+accId+"";
		}
		if(channel!=-1){
			where+="&channel="+channel+"";
		}
		if(gamename!=''){
			where+="&gamename="+gamename+"";
		}
		if(goodname!=''){
			where+="&goodname="+goodname+"";
		}
		if(payDateMin!=''){
			where+="&payDateMin="+payDateMin+"";
		}
		if(payDateMax!=''){
			where+="&payDateMax="+payDateMax+"";
		}
		if(amountMin!=''){
			where+="&amountMin="+amountMin+"";
		}
		if(amountMax!=''){
			where+="&amountMax="+amountMax+"";
		}
		if($('#state').val()!=-1){
			where+="&state="+$('#state').val()+"";
		}
		return where;
	}

	function search(page){
		skip("../paymgr/paylist?pageNo="+page+""+getWhere());
	}
	
</script>
<style type="text/css">
.btn {
	width: 95%;
	height: 50px;
	line-height: 50px;
	background-color: lightblue;
}

.btn:hover {
	opacity: 0.8;
}

.paylist {
	text-align: center;
	padding: 50px;
}
</style>
</head>
<body>
	<div class="paylist">
		<center>
			<table border="1">
				<tr>
					<th colspan="10"><center>
							<h2>支付订单</h2>
						</center></th>
				</tr>
				<tr>
					<th colspan="10">厂商订单号：<input type="text" name="orderId"
						id="orderId" value="${orderId }" /> 账号id：<input type="text"
						name="accId" id="accId" value="${accId }" /> 渠道： <select
						name="channel" id="channel">
							<option value="-1">请选择...</option>
							<option value="0"
								<c:if test="${channel==0 }">selected="selected"</c:if>>无渠道</option>
							<option value="android-xiaomi"
								<c:if test="${channel=='android-xiaomi' }">selected="selected"</c:if>>小米</option>
							<option value="android-360"
								<c:if test="${channel=='android-360' }">selected="selected"</c:if>>360</option>
							<option value="android-4399"
								<c:if test="${channel=='android-4399' }">selected="selected"</c:if>>4399</option>
							<option value="ios-le8"
								<c:if test="${channel=='ios-le8' }">selected="selected"</c:if>>乐8</option>
							<option value="android-paojiao"
								<c:if test="${channel=='android-paojiao' }">selected="selected"</c:if>>泡椒</option>
							<option value="android-baidu"
								<c:if test="${channel=='android-baidu' }">selected="selected"</c:if>>百度</option>
							<option value="android-tecent"
								<c:if test="${channel=='android-tecent' }">selected="selected"</c:if>>腾讯</option>
							<option value="ios-xy"
								<c:if test="${channel=='ios-xy' }">selected="selected"</c:if>>XY助手</option>
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
							<option value="ios-aisi"
								<c:if test="${channel=='ios-aisi' }">selected="selected"</c:if>>爱思</option>
					</select> 游戏名：<input type="text" name="gamename" id="gamename"
						value="${gamename }" /> 商品名：<input type="text" name="goodname"
						id="goodname" value="${goodname }" /><br /> 支付日期(格式如2015-01-01)：<input
						type="text" name="payDateMin" id="payDateMin"
						value="${payDateMin }" />~ <input type="text" name="payDateMax"
						id="payDateMax" value="${payDateMax }" /> 充值金额（元）<input
						type="text" name="amountMin" id="amountMin" value="${amountMin }" />~
						<input type="text" name="amountMax" id="amountMax"
						value="${amountMax }" /> 订单状态： <select id="state">
							<option value="-1">请选择...</option>
							<option value="0"
								<c:if test="${state==0 }">selected="selected"</c:if>>待验证</option>
							<option value="1"
								<c:if test="${state==1 }">selected="selected"</c:if>>支付失败</option>
							<option value="2"
								<c:if test="${state==2 }">selected="selected"</c:if>>支付成功</option>
					</select>
						<div class="btn" onclick="javascript:search(${pageNo})">搜索</div> <!-- <button onclick="javascript:search(${pageNo})">搜索</button> -->
					</th>
				</tr>
				<tr>
					<th>游戏服订单号</th>
					<th>平台账号id</th>
					<th>玩家id</th>
					<th>厂商订单号</th>
					<th>充值金额（元）</th>
					<th>渠道</th>
					<th>游戏名</th>
					<th>商品名</th>
					<th>支付日期</th>
					<th>完成状态</th>
				</tr>
				<c:choose>
					<c:when test="${fn:length(payList)==0 }">
						<tr>
							<th colspan="10"><center>暂无充值记录</center></th>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${payList }" var="pay">
							<tr>
								<td>${pay.billno }</td>
								<td>${pay.userId }</td>
								<td>${pay.accId }</td>
								<td>${pay.orderId }</td>
								<td>${pay.amount }</td>
								<td><c:choose>
										<c:when test="${pay.channel=='ios-xy' }">xy</c:when>
										<c:when test="${pay.channel=='ios-haima' }">海马iOS</c:when>
										<c:when test="${pay.channel=='android-haima' }">海马安卓</c:when>
										<c:when test="${pay.channel=='android-xiaomi' }">小米</c:when>
										<c:when test="${pay.channel=='android-360' }">360</c:when>
										<c:when test="${pay.channel=='ios' }">iOS正版</c:when>
										<c:when test="${pay.channel=='ios-le8' }">乐8</c:when>
										<c:when test="${pay.channel=='android-paojiao' }">泡椒</c:when>
										<c:when test="${pay.channel=='android-baidu' }">百度</c:when>
										<c:when test="${pay.channel=='android-tecent' }">腾讯</c:when>
										<c:when test="${pay.channel=='android-kuaiyong' }">快用</c:when>
										<c:when test="${pay.channel=='android-sandayunying' }">三大运营</c:when>
										<c:when test="${pay.channel=='android-uc' }">UC</c:when>
										<c:when test="${pay.channel=='android-jinli' }">金立</c:when>
										<c:when test="${pay.channel=='android-lenovo' }">联想</c:when>
										<c:when test="${pay.channel=='android-vivo' }">vivo</c:when>
										<c:when test="${pay.channel=='android-huawei' }">华为</c:when>
										<c:when test="${pay.channel=='android-oppo' }">oppo</c:when>
										<c:when test="${pay.channel=='android-dangle' }">当乐</c:when>
										<c:when test="${pay.channel=='android-leshi' }">乐视</c:when>
										<c:when test="${pay.channel=='android-muzhiwan' }">拇指玩</c:when>
										<c:when test="${pay.channel=='android-wandoujia' }">豌豆荚</c:when>
										<c:when test="${pay.channel=='android-yingyonghui' }">应用汇</c:when>
										<c:when test="${pay.channel=='android-shouyoutianxia' }">手游天下</c:when>
										<c:when test="${pay.channel=='android-mumayi' }">木蚂蚁</c:when>
										<c:when test="${pay.channel=='android-youyoucun' }">悠悠村</c:when>
										<c:when test="${pay.channel=='ios-aisi' }">爱思</c:when>
										<c:otherwise>${pay.channel }</c:otherwise>
									</c:choose></td>
								<td>${pay.gamename }</td>
								<td>${pay.goodname }</td>
								<td>${pay.payDate }</td>
								<td><c:choose>
										<c:when test="${pay.isFinished==0 }">待验证</c:when>
										<c:when test="${pay.isFinished==1 }">失败</c:when>
										<c:when test="${pay.isFinished==2 }">成功</c:when>
									</c:choose></td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(payList)!=0 }">
							<tr>
								<th colspan="12"><c:choose>
										<c:when test="${pageNo==1 }">
											<div class="pageBtn"><</div>
										</c:when>
										<c:otherwise>
											<div class="pageBtn"
												onclick="javascript:search('${pageNo-1}')"><</div>
										</c:otherwise>
									</c:choose> <c:forEach begin="1" end="${payCount }" step="1" var="nowPage">
										<c:choose>
											<c:when test="${nowPage==pageNo }">
												<div class="page selected">${nowPage }</div>
											</c:when>
											<c:otherwise>
												<div class="page" onclick="javascript:search('${nowPage}')">${nowPage }</div>
											</c:otherwise>
										</c:choose>
									</c:forEach> <c:choose>
										<c:when test="${pageNo==payCount }">
											<div class="pageBtn">></div>
										</c:when>
										<c:otherwise>
											<div class="pageBtn"
												onclick="javascript:search('${pageNo+1}')">></div>
										</c:otherwise>
									</c:choose>
							</tr>
						</c:if>
					</c:otherwise>
				</c:choose>
			</table>
		</center>
	</div>
</body>
</html>