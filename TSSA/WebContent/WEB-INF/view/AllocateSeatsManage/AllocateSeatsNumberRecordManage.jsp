<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${pageMode}"></c:set>
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="系统派位管理" />
<%@ include file="../../../js/js.inc" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<script type="text/javascript" src="${ctx}/js/side/AllocateSeatsNumberRecordManage/AllocateSeatsNumberRecordManage.js"></script>
<script type="text/javascript">
var ctx = '${ctx}';
</script>
</head>
<body>
	<div id="allocateNumber"></div>
</body>
</html>