<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../js/head.inc"%>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageMode" value="${pageMode}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="pageAjax" value="true"></c:set>
<c:set var="pageTitle" value="系统公告" />
<%@ include file="../../../js/js.inc"%>
<html>
<head>
<script type="text/javascript" src="${ctx }/js/pageTool/kkpager.js"></script>
<script type="text/javascript" src="${ctx }/js/pageTool/kkpager.min.js" ></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/pageTool/kkpager_blue.css" >
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/css/pageTool/kkpager_orange.css" > --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
a:hover {
	color: red;
	cursor: pointer;
}

p {
	font-size: 12px;
	font-family: serif;
	float: left;
	clear: left;
	padding-left: 20px !important;
	padding-top: 10px !important;
	/* margin-top: 10px;
	margin-left: 20px; */
}
</style>
<script type="text/javascript">
$(function(){
	
	//生成分页
	//有些参数是可选的，比如lang，若不传有默认值
	kkpager.generPageHtml({
		pno : '${pageMode.pageNumber}',
		//总页码
		total : '${pageMode.pageCount}',
		//总数据条数
		totalRecords : '${pageMode.count}',
		//链接前部
		hrefFormer : '${ctx}/sys-Bulletin/business-Buttetin',
		//链接尾部
		hrefLatter : '.do',
		getLink : function(n){
			return this.hrefFormer + this.hrefLatter + "?pageNumber="+n;
		}
		/*
		,lang				: {
			firstPageText			: '首页',
			firstPageTipText		: '首页',
			lastPageText			: '尾页',
			lastPageTipText			: '尾页',
			prePageText				: '上一页',
			prePageTipText			: '上一页',
			nextPageText			: '下一页',
			nextPageTipText			: '下一页',
			totalPageBeforeText		: '共',
			totalPageAfterText		: '页',
			currPageBeforeText		: '当前第',
			currPageAfterText		: '页',
			totalInfoSplitStr		: '/',
			totalRecordsBeforeText	: '共',
			totalRecordsAfterText	: '条数据',
			gopageBeforeText		: ' 转到',
			gopageButtonOkText		: '确定',
			gopageAfterText			: '页',
			buttonTipBeforeText		: '第',
			buttonTipAfterText		: '页'
		}*/
		
		//,
		//mode : 'click',//默认值是link，可选link或者click
		//click : function(n){
		//	this.selectPage(n);
		//  return false;
		//}
	});
});
	
	function showBulletin(title, mainText, publishDate, publisher) {
		if (title != null && mainText != null && publishDate != null
				&& publisher != null) {
			window.location.href = "${ctx}/sys-Bulletin/bulletinInformation.do?title=" 
					+ title + "&mainText=" 
					+ mainText + "&publishDate=" 
					+ publishDate + "&publisher=" 
					+ publisher;
		}
	}
</script>
<title>${pageTitle }</title>
</head>
<body>
	<c:forEach items="${pageMode.records}" var="list" varStatus="data">
		<p>
			<a
				onclick='showBulletin("${list.title}", "${list.mainText }", "<fmt:formatDate value="${list.publishDate }"
					pattern="yyyy-MM-dd" />", "${list.publisher}");'>${list.title }
				&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${list.publishDate }"
					pattern="yyyy-MM-dd" />
			</a>
		</p>
	</c:forEach>
	<div style="width: 100%; margin-bottom: 0px; margin-left: 20px;">
		<div id="kkpager"></div>
	</div>
</body>
</html>