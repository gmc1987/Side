<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="img_path_16" value="${ctx}/images/icon/Nuvola/16" />
<c:set var="img_path_22" value="${ctx}/images/icon/Nuvola/22" />
<c:set var="pageExt" value="true" />
<c:set var="pageJquery" value="true" />
<c:if test="${pageNocache}">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
</c:if>
<c:if test="${pageExt}">
<link rel="stylesheet" type="text/css" href="${ctx}/css/default.css" >
</c:if>
<c:if test="${pageExt}">
<link rel="stylesheet" type="text/css" href="${ctx}/js/resources/css/ext-all.css" />
</c:if>
<c:if test="${pageGrid}">
<link rel="stylesheet" type="text/css" href="${ctx}/css/ecside_style.css" />
</c:if>
<c:if test="${pageTree}">
<link href="${ctx}/css/dhtmlXTree.css" rel="stylesheet" type="text/css">
</c:if>
<c:if test="${pageForm}">
<link rel="stylesheet" type="text/css" href="${ctx}/css/form/jscal2.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/form/border-radius.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/fileuploader.css" />
</c:if>
<c:if test="${pageJquery}">
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-ui.css" />
</c:if>
