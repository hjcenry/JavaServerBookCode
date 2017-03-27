<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账号列表</title>
<link rel="stylesheet" type="text/css" href="../css/page.css" />
<script type="text/javascript">

	function getWhere(){
		var where="";
		var userID = $('#userID').val();
		var uid = $('#uid').val();
		var dateMax = $('#dateMax').val();
		var dateMin = $('#dateMin').val();
		var channel = $('#channel').val();
		if(userID!=''){
			where+="&userID="+userID+"";
		}
		if(uid!=''){
			where+="&uid="+uid+"";
		}
		if(dateMax!=''){
			where+="&dateMax="+dateMax+"";
		}
		if(dateMin!=''){
			where+="&dateMin="+dateMin+"";
		}
		if(channel!=-1){
			where+="&channel="+channel+"";
		}
		return where;
	}

	function search(page){
		skip("../accountmgr/accountlist?pageNo="+page+""+getWhere());
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

.accountlist {
	text-align: center;
	padding: 50px;
}
</style>
</head>
<body>
	<div class="accountlist">
		<center>
			<table border="1">
				<tr>
					<th colspan="4"><center>
							<h2>账号列表</h2>
						</center></th>
				</tr>
				<tr>
					<th colspan="4">账号id：<input type="text" name="userID"
						id="userID" value="${userID }" /> 第三方id：<input type="text"
						name="uid" id="uid" value="${uid }" /> 创建时间(格式如2015-01-01)：<input
						type="text" name="dateMin" id="dateMin" value="${dateMin }" />~ <input
						type="text" name="dateMax" id="dateMax" value="${dateMax }" /> <br />渠道：
						<select id="channel">
							<option value="-1">请选择...</option>
							<option value="0"
								<c:if test="${channel==0 }">selected="selected"</c:if>>无渠道</option>
							<option value="1"
								<c:if test="${channel==1 }">selected="selected"</c:if>>小米</option>
							<option value="2"
								<c:if test="${channel==2 }">selected="selected"</c:if>>360</option>
							<option value="3"
								<c:if test="${channel==3 }">selected="selected"</c:if>>4399</option>
							<option value="4"
								<c:if test="${channel==4 }">selected="selected"</c:if>>乐8</option>
							<option value="5"
								<c:if test="${channel==5 }">selected="selected"</c:if>>泡椒</option>
							<option value="6"
								<c:if test="${channel==6 }">selected="selected"</c:if>>百度</option>
							<option value="7"
								<c:if test="${channel==7 }">selected="selected"</c:if>>腾讯</option>
							<option value="8"
								<c:if test="${channel==8 }">selected="selected"</c:if>>XY助手</option>
							<option value="9"
								<c:if test="${channel==9 }">selected="selected"</c:if>>海马iOS</option>
							<option value="10"
								<c:if test="${channel==10 }">selected="selected"</c:if>>快用</option>
							<option value="11"
								<c:if test="${channel==11 }">selected="selected"</c:if>>iOS正版</option>
							<option value="12"
								<c:if test="${channel==12 }">selected="selected"</c:if>>三大运营商</option>
							<option value="13"
								<c:if test="${channel==13 }">selected="selected"</c:if>>UC</option>
							<option value="14"
								<c:if test="${channel==14 }">selected="selected"</c:if>>金立</option>
							<option value="15"
								<c:if test="${channel==15 }">selected="selected"</c:if>>联想</option>
							<option value="16"
								<c:if test="${channel==16 }">selected="selected"</c:if>>vivo</option>
							<option value="17"
								<c:if test="${channel==17 }">selected="selected"</c:if>>华为</option>
							<option value="18"
								<c:if test="${channel==18 }">selected="selected"</c:if>>oppo</option>
							<option value="19"
								<c:if test="${channel==19 }">selected="selected"</c:if>>当乐</option>
							<option value="20"
								<c:if test="${channel==20 }">selected="selected"</c:if>>乐视</option>
							<option value="21"
								<c:if test="${channel==21 }">selected="selected"</c:if>>拇指玩</option>
							<option value="22"
								<c:if test="${channel==22 }">selected="selected"</c:if>>豌豆荚</option>
							<option value="23"
								<c:if test="${channel==23 }">selected="selected"</c:if>>应用汇</option>
							<option value="24"
								<c:if test="${channel==24 }">selected="selected"</c:if>>手游天下</option>
							<option value="25"
								<c:if test="${channel==25 }">selected="selected"</c:if>>木蚂蚁</option>
							<option value="26"
								<c:if test="${channel==26 }">selected="selected"</c:if>>悠悠村</option>
							<option value="27"
								<c:if test="${channel==27 }">selected="selected"</c:if>>爱思</option>
							<option value="28"
								<c:if test="${channel==28 }">selected="selected"</c:if>>海马安卓</option>
					</select>
						<div class="btn" onclick="javascript:search(${pageNo})">搜索</div> <!-- <button onclick="javascript:search(${pageNo})">搜索</button> -->
					</th>
				</tr>
				<tr>
					<th>账号id</th>
					<th>第三方id</th>
					<th>创建时间</th>
					<th>渠道</th>
				</tr>
				<c:choose>
					<c:when test="${fn:length(accountList)==0 }">
						<tr>
							<th colspan="4"><center>暂无账号</center></th>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${accountList }" var="account">
							<tr>
								<td>${account.id }</td>
								<td>${account.uid }</td>
								<td>${account.createtime }</td>
								<td><c:choose>
										<c:when test="${account.channel==0 }">无渠道</c:when>
										<c:when test="${account.channel==1 }">小米</c:when>
										<c:when test="${account.channel==2 }">360</c:when>
										<c:when test="${account.channel==3 }">4399</c:when>
										<c:when test="${account.channel==4 }">乐8</c:when>
										<c:when test="${account.channel==5 }">泡椒</c:when>
										<c:when test="${account.channel==6 }">百度</c:when>
										<c:when test="${account.channel==7 }">腾讯</c:when>
										<c:when test="${account.channel==8 }">XY助手</c:when>
										<c:when test="${account.channel==9 }">海马iOS</c:when>
										<c:when test="${account.channel==10 }">快用</c:when>
										<c:when test="${account.channel==11 }">iOS正版</c:when>
										<c:when test="${account.channel==12 }">三大运营商</c:when>
										<c:when test="${account.channel==13 }">UC</c:when>
										<c:when test="${account.channel==14 }">金立</c:when>
										<c:when test="${account.channel==15 }">联想</c:when>
										<c:when test="${account.channel==16 }">vivo</c:when>
										<c:when test="${account.channel==17 }">华为</c:when>
										<c:when test="${account.channel==18 }">oppo</c:when>
										<c:when test="${account.channel==19 }">当乐</c:when>
										<c:when test="${account.channel==20 }">乐视</c:when>
										<c:when test="${account.channel==21 }">拇指玩</c:when>
										<c:when test="${account.channel==22 }">豌豆荚</c:when>
										<c:when test="${account.channel==23 }">应用汇</c:when>
										<c:when test="${account.channel==24 }">手游天下</c:when>
										<c:when test="${account.channel==25 }">木蚂蚁</c:when>
										<c:when test="${account.channel==26 }">悠悠村</c:when>
										<c:when test="${account.channel==27 }">爱思</c:when>
										<c:when test="${account.channel==28 }">海马安卓</c:when>
									</c:choose></td>
							</tr>
						</c:forEach>
						<c:if test="${fn:length(accountList)!=0 }">
							<tr>
								<th colspan="4"><c:choose>
										<c:when test="${pageNo==1 }">
											<div class="pageBtn"><</div>
										</c:when>
										<c:otherwise>
											<div class="pageBtn"
												onclick="javascript:search('${pageNo-1}')"><</div>
										</c:otherwise>
									</c:choose> <c:forEach begin="1" end="${accountCount }" step="1"
										var="nowPage">
										<c:choose>
											<c:when test="${nowPage==pageNo }">
												<div class="page selected">${nowPage }</div>
											</c:when>
											<c:otherwise>
												<div class="page" onclick="javascript:search('${nowPage}')">${nowPage }</div>
											</c:otherwise>
										</c:choose>
									</c:forEach> <c:choose>
										<c:when test="${pageNo==accountCount }">
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