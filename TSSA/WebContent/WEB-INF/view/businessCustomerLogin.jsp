<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../../../js/head.inc" %>
<c:set var="pageNocache" value="true"></c:set>
<c:set var="pageTitle" value="时代折扣移动支付平台"></c:set>
<c:set var="pageTree" value="true"></c:set>
<c:set var="pageExt" value="true"></c:set>
<c:set var="pageAjax" value="true"></c:set> 
<%@ include file="../../../js/js.inc" %> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${pageTitle }</title>
<style type="text/css">
.x-panel-header-text-align {
	text-align:left;
}
</style>
<script type="text/javascript">
Ext.onReady(function(){
	
	var businessCustomerLoginForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
		title : '商户登录',
		labelAlign : 'right',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px 5px 0',
		items : [{
			layout : 'table',
			border : true,
			labelSeparator : ':',
			defaults : {
				bodyStyle : 'padding:1px'
			},
			layoutConfig : {
				columns : 1
			},
			items:[{
				layout : 'form',
				border : true,
				items:[{
					xtype : 'textfield',
					fieldLabel : '员工编码',
					name : 'loginID',
					width : 215,
					maxLength:100,
					maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '员工编码不能为空'
					}]
				},{
					layout : 'form',
					border : true,
					items : [{
						xtype : 'textfield',
						fieldLabel : '登录密码',
						name : 'password',
						inputType : 'password',
						width : 215,
						maxLength:100,
						maxLengthText:'最大字符长度不能超过:{0},(注:每个中文字符长度为2)',
						allowBlank : false,
				    	blankText : '登录密码不能为空'
					}]
				}]
			}],
			buttons : [{
				text : '登录',
				handler : function(){
					businessCustomerLoginForm.form.submit({
						url : '${ctx}/businessCustomer/login.do',
						method : 'POST',
						success : function(form,action){
								loginWin.hide();
								window.location.href = '${ctx}' + action.result.msg;
						},
						failure : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								loginWin.hide();
						}
					});
				}
			}]
	});
	
	var showLoginButton = new Ext.Button({
		id : 'loginButton',
		text : '登录',
		width:'80%',
		height:30
	});
	
	var showResetPWDButton = new Ext.Button({
		id : 'resetPwdButton',
		text : '忘记密码',
		width:'80%',
		height:30
	});
	
	var loginWin = null;
	showLoginButton.on('click', function(){
		if(!loginWin){
			loginWin = new Ext.Window({
				el : 'loginDiv',
				layout : 'fit',
				closeAction : 'hide',
				width : 380,
				height : 165,
				modal : true,
				collapsible : true,
				constrain : true,
				resizable : false,
				items : businessCustomerLoginForm
			});
		}
		businessCustomerLoginForm.getForm().reset();
		loginWin.show(this);
	});
	
	var headPanel = new Ext.Panel({
		region : 'north',
		height : 150,
		layout: "border",
		headerCssClass:'x-panel-header-text-align',
		title : '商户管理平台',
		items : [{
			xtype : 'panel',
            itemId: 'logoPanel',
            width : '80%',
            height : 150,
            region : 'west'
		},{
			xtype : 'panel',
            itemId: 'loginPanel',
            layout: "column",
            width : '20%',
            height : 150,
            region : 'center',
            defaults: {
                // 对每一个子面板都有效applied to each contained panel
                bodyStyle:'padding:25px 25px 25px 30px;'
            },
            items: [{
                width: '100%',
                items:[{
                	items : [showLoginButton]
                },{
                	items : [showResetPWDButton]
                }]
            }]
		/* items: [{
        	region : 'north',
        	height : 75,
        	margins: '0 0 0 5',
            cmargins: '5 5 0 5',
        	items : [showLoginButton]
        },{
        	region : 'center',
            height : 75,
            margins: '0 0 0 5',
            cmargins: '5 5 0 5',
            items : [showResetPWDButton]
        }] */
 //          }
		}]
	});
	
	var contentPanel = new Ext.Panel({
		region : 'center',
		headerCssClass:'x-panel-header-text-align',
		title : '系统公告',
		/* autoLoad : {url:'${ctx}/sys-Bulletin/business-Buttetin.do'} */
		html : '<iframe id=\"listFrame\" src=\"${ctx}/sys-Bulletin/business-Buttetin.do"\" frameborder=\"0\" scrolling=\"auto\" style=\"height:100%;width:100%;border:0px none;\"></iframe>'
	});
	
	new Ext.Viewport({
        enableTabScroll: true,
        layout: "border", 
        items: [headPanel,contentPanel]
     });
	
});
</script>
</head>
<body>
<div id="mainDiv"></div>
<div id="headDiv"></div>
<div id="contentDiv"></div>
<div id="loginDiv"></div>
</body>
</html>