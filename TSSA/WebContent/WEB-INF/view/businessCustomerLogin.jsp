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
	
	var loginWin = null;
	var resetPasswordWin = null;
	
	var businessCustomerLoginForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
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
	
	var businessCustomerResetForm = new Ext.FormPanel({
		labelWidth : 80, 
		frame : true,
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
					maxLengthText:'最大字符长度不能超过:{0}个字符(注:每个中文字符长度为2)',
					allowBlank : false,
			    	blankText : '员工编码不能为空'
					},{
						xtype : 'textarea',
						fieldLabel : '申请备注',
						name : 'remark',
						width : 215,
						height : 50,
						maxLength : 200,
						maxLengthText : '最多不能输出超过:{0}个字符(注:每个中文字符长度为2)'
					}]
				}]
			}],
			buttons : [{
				text : '重置密码',
				handler : function(){
					businessCustomerResetForm.form.submit({
						url : '${ctx}/pwdReset/addRecord.do',
						method : 'POST',
						success : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								resetPasswordWin.hide();
						},
						failure : function(form,action){
								Ext.MessageBox.alert("提示",action.result.msg);
								resetPasswordWin.hide();
						}
					});
				}
			}]
	});
	
	var loginButton = new Ext.Button({
		id : 'loginButton',
		text : '登录/重置密码',
		arrowAlign : 'right',
		menuAlign : 'tl-bl',
		menu : [
		        { text: "登录", handler : function(){
		        	if(!loginWin){
		    			loginWin = new Ext.Window({
		    				el : 'loginDiv',
		    				layout : 'fit',
		    				title : '商户登录',
		    				closeAction : 'hide',
		    				width : 380,
		    				height : 152,
		    				modal : true,
		    				constrain : true,
		    				resizable : false,
		    				items : businessCustomerLoginForm
		    			});
		    		}
		    		businessCustomerLoginForm.getForm().reset();
		    		loginWin.show(this);
		        }},
		        { text: "重置密码", handler : function(){
		        	if(!resetPasswordWin){
		        		resetPasswordWin = new Ext.Window({
		    				el : 'resetDiv',
		    				layout : 'fit',
		    				title : '重置密码',
		    				closeAction : 'hide',
		    				width : 380,
		    				height : 180,
		    				modal : true,
		    				constrain : true,
		    				resizable : false,
		    				items : businessCustomerResetForm
		    			});
		    		}
		        	businessCustomerResetForm.getForm().reset();
		    		resetPasswordWin.show(this);
		        }}
		]
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
            layout: 'fit',
            width : '20%',
            height : 150,
            region : 'center',
            items : [loginButton]
 //       }
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
<div id="resetDiv"></div>
</body>
</html>