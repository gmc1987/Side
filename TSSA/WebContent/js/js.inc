<%@ page contentType="text/html; charset=UTF-8"%>
<c:choose>
<c:when test="${pageJquery}">
<script src="${ctx}/js/jquery/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery/jquery-ui.js" type="text/javascript"></script>
</c:when>
<c:otherwise>
<script src="${ctx}/js/prototype.js" type="text/javascript"></script>
</c:otherwise>
</c:choose>
<c:if test="${pageExt}">
<script src="${ctx}/js/adapter/ext/ext-base.js" type="text/javascript"></script>
<script src="${ctx}/js/ext-all.js" type="text/javascript"></script>
</c:if>
<c:if test="${pageTree}">
<script type="text/javascript" src="${ctx}/js/tree/dhtmlXCommon.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/dhtmlXTree.js"></script>
</c:if>
<script type="text/javascript" src="${ctx}/js/htmlEditor.js"></script>
<script type="text/javascript" src="${ctx}/js/util/DateTimeFormat.js"></script>
<script type="text/javascript">
var CONTEXT_PATH = '${ctx}';
<c:if test="${pageExt}">
	Ext.BLANK_IMAGE_URL = '${ctx}/js/resources/images/default/s.gif';
	Ext.MessageBox.buttonText.ok ="确定";
	Ext.MessageBox.buttonText.cancel ="取消";
	Ext.MessageBox.buttonText.yes ="是";
	Ext.MessageBox.buttonText.no ="否";
</c:if>
<c:if test="${pageTree}">
  dhtmlXTreeImPath="${ctx}/images/tree/csh_books/";
</c:if>

</script>

