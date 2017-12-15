<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

	<!-- jsp文件头和头部 -->
	<%@ include file="../common/top.jsp"%>
	<style type="text/css">
	.commitopacity{position:absolute; width:100%; height:100px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.8; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
	</style>
	
	<!-- 即时通讯 -->
	<link rel="stylesheet" type="text/css" href="${ctx }/js/plugins/websocketInstantMsg/ext4/resources/css/ext-all.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/js/plugins/websocketInstantMsg/css/websocket.css" />
	<script type="text/javascript" src="${ctx }/js/plugins/websocketInstantMsg/ext4/ext-all-debug.js"></script>
	<script type="text/javascript" src="${ctx }/js/plugins/websocketInstantMsg/websocket.js"></script>
	<!-- 即时通讯 -->
	
</head>
<body>

	<!-- 页面顶部¨ -->
	<%@ include file="../common/head.jsp"%>
	<div id="websocket_button"></div>
	<div class="container-fluid" id="main-container">
		<a href="#" id="menu-toggler"><span></span></a>
		<!-- menu toggler -->

		<!-- 左侧菜单 -->
		<%@ include file="../common/left.jsp"%>

		<div id="main-content" class="clearfix">

			<div id="jzts" style="display:none; width:100%; position:fixed; z-index:99999999;">
			<div class="commitopacity" id="bkbgjz"></div>
			<div style="padding-left: 70%;padding-top: 1px;">
				<div style="float: left;margin-top: 3px;"><img src="${ctx }/images/img/loadingi.gif" /> </div>
				<div style="margin-top: 5px;"><h4 class="lighter block red">&nbsp;加载中 ...</h4></div>
			</div>
			</div>

			<div>
				<iframe name="mainFrame" id="mainFrame" frameborder="0" src="tab.do" style="margin:0 auto;width:100%;height:100%;"></iframe>
			</div>

		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
	<!-- basic scripts -->
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='${ctx }/js/common/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="${ctx }/js/bootstarp/bootstrap.min.js"></script>
		<script src="${ctx }/js/common/ace-elements.min.js"></script>
		<script src="${ctx }/js/common/ace.min.js"></script>
		<!-- 引入 -->
		
		<script type="text/javascript" src="${ctx }/js/common/jquery.cookie.js"></script>
		<script type="text/javascript" src="${ctx }/js/common/myjs/menusf.js"></script>
		
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="${ctx }/js/common/myjs/index.js"></script>
</body>
</html>
