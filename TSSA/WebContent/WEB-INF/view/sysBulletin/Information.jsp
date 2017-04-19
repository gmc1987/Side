<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${pageMode}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="pageTitle" value="公告内容" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle}</title>
<style type="text/css">
div{
	width: 100%;
	text-align: center;
	line-height: 14px;
	margin-top: 20px;
}
</style>
<script type="text/javascript">

</script>
</head>
<body>
	<div id="title" style="line-height: 24px; font-size: 24px;">${title }</div>
	<div id="content">
		<div>${mainText }</div>
		<div>发布日期：${publishDate}</div>
	</div>
	<div id="forback">
		<input type="button" value="返回" id="back" onclick="javascript:history.go(-1)" style="width: 60px; border-style: hidden; border-width: 0px;">
	</div>
</body>
</html>