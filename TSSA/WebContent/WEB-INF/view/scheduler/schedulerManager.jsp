<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${pageMode}"></c:set>
<c:set var="pageAjax" value="true"></c:set> 
<c:set var="title" value="作业管理" />
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
var ctx = '${ctx}';
</script>
<title>${title}</title>
<script type="text/javascript" src="${ctx }/js/side/schedulerManager/scheduler.js"></script>
</head>
<body>
	<!-- <div id="toolBar"></div> -->
	<div id="dataGrild"></div>
	<div id="add_job"></div>
	<div id="mod_job"></div>
</body>
</html>