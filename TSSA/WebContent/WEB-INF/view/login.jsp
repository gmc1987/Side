<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="include/include.inc" %>
<%@ include file="../../js/head.inc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="${ctx}/static/style/images/index/favicon.png" />
<!-- 引入my97日期时间控件 -->
<script type="text/javascript" src="${ctx}/static/My97DatePicker/WdatePicker.js" charset="utf-8"></script>

<!-- 引入jQuery -->
<script src="${ctx}/static/jquery-1.8.3.js" type="text/javascript" charset="utf-8"></script>

<!-- 引入EasyUI -->
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/static/easyui1.4/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css">
<script type="text/javascript" src="${ctx}/static/easyui1.4/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/easyui1.4/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<!-- 扩展EasyUI -->
<script type="text/javascript" src="${ctx}/static/extEasyUI.js" charset="utf-8"></script>
<!-- 扩展Jquery -->
<script type="text/javascript" src="${ctx}/static/extJquery.js" charset="utf-8"></script>

<!-- 自定义工具类 -->
<script type="text/javascript" src="${ctx}/static/lightmvc.js" charset="utf-8"></script>

<!-- 扩展EasyUI图标 -->
<link rel="stylesheet" href="${ctx}/static/style/lightmvc.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>时代移动支付平台管理平台</title>
<script>

	$(function() {
		
		$('#loginform').form({
		    url:'${ctx}/user/login.do?isLogin=0',
		    onSubmit : function() {
		    	progressLoad();
				var isValid = $(this).form('validate');
				if(!isValid){
					progressClose();
				}
				return isValid;
			},
		    success:function(result){
		    	result = $.parseJSON(result);
		    	progressClose();
		    	if (result.success) {
		    		window.location.href='${ctx}/user/toIndex.do';
		    	}else{
		    		$.messager.show({
		    			title:'提示',
		    			msg:'<div class="light-info"><div class="light-tip icon-tip"></div><div>用户名或密码错误</div></div>',
		    			showType:'show'
		    		});
		    	}
		    }
		});
	});
	function submitForm(){
		$('#loginform').submit();
	}
	
	function clearForm(){
		$('#loginform').form('clear');
	}
	
	function enterlogin(){
		if (event.keyCode == 13){
			document.getElementById('login').focus(); 
        	event.returnValue=false;
        	event.cancel = true;
        	$('#loginform').submit();
    	}
	}
	
</script>
</head>
<body onkeydown="enterlogin();" style="background-color: #00aff0">

<div align="center" style="padding:60px 0 0 0">
	<div   style="width:620px;height:320px; " >
		<div  align="center" style="width:600px;height:120px;"></div>
		<div  align="center" ><img style="width:600px;height:80px;" src="${ctx}/images/common/logo.png" /></div>
		<div style="padding:10px 10px 10px 10px" align="center" >
	    <form id="loginform" action="${ctx}/user/login.do?isLogin=0"  method="post" style="width:320px;">
	    	<div style="margin-bottom:10px">
    	    	<input class="easyui-textbox" name="userId" style="width:100%;height:40px;padding:12px" data-options="prompt:'输入用户名',iconCls:'icon_login',iconWidth:38">
	       	</div>
            <div style="margin-bottom:20px">
            	<input class="easyui-textbox" type="password" name="pwd" style="width:100%;height:40px;padding:12px" data-options="prompt:'请输入密码',iconCls:'',iconWidth:38">
	    	</div>
	    	<div>
	    		<a id="login" onclick="submitForm()" href="javascript:void(0)" class="easyui-linkbutton"  style="padding:5px 0px;width:100%;border-color: #0b7daf;background-color: #0b7daf"><span style="font-size:14px;">登 录</span></a>
			</div>
	    </form>
	    </div>
	</div>
	</div>
	
	<style>
	/*ie6提示*/
	#ie6-warning{width:100%;position:absolute;top:0;left:0;background:#fae692;padding:5px 0;font-size:12px}
	#ie6-warning p{width:960px;margin:0 auto;}
	</style>
	<script>
	/* jQuery(function ($) {
	 if ( jQuery.browser.msie && ( jQuery.browser.version == "6.0" )&& ( jQuery.browser.version == "7.0" ) && !jQuery.support.style ){
	  jQuery('#ie6-warning').css({'top':jQuery(this).scrollTop()+'px'});
	 }
	}); */
	</script>
	<%-- <form action="${ctx}/user/login.do?isLogin=0" method="post">
		<table>
			<tr>
				<td>用户名:</td>
				<td><input id="userId" name="userId" type="text"></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input id="pwd" name="pwd" type="password"></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="提交">
				</td>
			</tr>
		</table>
	</form> --%>
</body>
</html>