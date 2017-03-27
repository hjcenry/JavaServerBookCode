<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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

.maillist {
	text-align: center;
	padding: 50px;
}
</style>
<script type="text/javascript">
	function delMail(mailId) {
		var flag = confirm('真的要删除邮件吗？');
		if(flag){
			var userID = $('#userID').val();
			$.ajax({
				type : "post",
				dataType : "text",
				data : {
					"mailId" : mailId
				},
				url : "../activity/delMail",
				success : function(data) {
					if (data == "success") {
						skip("../activity/awardlist?userID=" + userID);
						alert('删除邮件成功');
					} else {
						alert('删除邮件失败');
						return;
					}
				}
			});
		}
	}
</script>
</head>
<body>
	<div class="maillist">
		<center>
			<table border="1">
				<tr>
					<th colspan="10"><center>
							<h2>邮件</h2>
						</center></th>
				</tr>
				<tr>
					<th>邮件id</th>
					<th>userid</th>
					<th>邮件标题</th>
					<th>邮件内容</th>
					<th>发送者</th>
					<th>发送日期</th>
					<th>邮件附件</th>
					<th>是否阅读</th>
					<th>是否领取</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${fn:length(mails)==0 }">
						<tr>
							<th colspan="10"><center>暂无邮件</center></th>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${mails}" var="mail">
							<tr>
								<td>${mail.id }<input type="hidden" id="userID"
									value="${userid }"></td>
								<td>${mail.userid}</td>
								<td>${mail.title}</td>
								<td>${mail.content}</td>
								<td>${mail.sendName}</td>
								<td>${mail.sendDate}</td>
								<td>${mail.items }</td>
								<td><c:choose>
										<c:when test="${mail.isRead==1 }">
								已领取
							</c:when>
										<c:otherwise>
								未领取
							</c:otherwise>
									</c:choose></td>
								<td><c:choose>
										<c:when test="${mail.isPick==1 }">
								已领取
							</c:when>
										<c:otherwise>
								未领取
							</c:otherwise>
									</c:choose></td>
								<td><a onclick="delMail(${mail.id})" href="#">删除</a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</center>
	</div>
</body>
</html>