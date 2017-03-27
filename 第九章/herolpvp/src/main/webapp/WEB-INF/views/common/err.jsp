<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>

<div data-role="page">
    <div data-role="header" data-theme="c">
    	<a href="<%=request.getContextPath() %>/admin/login" data-icon="home" data-ajax="false">首页</a>
    	<h1>异常页面</h1>
    </div>
    <div data-role="content">
       <%--  <% Exception ex = (Exception)request.getAttribute("exception"); %>
        <H2>Exception: <%= ex.getMessage()%></H2>
        <P/>
        <%ex.printStackTrace(new java.io.PrintWriter(out)); %> --%>
        系统出现异常了，请您点击左上角的首页返回到首页面，您可以在首页面中的反馈里面想管理员反映情况，谢谢您的使用
    </div>
</div>
<%@include file="end.jsp" %>