<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>

<div data-role="page">
    <div data-theme="c" data-role="header">
        <h3>
            错误页面
        </h3>
    </div>
    <div data-role="content">
        ${requestScope.error}
    </div>
</div>
<%@include file="end.jsp" %>